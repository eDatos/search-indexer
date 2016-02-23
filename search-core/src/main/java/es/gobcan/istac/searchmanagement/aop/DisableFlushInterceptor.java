package es.gobcan.istac.searchmanagement.aop;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.siemac.metamac.core.common.aop.DisableFlushInterceptorBase;

public class DisableFlushInterceptor extends DisableFlushInterceptorBase {

    @Override
    @PersistenceContext(unitName = "SearchManagementEntityManagerFactory")
    protected void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}