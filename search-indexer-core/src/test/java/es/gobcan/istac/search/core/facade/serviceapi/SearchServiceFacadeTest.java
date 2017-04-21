package es.gobcan.istac.search.core.facade.serviceapi;

import static org.junit.Assert.fail;

import org.fornax.cartridges.sculptor.framework.test.AbstractDbUnitJpaTests;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Spring based transactional test with DbUnit support.
 */
@Ignore
public class SearchServiceFacadeTest extends AbstractDbUnitJpaTests implements SearchServiceFacadeTestBase {

    @Autowired
    protected SearchServiceFacade searchServiceFacade;

    @Override
    @Test
    public void testRetrieveRecommendedLinkById() throws Exception {
        fail("testRetrieveRecommendedLinkById not implemented");
    }

    @Override
    @Test
    public void testCreateRecommendedLink() throws Exception {
        fail("testCreateRecommendedLink not implemented");
    }

    @Test
    @Override
    public void testCreateRecommendedLinks() throws Exception {
        fail("testCreateRecommendedLink not implemented");
    }

    @Override
    @Test
    public void testUpdateRecommendedLink() throws Exception {
        fail("testUpdateRecommendedLink not implemented");
    }

    @Override
    @Test
    public void testDeleteRecommendedLink() throws Exception {
        fail("testDeleteRecommendedLink not implemented");
    }

    @Override
    @Test
    public void testFindAllRecommendedLinks() throws Exception {
        fail("testFindAllRecommendedLinks not implemented");
    }

    @Override
    @Test
    public void testFindRecommendedLinks() throws Exception {
        fail("testFindRecommendedLinks not implemented");
    }

    @Override
    @Test
    public void testExportRecommendedLinks() throws Exception {
        fail("testExportRecommendedLinks not implemented");
    }

    @Override
    @Test
    public void testImportByReplacingRecommendedLinks() throws Exception {
        fail("testImportByReplacingRecommendedLinks not implemented");
    }

    @Override
    @Test
    public void testImportByAddingRecommendedLinks() throws Exception {
        fail("testImportByAddingRecommendedLinks not implemented");
    }

    @Override
    @Test
    public void testRetrieveRecommendedKeywordById() throws Exception {
        fail("testRetrieveRecommendedKeywordById not implemented");
    }

    @Override
    @Test
    public void testCreateRecommendedKeyword() throws Exception {
        fail("testCreateRecommendedKeyword not implemented");
    }

    @Override
    @Test
    public void testUpdateRecommendedKeyword() throws Exception {
        fail("testUpdateRecommendedKeyword not implemented");
    }

    @Override
    @Test
    public void testDeleteRecommendedKeyword() throws Exception {
        fail("testDeleteRecommendedKeyword not implemented");
    }

    @Override
    @Test
    public void testFindAllRecommendedKeywords() throws Exception {
        fail("testFindAllRecommendedKeywords not implemented");
    }

    @Override
    @Test
    public void testFindRecommendedKeywords() throws Exception {
        fail("testFindRecommendedKeywords not implemented");
    }

    @Override
    public void testReindexWeb() throws Exception {
        fail("testReindexWeb not implemented");
    }

    @Override
    public void testReindexGpe() throws Exception {
        fail("testReindexGpe not implemented");
    }

    @Override
    public void testReindexRecommendedLinks() throws Exception {
        fail("testReindexRecommendedLinks not implemented");
    }

    @Override
    public void testReindexMetamacStatisticalResources() throws Exception {
        fail("testReindexMetamacStatisticalResources not implemented");
    }
}
