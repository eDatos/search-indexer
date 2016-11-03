package es.gobcan.istac.idxmanager.service.test.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.core.CoreContainer;
import org.junit.After;
import org.junit.Before;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class EmbeddedSolrServerTestCaseBase {

    protected static Logger     log       = LoggerFactory.getLogger(EmbeddedSolrServerTestCaseBase.class);

    protected static File       SOLR_HOME = null;
    private static final String SOLR_XML  = "solr.xml";

    protected CoreContainer     cores     = null;
    protected Path              tempPath;

    @Before
    public void setUp() throws Exception {
        // SOLR HOME
        SOLR_HOME = ApplicationContextProvider.getApplicationContext().getResource("file:../etc/solr").getFile();
        System.setProperty("solr.solr.home", SOLR_HOME.getCanonicalPath());
        System.out.println("Solr home: " + SOLR_HOME.getCanonicalPath());

        // SOLR DATA
        tempPath = Files.createTempDirectory("istac-solar_");
        System.setProperty("solr.data.dir", tempPath.toString());
        System.out.println("Solr data: " + tempPath.toString());

        cores = CoreContainer.createAndLoad(SOLR_HOME.getAbsolutePath(), getSolrXml());
    }

    @After
    public void tearDown() throws Exception {
        if (cores != null) {
            cores.shutdown();
        }
        deleteAdditionalFiles();
    }

    protected void deleteAdditionalFiles() throws IOException {
        deleteRecursive(tempPath);
    }

    protected SolrClient getIstacCore() {
        return new EmbeddedSolrServer(cores, "istac");
    }

    protected SolrClient getSolrCore(String name) {
        return new EmbeddedSolrServer(cores, name);
    }

    protected File getSolrXml() throws Exception {
        return new File(SOLR_HOME, SOLR_XML);
    }

    protected void deleteRecursive(Path directory) throws IOException {
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
    }

}
