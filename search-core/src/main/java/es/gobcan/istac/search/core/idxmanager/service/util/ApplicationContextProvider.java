package es.gobcan.istac.idxmanager.service.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx;

    /**
     * Injected from the class "ApplicationContextProvider" which is automatically loaded during Spring-Initialization.
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext; // NOSONAR
    }

    /**
     * Get access to the Spring ApplicationContext from everywhere in your Application.
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }
}
