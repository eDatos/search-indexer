package es.gobcan.istac.idxmanager.domain.dom;

public enum OrigenRecursoDomain {

    /*
     * Representa el origen del recurso que ha indexar en SOLR.
     */
	RECURSO_PATROCINADO("PATROCINADO","dominio.origenrecurso.gpe"), //Recurso patrocinado
	RECURSO_GPE("GPE", "dominio.origenrecurso.gpe"),   			//	GPE: El recurso esta gestionado por el GPE (Alfresco)
	RECURSO_WEB("WEB", "dominio.origenrecurso.web"), 			//	WEB: Se trata de un recurso web gestionado por la web ISTAC
//	RECURSO_OLAP("OLAP", "dominio.origenrecurso.olap"), 		//	OLAP: Se trata de un recurso en la base de datos OLAP

    DATASET_OTROS("OTROS", "dominio.origenrecurso.otros"); // Para otros casos

    private String siglas;
    private String descripcion;

    public String getSiglas() {
        return siglas;
    }

    OrigenRecursoDomain(String siglasIN, String descripcionIN) {
        this.siglas = siglasIN;
        this.descripcion = descripcionIN;
    }

}
