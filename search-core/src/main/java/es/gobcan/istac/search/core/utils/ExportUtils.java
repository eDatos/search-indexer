package es.gobcan.istac.search.core.utils;

import java.io.IOException;
import java.io.OutputStreamWriter;

import es.gobcan.istac.search.core.constants.SearchConstants;
import es.gobcan.istac.search.core.recommendedlink.domain.RecommendedLink;

public class ExportUtils {

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
