package es.gobcan.istac.idxmanager.domain.dom;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ContentTypeEnumDomain {

    TEXT_PLAIN("text/plain", FormatoRecursoIstaceDomain.TIPO_RECURSO_OTROS.getSiglas()),
    TEXT_HTML("text/html", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_HTML.getSiglas()),

    APPLICATION_PDF("application/pdf", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PDF.getSiglas()),

    APPLICATION_MSWORD("application/msword", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_WORD.getSiglas()),
    APPLICATION_EXCEL("application/vnd.ms-excel", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),
    APPLICATION_POWERPOINT("application/vnd.ms-powerpoint", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PPT.getSiglas()),

    // OPENOFFICE
    APPLICATION_MSWORD_OPEN("application/vnd.oasis.opendocument.text", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),
    APPLICATION_EXCEL_OPEN("application/vnd.oasis.opendocument.spreadsheet", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),

    // OFFICE OXML
    APPLICATION_POWERPOINT_POXML("application/vnd.openxmlformats-officedocument.presentationml.presentation", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PPT.getSiglas()),
    APPLICATION_POWERPOINT_SOXML("application/vnd.openxmlformats-officedocument.presentationml.slide", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PPT.getSiglas()),
    APPLICATION_POWERPOINT_SSOXML("application/vnd.openxmlformats-officedocument.presentationml.slideshow", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PPT.getSiglas()),
    APPLICATION_POWERPOINT_TOXML("application/vnd.openxmlformats-officedocument.presentationml.template", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PPT.getSiglas()),

    APPLICATION_EXCEL_SOXML("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),
    APPLICATION_EXCEL_TOXML("application/vnd.openxmlformats-officedocument.spreadsheetml.template", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),
    APPLICATION_EXCEL_MOXML("application/vnd.ms-excel.template.macroenabled.12", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_EXCEL.getSiglas()),

    APPLICATION_MSWORD_DOXML("application/vnd.openxmlformats-officedocument.wordprocessingml.document", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_WORD.getSiglas()),
    APPLICATION_MSWORD_TOXML("application/vnd.openxmlformats-officedocument.wordprocessingml.template", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_WORD.getSiglas()),
    // ***************

    APPLICATION_XML("application/xml", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_XML.getSiglas()),
    APPLICATION_RTF("application/rtf", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_RTF.getSiglas()),
    APPLICATION_RSS_XML("application/rss+xml", FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_RSS.getSiglas());

    private String nombre;
    private String contentType;

    private static final Map<String, ContentTypeEnumDomain> lookup = new HashMap<String, ContentTypeEnumDomain>();

    static {
        for (ContentTypeEnumDomain s : EnumSet.allOf(ContentTypeEnumDomain.class))
            lookup.put(s.getNombre(), s);
    }

    public static ContentTypeEnumDomain get(String campo) {
        return lookup.get(campo);
    }

    public String getNombre() {
        return nombre;
    }

    public String getContentType() {
        return contentType;
    }

    private ContentTypeEnumDomain(String nombre, String contenType) {
        this.nombre = nombre;
        this.contentType = contenType;
    }

}
