package es.gobcan.istac.search.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.siemac.metamac.core.common.aop.FlushingInterceptorBase;

public class FlushingInterceptor extends FlushingInterceptorBase {

    @Override
    @PersistenceContext(unitName = "SearchEntityManagerFactory")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}