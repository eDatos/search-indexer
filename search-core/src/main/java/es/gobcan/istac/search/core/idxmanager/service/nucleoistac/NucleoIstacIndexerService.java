package es.gobcan.istac.idxmanager.service.nucleoistac;

import es.gobcan.istac.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;

public interface NucleoIstacIndexerService {

    void indexarElementoGPE(NucleoMetadatos nucleoIstacDomain, String formatoRecurso) throws ServiceExcepcion;

    void reindexarGPEelementos() throws ServiceExcepcion;

    void eliminarElemento(String id) throws ServiceExcepcion;

    void borrarTodo() throws ServiceExcepcion;

    void borrar(String query) throws ServiceExcepcion;

    void commitANDoptimize() throws ServiceExcepcion;

}
