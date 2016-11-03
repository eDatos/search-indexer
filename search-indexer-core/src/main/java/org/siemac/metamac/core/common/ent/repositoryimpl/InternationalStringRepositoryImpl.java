package org.siemac.metamac.core.common.ent.repositoryimpl;

import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * Repository implementation for InternationalString
 */
@Repository("internationalStringRepository")
public class InternationalStringRepositoryImpl
    extends InternationalStringRepositoryBase {
    public InternationalStringRepositoryImpl() {
    }

    public void deleteInternationalStringsEfficiently(
        Collection<Long> internationalStringToDelete) {

        // not necessary
        throw new UnsupportedOperationException(
            "deleteInternationalStringsEfficiently not implemented");

    }
}
