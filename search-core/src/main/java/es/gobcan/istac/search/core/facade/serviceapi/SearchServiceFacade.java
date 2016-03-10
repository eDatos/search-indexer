package es.gobcan.istac.search.core.facade.serviceapi;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface SearchServiceFacade {

    public void reindexWeb(ServiceContext ctx) throws ServiceExcepcion, MetamacException;
    public void reindexGpe(ServiceContext ctx) throws ServiceExcepcion, MetamacException;
    public void reindexRecommendedLinks(ServiceContext ctx) throws ServiceExcepcion, MetamacException;
}
