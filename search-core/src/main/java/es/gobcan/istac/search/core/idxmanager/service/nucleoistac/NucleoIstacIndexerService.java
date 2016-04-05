package es.gobcan.istac.search.core.idxmanager.service.nucleoistac;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface NucleoIstacIndexerService {

    void indexarElementoGPE(NucleoMetadatos nucleoIstacDomain, String formatoRecurso) throws ServiceExcepcion;

    void eliminarElemento(String id) throws ServiceExcepcion;

    void borrarTodo() throws ServiceExcepcion;

    void borrar(String query) throws ServiceExcepcion;

    void commitANDoptimize() throws ServiceExcepcion;

    void reindexarGPEelementos(ServiceContext ctx) throws ServiceExcepcion;

}
