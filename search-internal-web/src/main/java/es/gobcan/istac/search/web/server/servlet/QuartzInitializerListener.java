package es.gobcan.istac.search.web.server.servlet;

import java.util.Date;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;

import es.gobcan.istac.search.core.conf.SearchConfigurationService;
import es.gobcan.istac.search.core.idxmanager.service.indexacion.CrawlerJob;
import es.gobcan.istac.search.core.idxmanager.service.indexacion.GPEIndexerJob;

/**
 * <p>
 * A ServletContextListner that can be used to initialize Quartz.
 * </p>
 * <p>
 * You'll want to add something like this to your WEB-INF/web.xml file:
 *
 * <pre>
 *     &lt;context-param&gt;
 *         &lt;param-name&gt;quartz-config-name&lt;/param-name&gt;
 *         &lt;param-value&gt;quartz&lt;/param-value&gt;
 *     &lt;/context-param&gt;
 *     &lt;context-param&gt;
 *         &lt;param-name&gt;quartz-shutdown-on-unload&lt;/param-name&gt;
 *         &lt;param-value&gt;true&lt;/param-value&gt;
 *     &lt;/context-param&gt;
 *     &lt;context-param&gt;
 *         &lt;param-name&gt;quartz-start-scheduler-on-load&lt;/param-name&gt;
 *         &lt;param-value&gt;true&lt;/param-value&gt;
 *     &lt;/context-param&gt;
 *
 *     &lt;listener&gt;
 *         &lt;listener-class&gt;
 *             es.gobcan.istac.gpe.web.servlet.QuartzInitializerListener
 *         &lt;/listener-class&gt;
 *     &lt;/listener&gt;
 * </pre>
 * </p>
 * <p>
 * The init parameter 'quartz-config-name' can be used to specify the name. If you leave out this parameter, the default ("quartz") will be used.
 * </p>
 * <p>
 * The init parameter 'shutdown-on-unload' can be used to specify whether you want scheduler.shutdown() called when the servlet is unloaded (usually when the application server is being shutdown).
 * Possible values are "true" or "false". The default is "true".
 * </p>
 * <p>
 * The init parameter 'start-scheduler-on-load' can be used to specify whether you want the scheduler.start() method called when the servlet is first loaded. If set to false, your application will
 * need to call the start() method before the scheduler begins to run and process jobs. Possible values are "true" or "false". The default is "true", which means the scheduler is started.
 * </p>
 * A StdSchedulerFactory instance is stored into the ServletContext. You can gain access
 * to the factory from a ServletContext instance like this: <br>
 *
 * <pre>
 *
 * StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY);
 * </pre>
 * <p>
 * The init parameter 'servlet-context-factory-key' can be used to override the name under which the StdSchedulerFactory is stored into the ServletContext, in which case you will want to use this name
 * rather than <code>QuartzInitializerListener.QUARTZ_FACTORY_KEY</code> in the above example.
 * </p>
 * <p>
 * The init parameter 'quartz-start-delay-seconds' can be used to specify the amount of time to wait after initializing the scheduler before scheduler.start() is called.
 * </p>
 * Once you have the factory instance, you can retrieve the Scheduler instance by calling <code>getScheduler()</code> on the factory.
 *
 * @author James House
 * @author Chuck Cavaness
 * @author John Petrocik
 */
public class QuartzInitializerListener implements ServletContextListener {

    private static final String        GROUP_IDX_JOBS             = "groupIdx";

    protected static Log               LOG                        = LogFactory.getLog(QuartzInitializerListener.class);

    public static final String         QUARTZ_FACTORY_KEY         = "org.quartz.impl.StdSchedulerFactory.KEY";

    private boolean                    performShutdown            = true;

    private Scheduler                  scheduler                  = null;

    private SearchConfigurationService searchConfigurationService = null;

    /*
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     * Interface.
     * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
     */

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("Quartz Initializer Servlet loaded, initializing Scheduler...");

