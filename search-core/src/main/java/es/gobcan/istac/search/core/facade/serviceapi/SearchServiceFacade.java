package es.gobcan.istac.search.core.facade.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface SearchServiceFacade {

    public void reindexWeb(ServiceContext ctx);
    public void reindexGpe(ServiceContext ctx) throws ServiceExcepcion;
    public void reindexRecommendedLinks(ServiceContext ctx) throws ServiceExcepcion;
}
