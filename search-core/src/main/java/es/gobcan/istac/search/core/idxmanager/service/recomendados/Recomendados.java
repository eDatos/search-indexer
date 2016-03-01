package es.gobcan.istac.search.core.idxmanager.service.recomendados;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("recomendados")
public class Recomendados {

    // @formatter:off
    /**
     * Ejemplo de estructura XML de la plantilla

		<recomendados>
			<reomendacion>
				<palabras>
		  			<palabra>nacimientos</palabra>
	  				<palabra>tasa</palabra>
				</palabras>
				<enlaces>
			  		<enlace url="urlEnlace" titulo="titulo del enlace">
			  			<descripcion>Esto es un ejemplo de descripcion ....</descripcion>
					</enlace>
				</enlaces>
  			</recomendacion>
	     </recomendados>

     */
    // @formatter:on

    @XStreamImplicit
    private List<Recomendacion> recomendaciones;

    public Recomendados() {
        recomendaciones = new ArrayList<Recomendacion>();
    }

    public List<Recomendacion> getRecomendaciones() {
        return recomendaciones;
    }

    public void setRecomendaciones(List<Recomendacion> enlacesSet) {
        recomendaciones = enlacesSet;
    }

    @Override
    public String toString() {
        return recomendaciones != null ? recomendaciones.toString() : "null";
    }

}
