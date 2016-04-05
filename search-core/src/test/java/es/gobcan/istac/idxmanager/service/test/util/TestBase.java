package es.gobcan.istac.idxmanager.service.test.util;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-test.xml", "classpath:spring/solr-embedded-server-test.xml"})
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

    protected ServiceContext mockServiceContextWithoutPrincipal() {
        return new ServiceContext("junit", "junit", "app");
    }

}
