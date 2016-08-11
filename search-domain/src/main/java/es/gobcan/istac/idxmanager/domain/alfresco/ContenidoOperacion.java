package es.gobcan.istac.idxmanager.domain.alfresco;

import java.util.List;

public class ContenidoOperacion {

    private String id = null;
    private String operacion = null;

    private List<RecursoInfo> recursos = null;

    public static class RecursoInfo {

        private String id = null;
        private String nombre = null;
        private String tipoRecurso = null;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getTipoRecurso() {
            return tipoRecurso;
        }

        public void setTipoRecurso(String tipoRecurso) {
            this.tipoRecurso = tipoRecurso;
        }
    }

    // @formatter:off
	/* EJEMPLO JSON
	[
	   {
	      "id":"cd226a7f-4bc5-46d9-93e3-b6957b736968",
	      "operacion":"C00028B",
	      "recursos":[

	      ]
	   },
	   {
	      "id":"a76f0ab6-57ff-4cf4-b9d5-e1acd082c646",
	      "operacion":"C00040A",
	      "recursos":[
	         {
	            "id":"62d91cc4-639a-406a-ba85-7b7f668686cd",
	            "nombre":"C00040A_DSC_0001_V01000.px",
	            "tipoRecurso":"ARCHIVO_PX",
	            "tipo": [
	            	"DSC"
	            ]
	         },
		....
	 */
	// @formatter:on

    /**************************************************************************
     * GETTERS/SETTERS
     **************************************************************************/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public List<RecursoInfo> getRecursos() {
        return recursos;
    }

    public void setRecursos(List<RecursoInfo> recursos) {
        this.recursos = recursos;
    }

}
