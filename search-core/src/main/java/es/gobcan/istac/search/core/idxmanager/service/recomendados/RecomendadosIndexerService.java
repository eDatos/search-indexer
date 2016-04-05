package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;

public interface RecomendadosIndexerService {

    void indexRecommendedKeyword(RecommendedKeyword recommendedKeyword) throws MetamacException;
    
    void reindexRecommendedKeywords(ServiceContext ctx) throws MetamacException;
    
    void deleteById(String id) throws ServiceExcepcion;

    void deleteAll() throws ServiceExcepcion;

    void deleteByQuery(String query) throws ServiceExcepcion;

    void commitANDoptimize() throws ServiceExcepcion;

}
