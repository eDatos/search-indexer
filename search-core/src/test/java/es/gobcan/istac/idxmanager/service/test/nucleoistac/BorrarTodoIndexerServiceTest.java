package es.gobcan.istac.idxmanager.service.test.nucleoistac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.service.test.util.TestBase;
import es.gobcan.istac.search.core.idxmanager.service.nucleoistac.NucleoIstacIndexerService;

public class BorrarTodoIndexerServiceTest extends TestBase {

    @Autowired
    private NucleoIstacIndexerService nucleoIstacIndexerService = null;

    @Test
    // @Ignore
    public void testBorrar() throws Exception {
        nucleoIstacIndexerService.borrarTodo();
    }

    @Test
    // @Ignore
    public void testBorrarGPE() throws Exception {
        nucleoIstacIndexerService.borrar(IndexacionEnumDomain.ORIGENRECURSO.getCampo() + ":" + OrigenRecursoDomain.RECURSO_GPE.getSiglas());
        nucleoIstacIndexerService.commitANDoptimize();
    }

}
