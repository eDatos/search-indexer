package es.gobcan.istac.idxmanager.domain.alfresco;

import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;

public class NucleoMetadatosPublicado extends NucleoMetadatos {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3925161903696368486L;

    private String tipoRecurso = null;

    // @formatter:off
	/* EJEMPLO JSON
	{
	"identifierUniversal": "urn:uuid:35980bfa-0eaf-41bc-973d-55aa1d708ec2",
	"tipoRecurso": "ARCHIVO_PX",
	"type": [
		"DSC"
		],
	"title": "Turistas jugadores de golf seg\u00fan grupos de edad por pa\u00edses de residencia y per\u00edodos.",
	"subtitle": [
	    ],
	"titleAlternative": [
	    ],
	 "surveyCode": "C00040A",   
	 "surveyTitle": "Encuesta sobre satisfacci\u00f3n y gasto de los usuarios de campos de golf",   
	 "surveyAlternative": "",   
	 "subjectAreas": [
					"Sector servicios > Hosteler\u00eda y turismo > Demanda tur\u00edstica: Turistas y pasajeros"	
				
	    ],
	  "subjectCodes": [
					"08021"	
				
	    ],
	  "description": [
	    ],
	  "_abstract": [
	    ],
	   "keywords": [
					"Pa\u00edses de residencia"	
				,
					"TOTAL"	
				,
					"Espa\u00f1a (excepto Canarias)"	
				,
					"Alemania"	
				,
					"Reino Unido"	
				,
					"Pa\u00edses N\u00f3rdicos"	
				,
					"Otros pa\u00edses"	
				,
					"###"	
				,
					"Grupos de edad"	
				,
					"TOTAL"	
				,
					"Menores de 30"	
				,
					"De 31 a 45"	
				,
					"De 46 a 65"	
				,
					"M\u00e1s de 65"	
				,
					"###"	
				
	    ],
	   "coverageSpatial": [
	    ],
	    "coverageSpatialCodes": [
	    ],
	    "coverageTemporal": [
						"2008 Primer trimestre"	
					,
						"2008 Segundo trimestre"	
					,
						"2008 Tercer trimestre"	
					,
						"2008 Cuarto trimestre"	
					,
						"2009 Primer trimestre"	
					
	    ],
	    "coverageTemporalCodes": [
						"2008Q1"	
					,
						"2008Q2"	
					,
						"2008Q3"	
					,
						"2008Q4"	
					,
						"2009Q1"	
					
	    ],
	   	"replaces": [
						"C00040A_0001_V02005"	
	    ],
	    "lastUpdate": "2009-06-25T10:00:00.000+0000",
	    "rangeDatesAvailable": "2009-06-25T10:00:00.000+0000"
	}
     */
    // @formatter:on

    /**************************************************************************
     * GETTERS/SETTERS
     **************************************************************************/

    public void setTipoRecurso(String tipoRecurso) {
        this.tipoRecurso = tipoRecurso;
    }

    public String getTipoRecurso() {
        return tipoRecurso;
    }

}
