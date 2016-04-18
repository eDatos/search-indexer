package es.gobcan.istac.search.core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.siemac.metamac.core.common.exception.MetamacException;
import org.siemac.metamac.core.common.exception.MetamacExceptionBuilder;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.exception.ServiceExceptionType;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public class ExportUtils {

    public static String exportRecommendedLinks(List<RecommendedLink> recommendedLinks) throws MetamacException {
        // Export
        OutputStream outputStream = null;
        OutputStreamWriter writer = null;

        try {
            File file = File.createTempFile(SearchConstants.TSV_RECOMMENDED_LINKS_FILE_PREFIX, SearchConstants.TSV_EXPORTATION_EXTENSION);
            outputStream = new FileOutputStream(file);
            writer = new OutputStreamWriter(outputStream, SearchConstants.TSV_EXPORTATION_ENCODING);

            writeRecommendedLinksHeader(writer);

            for (RecommendedLink recommendedLink : recommendedLinks) {
                writeRecommendedLink(writer, recommendedLink);
            }

            writer.flush();
            return file.getName();
        } catch (Exception e) {
            throw MetamacExceptionBuilder.builder().withExceptionItems(ServiceExceptionType.EXPORTATION_TSV_ERROR).withMessageParameters(e).build();
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(writer);
        }
    }

    public static void writeRecommendedLinksHeader(OutputStreamWriter writer) throws IOException {
        writeCell(writer, SearchConstants.TSV_HEADER_KEYWORD, true);
        writeCell(writer, SearchConstants.TSV_HEADER_TITLE);
        writeCell(writer, SearchConstants.TSV_HEADER_URL);
        writeCell(writer, SearchConstants.TSV_HEADER_DESCRIPTION);

        writer.write(SearchConstants.TSV_LINE_SEPARATOR);
    }

    public static void writeRecommendedLink(OutputStreamWriter writer, RecommendedLink recommendedLink) throws IOException {
        writeCell(writer, recommendedLink.getRecommendedKeyword().getKeyword(), true);
        writeCell(writer, recommendedLink.getTitle());
        writeCell(writer, recommendedLink.getUrl());
        writeCell(writer, recommendedLink.getDescription());

        writer.write(SearchConstants.TSV_LINE_SEPARATOR);
    }

    private static void writeCell(OutputStreamWriter writer, Object field) throws IOException {
        writeCell(writer, field, false);
    }

    private static void writeCell(OutputStreamWriter writer, Object field, boolean firstColumnCell) throws IOException {
        if (!firstColumnCell) {
            writer.write(SearchConstants.TSV_SEPARATOR);
        }

        if (field != null) {
            writer.write(field.toString());
        }
    }

}
