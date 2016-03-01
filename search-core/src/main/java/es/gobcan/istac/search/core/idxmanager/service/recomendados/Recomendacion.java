package es.gobcan.istac.idxmanager.service.recomendados;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@XStreamAlias("recomendacion")
public class Recomendacion {

    @XStreamOmitField
    private String id;

    @XStreamAlias("palabras")
    @XStreamAsAttribute
    private String palabras;

    @XStreamImplicit(itemFieldName = "enlace")
    private List<Enlace> enlaces;

    public Recomendacion() {
        enlaces = new ArrayList<Enlace>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPalabras() {
        return palabras;
    }

    public void setPalabras(String palabras) {
        this.palabras = "";
        if (palabras != null) {
            String[] palabrasSplit = palabras.split(",");
            for (int i = 0; i < palabrasSplit.length; i++) {
                if (i > 0) {
                    this.palabras += ",";
                }
                this.palabras += palabrasSplit[i].trim();
            }
        }
    }

    public List<Enlace> getEnlaces() {
        return enlaces;
    }

    public void setEnlaces(List<Enlace> enlaces) {
        this.enlaces = enlaces;
    }

    public List<String> getListaPalabras() {
        List<String> list = new ArrayList<String>();
        if (palabras != null) {
            String[] palabrasSplit = palabras.split(",");
            for (int i = 0; i < palabrasSplit.length; i++) {
                list.add(palabrasSplit[i].trim());
            }
        }
        return list;
    }

    @Override
    public String toString() {
        return "Recomendacion [palabras=" + palabras + ", enlaces=" + enlaces + "]";
    }
}
