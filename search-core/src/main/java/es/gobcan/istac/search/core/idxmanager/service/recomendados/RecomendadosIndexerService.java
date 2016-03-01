package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface RecomendadosIndexerService {

    void indexarElementoRecomendado(Recomendacion recomendacion) throws ServiceExcepcion;

    void reindexarElementosRecomendados() throws ServiceExcepcion;

    void eliminarElemento(String id) throws ServiceExcepcion;

    void borrarTodo() throws ServiceExcepcion;

    void borrar(String query) throws ServiceExcepcion;

    void commitANDoptimize() throws ServiceExcepcion;

}
