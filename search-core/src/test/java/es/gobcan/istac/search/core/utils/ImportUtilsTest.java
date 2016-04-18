package es.gobcan.istac.search.core.utils;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.io.FileUtils;

import es.gobcan.istac.idxmanager.service.test.util.TestBase;
import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.domain.RecommendedLinksTsvHeader;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public class ImportUtilsTest extends TestBase {

    @Test
    public void testParseRecommendedLinksTsv() throws Exception {

        String fileName = "importation-recommended-links-01.tsv";
        File file = new File(this.getClass().getResource("/tsv/" + fileName).getFile());

        List<MetamacExceptionItem> exceptionItems = new ArrayList<MetamacExceptionItem>();
        InputStream stream = new FileInputStream(file);
        String charset = FileUtils.guessCharset(file);
        InputStreamReader inputStreamReader = new InputStreamReader(stream, charset);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        // Header
        String line = bufferedReader.readLine();
        RecommendedLinksTsvHeader header = ImportUtils.parseTsvHeaderToImportRecommendedLinks(line, exceptionItems);

        assertEquals(4, header.getColumnsSize());
        assertEquals(0, header.getKeywordPosition());
        assertEquals(1, header.getTitlePosition());
        assertEquals(2, header.getUrlPosition());
        assertEquals(3, header.getDescriptionPosition());

        line = bufferedReader.readLine();
        int lineNumber = 2;
        String[] columns = StringUtils.splitPreserveAllTokens(line, SearchConstants.TSV_SEPARATOR);

        Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword = new HashMap<String, RecommendedKeyword>();
        Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting = new HashMap<String, RecommendedKeyword>();
        RecommendedKeyword recommendedKeyword = ImportUtils.tsvLineToRecommendedKeyword(mockServiceContextWithoutPrincipal(), header, columns, lineNumber, exceptionItems,
                recommendedKeywordsToPersistByKeyword, recommendedKeywordsAlreadyExisting);
        RecommendedLink recommendedLink = ImportUtils.tsvLineToRecommendedLink(mockServiceContextWithoutPrincipal(), header, columns, lineNumber, exceptionItems, recommendedKeyword);

        assertEquals("documentos", recommendedLink.getRecommendedKeyword().getKeyword());
        assertEquals("http://localhost:9080/istac/complementaria/comp1_nogpe.pdf", recommendedLink.getUrl());
        assertEquals("Complementaria vitícola web", recommendedLink.getTitle());
        assertEquals("Descripción del pdf...", recommendedLink.getDescription());

    }

}
