package es.gobcan.istac.idxmanager.service.test.util;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/services-test.xml", "classpath:spring/solr-embedded-server-test.xml"})
public abstract class TestBase {

    private static boolean inicializado = false;

    @BeforeClass
    public static void onBeforeClass() {
        inicializado = false;
    }

    @Before
    public void onBefore() {
        if (!inicializado) {
            try {
                inicializado = true;
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