        ServletContext servletContext = sce.getServletContext();
        StdSchedulerFactory factory;
        try {
            // String configName = servletContext.getInitParameter("quartz-config-name");
            // if (configName == null) {
            // configName = "quartz";
            // }
            String shutdownPref = servletContext.getInitParameter("quartz-shutdown-on-unload");

            Properties quartzProperties = getSearchConfigurationService().getProperties();

            if (shutdownPref != null) {
                performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
            }

            // get Properties
            factory = new StdSchedulerFactory(quartzProperties);

            // Always want to get the scheduler, even if it isn't starting,
            // to make sure it is both initialized and registered.
            scheduler = factory.getScheduler();

            // Should the Scheduler being started now or later
            String startOnLoad = servletContext.getInitParameter("quartz-start-scheduler-on-load");

            int startDelay = 0;
            String startDelayS = servletContext.getInitParameter("quartz-start-delay-seconds");
            try {
                if (startDelayS != null && startDelayS.trim().length() > 0) {
                    startDelay = Integer.parseInt(startDelayS);
                }
            } catch (Exception e) {
                System.out.println("Cannot parse value of 'start-delay-seconds' to an integer: " + startDelayS + ", defaulting to 5 seconds.");
                startDelay = 5;
            }

            // Crea Jobs CRON
            crearJobsCron();

            /*
             * If the "start-scheduler-on-load" init-parameter is not specified,
             * the scheduler will be started. This is to maintain backwards
             * compatability.
             */
            if (startOnLoad == null || (Boolean.valueOf(startOnLoad).booleanValue())) {
                if (startDelay <= 0) {
                    // Start now
                    scheduler.start();
                    System.out.println("Scheduler has been started...");
                } else {
                    // Start delayed
                    scheduler.startDelayed(startDelay);
                    System.out.println("Scheduler will start in " + startDelay + " seconds.");
                }
            } else {
                System.out.println("Scheduler has not been started. Use scheduler.start()");
            }

            String factoryKey = servletContext.getInitParameter("quartz-servlet-context-factory-key");
            if (factoryKey == null) {
                factoryKey = QUARTZ_FACTORY_KEY;
            }

            System.out.println("Storing the Quartz Scheduler Factory in the servlet context at key: " + factoryKey);
            LOG.info("QuartzInitializerListener.contextInitialized --> Guardada la inicializacion del quartz bajo la clave " + factoryKey);

            servletContext.setAttribute(factoryKey, factory);

        } catch (Exception e) {
            System.out.println("Quartz Scheduler failed to initialize: " + e.toString());
            LOG.error("Fallo en la inicializacion del quartz --> QuartzInitializerListener.contextInitialized :: ", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        if (!performShutdown) {
            return;
        }

        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            System.out.println("Quartz Scheduler failed to shutdown cleanly: " + e.toString());
            LOG.error("Fallo en la destruccion del quartz --> QuartzInitializerListener.contextDestroyed :: ", e);
        }

        LOG.info("QuartzInitializerListener.contextDestroyed --> La finalizacion del quatz ha sido correcta");
        System.out.println("Quartz Scheduler successful shutdown.");
    }

    private void crearJobsCron() throws Exception {

        // INDEXACION WEB JOB
        crearJobCron(CrawlerJob.class, "crawlerJOB", "triggerCrawler", getSearchConfigurationService().retrieveIndexationWebCron());

        // INDEXACION GPE JOB
        crearJobCron(GPEIndexerJob.class, "gpeIndexerJob", "triggerGPEIndexer", getSearchConfigurationService().retrieveIndexationGpeCron());
    }

    private void crearJobCron(Class<? extends Job> jobClass, String jobName, String triggerName, String cronExpression) throws MetamacException, SchedulerException {
        //@formatter:off
        JobDetail job = JobBuilder
                .newJob(jobClass)
                .withIdentity(jobName, GROUP_IDX_JOBS)
                .build();

        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(triggerName, GROUP_IDX_JOBS)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
        //@formatter:on

        Date ft = scheduler.scheduleJob(job, trigger);
        LOG.info(job.getKey() + " ha sido planificado para ejecutase en fecha: " + ft + " y repetirse de acuerdo a la expresion cron: " + trigger.getCronExpression());
    }

    private SearchConfigurationService getSearchConfigurationService() {
        if (searchConfigurationService == null) {
            searchConfigurationService = (SearchConfigurationService) ApplicationContextProvider.getApplicationContext().getBean("configurationService");
        }
        return searchConfigurationService;
    }

}
