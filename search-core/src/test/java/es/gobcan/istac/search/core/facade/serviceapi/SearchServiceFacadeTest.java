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

    @Test
    public void testRetrieveRecommendedLinkById() throws Exception {
        fail("testRetrieveRecommendedLinkById not implemented");
    }

    @Test
    public void testCreateRecommendedLink() throws Exception {
        fail("testCreateRecommendedLink not implemented");
    }

    @Test
    public void testUpdateRecommendedLink() throws Exception {
        fail("testUpdateRecommendedLink not implemented");
    }

    @Test
    public void testDeleteRecommendedLink() throws Exception {
        fail("testDeleteRecommendedLink not implemented");
    }

    @Test
    public void testFindAllRecommendedLinks() throws Exception {
        fail("testFindAllRecommendedLinks not implemented");
    }

    @Test
    public void testFindRecommendedLinks() throws Exception {
        fail("testFindRecommendedLinks not implemented");
    }

    @Test
    public void testExportRecommendedLinks() throws Exception {
        fail("testExportRecommendedLinks not implemented");
    }

    @Test
    public void testImportByReplacingRecommendedLinks() throws Exception {
        fail("testImportByReplacingRecommendedLinks not implemented");
    }

    @Test
    public void testImportByAddingRecommendedLinks() throws Exception {
        fail("testImportByAddingRecommendedLinks not implemented");
    }

    @Test
    public void testRetrieveRecommendedKeywordById() throws Exception {
        fail("testRetrieveRecommendedKeywordById not implemented");
    }

    @Test
    public void testCreateRecommendedKeyword() throws Exception {
        fail("testCreateRecommendedKeyword not implemented");
    }

    @Test
    public void testUpdateRecommendedKeyword() throws Exception {
        fail("testUpdateRecommendedKeyword not implemented");
    }

    @Test
    public void testDeleteRecommendedKeyword() throws Exception {
        fail("testDeleteRecommendedKeyword not implemented");
    }

    @Test
    public void testFindAllRecommendedKeywords() throws Exception {
        fail("testFindAllRecommendedKeywords not implemented");
    }

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
}
