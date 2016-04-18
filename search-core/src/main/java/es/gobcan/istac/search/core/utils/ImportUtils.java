package es.gobcan.istac.search.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionItem;
import org.siemac.metamac.core.common.io.FileUtils;
import org.siemac.metamac.core.common.util.ApplicationContextProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.domain.RecommendedLinksTsvHeader;
import es.gobcan.istac.search.core.exception.ServiceExceptionParameters;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedKeyword;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedKeywordsServiceInvocationValidator;
import es.gobcan.istac.search.core.recommendedlink.serviceapi.validators.RecommendedLinksServiceInvocationValidator;

public class ImportUtils {

    private final static Logger logger = LoggerFactory.getLogger(ImportUtils.class);

    public static void parseFile(ServiceContext ctx, File file, List<MetamacExceptionItem> exceptionItems, Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting,
            Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword, List<RecommendedLink> recommendedLinksToPersist) {
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            InputStream stream = new FileInputStream(file);
            String charset = FileUtils.guessCharset(file);
            inputStreamReader = new InputStreamReader(stream, charset);
            bufferedReader = new BufferedReader(inputStreamReader);

            // Header
            String line = bufferedReader.readLine();
            RecommendedLinksTsvHeader header = ImportUtils.parseTsvHeaderToImportRecommendedLinks(line, exceptionItems);

            if (CollectionUtils.isEmpty(exceptionItems)) {

                int lineNumber = 2;
                while ((line = bufferedReader.readLine()) != null) {
                    if (StringUtils.isBlank(line)) {
                        continue;
                    }
                    String[] columns = StringUtils.splitPreserveAllTokens(line, SearchConstants.TSV_SEPARATOR);
                    if (columns.length != header.getColumnsSize()) {
                        exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_WRONG_NUMBER_ELEMENTS, lineNumber));
                        continue;
                    }

                    RecommendedKeyword recommendedKeyword = ImportUtils.tsvLineToRecommendedKeyword(ctx, header, columns, lineNumber, exceptionItems, recommendedKeywordsToPersistByKeyword,
                            recommendedKeywordsAlreadyExisting);
                    RecommendedLink recommendedLink = ImportUtils.tsvLineToRecommendedLink(ctx, header, columns, lineNumber, exceptionItems, recommendedKeyword);

                    if (recommendedKeyword != null && recommendedLink != null) {
                        if (recommendedKeyword.getId() == null && !recommendedKeywordsToPersistByKeyword.containsKey(recommendedKeyword.getKeyword())) {
                            recommendedKeywordsToPersistByKeyword.put(recommendedKeyword.getKeyword(), recommendedKeyword);
                        }
                        recommendedLinksToPersist.add(recommendedLink);
                    }
                    lineNumber++;
                }
            }
        } catch (Exception e) {
            logger.error("Error importing tsv file", e);
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_ERROR_FILE_PARSING, e.getMessage()));
        } finally {
            try {
                inputStreamReader.close();
                bufferedReader.close();
            } catch (IOException e) {
                // do not relaunch error
                logger.error("Error closing streams", e);
            }
        }
    }

    public static RecommendedLinksTsvHeader parseTsvHeaderToImportRecommendedLinks(String line, List<MetamacExceptionItem> exceptions) throws MetamacException {
        RecommendedLinksTsvHeader header = new RecommendedLinksTsvHeader();
        String[] headerColumns = StringUtils.splitPreserveAllTokens(line, SearchConstants.TSV_SEPARATOR);
        if (headerColumns == null) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_HEADER_INCORRECT, 2));
            return header;
        }
        List<String> headersExpected = Arrays.asList(SearchConstants.TSV_HEADER_KEYWORD, SearchConstants.TSV_HEADER_TITLE, SearchConstants.TSV_HEADER_URL, SearchConstants.TSV_HEADER_DESCRIPTION);
        int headerExpectedIndex = 0;
        header.setColumnsSize(headerColumns.length);
        for (int i = 0; i < headerColumns.length; i++) {
            String columnName = headerColumns[i];

            if (SearchConstants.TSV_HEADER_KEYWORD.equals(columnName)) {
                header.setKeywordPosition(i);
                headerExpectedIndex++;
            } else if (SearchConstants.TSV_HEADER_TITLE.equals(columnName)) {
                header.setTitlePosition(i);
                headerExpectedIndex++;
            } else if (SearchConstants.TSV_HEADER_URL.equals(columnName)) {
                header.setUrlPosition(i);
                headerExpectedIndex++;
            } else if (SearchConstants.TSV_HEADER_DESCRIPTION.equals(columnName)) {
                header.setDescriptionPosition(i);
                headerExpectedIndex++;
            }
        }

        if (headerExpectedIndex < headersExpected.size()) {
            exceptions.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_HEADER_INCORRECT));
        }

        return header;
    }

    public static RecommendedKeyword tsvLineToRecommendedKeyword(ServiceContext ctx, RecommendedLinksTsvHeader header, String[] columns, Serializable lineNumber,
            List<MetamacExceptionItem> exceptionItems, Map<String, RecommendedKeyword> recommendedKeywordsToPersistByKeyword, Map<String, RecommendedKeyword> recommendedKeywordsAlreadyExisting) {
        RecommendedKeyword recommendedKeyword = null;

        String keyword = columns[header.getKeywordPosition()];
        if (StringUtils.isBlank(keyword)) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_METADATA_REQUIRED, lineNumber, ServiceExceptionParameters.IMPORTATION_TSV_COLUMN_KEYWORD));
        }

        if (recommendedKeywordsToPersistByKeyword.containsKey(keyword)) {
            recommendedKeyword = recommendedKeywordsToPersistByKeyword.get(keyword);
        } else if (recommendedKeywordsAlreadyExisting.containsKey(keyword)) {
            recommendedKeyword = recommendedKeywordsAlreadyExisting.get(keyword);
        } else {
            recommendedKeyword = new RecommendedKeyword();
            recommendedKeyword.setKeyword(keyword);

            // try {
            // getRecommendedKeywordsServiceInvocationValidator().checkCreateRecommendedKeyword(ctx, recommendedKeyword);
            // } catch (MetamacException metamacException) {
            // logger.error("Error importing recommended link from tsv file", metamacException);
            // MetamacExceptionItem metamacExceptionItem = new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_LINE_INCORRECT, lineNumber);
            // metamacExceptionItem.setExceptionItems(metamacException.getExceptionItems());
            // exceptionItems.add(metamacExceptionItem);
            // }
        }

        if (CollectionUtils.isEmpty(exceptionItems)) {
            return recommendedKeyword;
        } else {
            return null;
        }
    }

    /**
     * Transforms tsv line to Code. IMPORTANT: Do not execute save or update operation
     *
     * @param recommendedKeywordsToPersistByKeyword
     * @param recommendedKeywordsAlreadyExisting
     * @param recommendedLinksToPersist
     * @return
     */
    public static RecommendedLink tsvLineToRecommendedLink(ServiceContext ctx, RecommendedLinksTsvHeader header, String[] columns, int lineNumber, List<MetamacExceptionItem> exceptionItems,
            RecommendedKeyword recommendedKeyword) {

        String title = columns[header.getTitlePosition()];
        String url = columns[header.getUrlPosition()];
        String description = columns[header.getDescriptionPosition()];

        if (StringUtils.isBlank(title)) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_METADATA_REQUIRED, lineNumber, ServiceExceptionParameters.IMPORTATION_TSV_COLUMN_TITLE));
        }
        if (StringUtils.isBlank(url)) {
            exceptionItems.add(new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_METADATA_REQUIRED, lineNumber, ServiceExceptionParameters.IMPORTATION_TSV_COLUMN_URL));
        }

        RecommendedLink recommendedLink = new RecommendedLink();
        recommendedLink.setRecommendedKeyword(recommendedKeyword);
        recommendedLink.setTitle(title);
        recommendedLink.setUrl(url);
        recommendedLink.setDescription(description);

        // try {
        // getRecommendedLinksServiceInvocationValidator().checkCreateRecommendedLink(ctx, recommendedLink);
        // } catch (MetamacException metamacException) {
        // logger.error("Error importing recommended link from tsv file", metamacException);
        // MetamacExceptionItem metamacExceptionItem = new MetamacExceptionItem(ServiceExceptionType.IMPORTATION_TSV_LINE_INCORRECT, lineNumber);
        // metamacExceptionItem.setExceptionItems(metamacException.getExceptionItems());
        // exceptionItems.add(metamacExceptionItem);
        // }

        if (CollectionUtils.isEmpty(exceptionItems)) {
            return recommendedLink;
        } else {
            return null;
        }
    }

    private static RecommendedKeywordsServiceInvocationValidator getRecommendedKeywordsServiceInvocationValidator() {
        return (RecommendedKeywordsServiceInvocationValidator) ApplicationContextProvider.getApplicationContext().getBean(RecommendedKeywordsServiceInvocationValidator.BEAN_ID);
    }

    private static RecommendedLinksServiceInvocationValidator getRecommendedLinksServiceInvocationValidator() {
        return (RecommendedLinksServiceInvocationValidator) ApplicationContextProvider.getApplicationContext().getBean(RecommendedLinksServiceInvocationValidator.BEAN_ID);
    }

}
