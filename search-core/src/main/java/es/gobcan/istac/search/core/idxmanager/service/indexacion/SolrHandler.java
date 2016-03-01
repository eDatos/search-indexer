package es.gobcan.istac.search.core.idxmanager.service.indexacion;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.droids.api.ContentEntity;
import org.apache.droids.api.Handler;
import org.apache.droids.exception.DroidsException;
import org.apache.droids.handle.WriterHandler;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibm.icu.text.DateFormatSymbols;
import com.ibm.icu.text.SimpleDateFormat;

import es.gobcan.istac.idxmanager.domain.dom.ContentTypeEnumDomain;
import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public class SolrHandler extends WriterHandler implements Handler {

    protected static Log           log                    = LogFactory.getLog(SolrHandler.class);

    @Autowired
    private ResourceIndexerService resourceIndexerService = null;

    private static final String    PATTERNDATE1           = "E MMM dd HH:mm:ss zzz yyyy";
    private static final String    PATTERNDATE2           = "yyyy-MM-dd HH:mm:ss";

    @Override
    public void handle(URI uri, ContentEntity entity) throws IOException, DroidsException {
        SolrInputDocument doc = new SolrInputDocument();
        doc.addField(IndexacionEnumDomain.ID.getCampo(), uri.toString()); // Equivalente al IDENTIFIER_UNIVERSAL del nucleo ISTAC
        doc.addField(IndexacionEnumDomain.ORIGENRECURSO.getCampo(), OrigenRecursoDomain.RECURSO_WEB.getSiglas());
        if (isComplementaria(uri)) {
            doc.addField(IndexacionEnumDomain.NM_TYPE.getCampo(), TypeNMDomain.COMPLEMENTARIA_WEB.getSiglas());
        }
        CustomMtdParseImpl parse = (CustomMtdParseImpl) entity.getParse();

        // INDEXADO DE METADATOS EXTRAIDOS
        boolean isContentTypeInside = false;
        boolean hasDataCategory = false;
        Date lastUpdateExcel = null;

        String[] metadataFound = parse.getMetadata().names();
        for (int i = 0; i < metadataFound.length; i++) {
            String mtdStr = metadataFound[i].toLowerCase();

            if (mtdStr.equals("content-type")) {
                isContentTypeInside = true;
            }

            IndexacionEnumDomain mtdEnum = IndexacionEnumDomain.get("tk_" + mtdStr);
            // Si es un metadato que tiene correspondencia con alguno de los de tika que indexamos en un campo automatico
            if (mtdEnum != null) {
                String[] mtdValues = parse.getMetadata().getValues(metadataFound[i]);
                if (mtdEnum.isMultievaluado()) {
                    if (mtdEnum.equals(IndexacionEnumDomain.TK_SUBJECT)) {
                        for (int j = 0; j < mtdValues.length; j++) {
                            if (mtdValues[j].equals("DS")) {
                                hasDataCategory = true;
                            }
                        }
                    }
                    doc.addField(mtdEnum.getCampo(), mtdValues);
                } else {
                    // La fecha de ultima modificacion es el unico de los de tika que mapeamos a unievaluado.
                    if (mtdEnum.equals(IndexacionEnumDomain.TK_LAST_MODIFIED)) { // pdfs principalmente
                        String newDate = parse.getMetadata().get(metadataFound[i]).replace("T", " ");
                        doc.addField(mtdEnum.getCampo(), paseDate(PATTERNDATE2, newDate));
                    } else if (mtdEnum.equals(IndexacionEnumDomain.TK_LAST_SAVE_DATE)) {
                        lastUpdateExcel = paseDate(PATTERNDATE1, parse.getMetadata().get(metadataFound[i]));
                    } else if (mtdEnum.equals(IndexacionEnumDomain.TK_DATE)) { // Docs openoffice
                        // vamos a quitar la T que separa el tiempo y la sustituimos por el " "
                        String newDate = parse.getMetadata().get(metadataFound[i]).replace("T", " ");
                        lastUpdateExcel = paseDate(PATTERNDATE2, newDate);
                    } else {
                        doc.addField(mtdEnum.getCampo(), parse.getMetadata().get(metadataFound[i]));
                    }
                }
                continue;
            }

            // Si es un metadato que tiene correspondencia con alguno de los de tika que indexamos en un campo automatico
            mtdEnum = IndexacionEnumDomain.get("nm_" + mtdStr);
            if (mtdEnum != null) {
                if (mtdEnum.isMultievaluado()) {
                    doc.addField(mtdEnum.getCampo(), parse.getMetadata().getValues(metadataFound[i]));
                } else {
                    doc.addField(mtdEnum.getCampo(), parse.getMetadata().get(metadataFound[i]));
                }
                continue;
            }
        }

        // Contenido
        doc.addField(IndexacionEnumDomain.TK_CONTENIDO.getCampo(), parse.getText());

        // Completado del metadato FORMAT si no existe en los metadatos extraidos.
        if (isContentTypeInside) {
            contentTypeParse(doc);
        }

        // Si es un excel con la categoria data lo incluimos como tipo data en el indice
        String docFormat = doc.getField(IndexacionEnumDomain.FORMATO.getCampo()) != null ? (String) doc.getField(IndexacionEnumDomain.FORMATO.getCampo()).getValue() : null;
        if ((ContentTypeEnumDomain.APPLICATION_EXCEL.getContentType().equals(docFormat) || ContentTypeEnumDomain.APPLICATION_EXCEL_OPEN.getContentType().equals(docFormat))) {
            if (lastUpdateExcel != null) {
                doc.addField(IndexacionEnumDomain.NM_LAST_UPDATE.getCampo(), lastUpdateExcel);
            }
            if (hasDataCategory) {
                doc.addField(IndexacionEnumDomain.NM_TYPE.getCampo(), "DS");
            }
        }

        try {
            resourceIndexerService.indexaRecurso(doc);
        } catch (ServiceExcepcion e) {
            throw new DroidsException(e);
        }
    }

    /***************************************************************************
     * PRIVATE
     ***************************************************************************/

    private Date paseDate(String pattern, String fecha) {
        try {
            // EJ: Fri Sep 10 07:57:35 GMT 2010 (PDF files)
            SimpleDateFormat pat1 = new SimpleDateFormat(pattern, new DateFormatSymbols(Locale.US));
            return pat1.parse(fecha);
        } catch (ParseException e) {
            log.info("No se ha podido parsear la fecha:: " + fecha);
            return null;
        }
    }

    private boolean isComplementaria(URI uri) {
        return uri.toString().contains("/complementaria/");
    }

    private void contentTypeParse(SolrInputDocument doc) {
        try {
            // Si no se ha completado el metadato FORMATO
            if (doc.getField(IndexacionEnumDomain.FORMATO.getCampo()) == null) {
                ContentTypeEnumDomain ctTypeEnum = ContentTypeEnumDomain.get(((ArrayList<String>) doc.getField(IndexacionEnumDomain.TK_CONTENT_TYPE.getCampo()).getValue()).get(0));
                // Si el contentType es uno de los preproseados
                if (ctTypeEnum != null) {
                    doc.addField(IndexacionEnumDomain.FORMATO.getCampo(), ctTypeEnum.getContentType());
                }
            }
        } catch (Exception e) {
            log.info("Paseando SolrHandler::contentTypeParse:: " + e);
        }
    }

    // <!-- HTML PARSER -->
    // <entry key="text/html" value-ref="htmlParser"></entry>
    // <!-- DOCUMENT PARSER -->
    // <entry key="text/plain" value-ref="documentParser"></entry>
    // <entry key="application/pdf" value-ref="documentParser"></entry>
    // <entry key="application/msword" value-ref="documentParser"></entry>
    // <entry key="application/x-tika-ooxml" value-ref="documentParser"></entry>
    // <entry key="application/vnd.ms-excel" value-ref="documentParser"></entry>
    // <entry key="application/vnd.ms-powerpoint" value-ref="documentParser"></entry>
    // <entry key="application/xml" value-ref="documentParser"></entry>
    // <entry key="application/rtf" value-ref="documentParser"></entry>
    // <entry key="application/rss+xml" value-ref="documentParser"></entry>

}
