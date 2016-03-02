package es.gobcan.istac.idxmanager.domain.dom;

public enum FormatoRecursoIstaceDomain {
    
    TIPO_RECURSO_ARCHIVO_PX("ARCHIVO_PX", "dominio.istace.tipo_recurso.archivo_px"),    
    TIPO_RECURSO_ARCHIVO_PDF("ARCHIVO_PDF", "dominio.istace.tipo_recurso.archivo_pdf"),
    TIPO_RECURSO_PUBLICACION("PUBLICACION", "dominio.istace.tipo_recurso.publicacion"),
    
    TIPO_RECURSO_ARCHIVO_HTML("ARCHIVO_HTML", "dominio.istace.tipo_recurso.html"),
    TIPO_RECURSO_ARCHIVO_WORD("ARCHIVO_WORD", "dominio.istace.tipo_recurso.word"),
    TIPO_RECURSO_ARCHIVO_EXCEL("ARCHIVO_EXCEL", "dominio.istace.tipo_recurso.excel"),
    TIPO_RECURSO_ARCHIVO_RTF("ARCHIVO_RTF", "dominio.istace.tipo_recurso.rtf"),
    TIPO_RECURSO_ARCHIVO_PPT("ARCHIVO_POWERPOINT", "dominio.istace.tipo_recurso.powerpoint"),
    
    TIPO_RECURSO_ARCHIVO_XML("ARCHIVO_XML", "dominio.istace.tipo_recurso.xml"),
    TIPO_RECURSO_ARCHIVO_RSS("ARCHIVO_RSS", "dominio.istace.tipo_recurso.rss"),
    
    TIPO_RECURSO_OTROS("OTROS", "dominio.istace.tipo_recurso.otros");
    
//  TIPO_RECURSO_CAPITULO("CAPITULO", "dominio.istace.tipo_recurso.capitulo"),              
//  TIPO_RECURSO_TABLA("TABLA", "dominio.istace.tipo_recurso.tabla"),
//  TIPO_RECURSO_CARPETA("CARPETA", "dominio.istace.tipo_recurso.carpeta"),         
//  TIPO_RECURSO_OPERACION("OPERACION", "dominio.istace.tipo_recurso.operacion"),       

    
    
    private String siglas;
    private String descripcion;
    
    public String getSiglas() {
        return siglas;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    FormatoRecursoIstaceDomain(String siglasIN, String descripcionIN) {
        this.siglas = siglasIN;
        this.descripcion = descripcionIN;
    }

}
