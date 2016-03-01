/*
 * Extension de la implementacion por defecto del Parse de Droids.
 * Esta extension permite almacenar los metadatos que ha extraido TIKA.
 */

package es.gobcan.istac.idxmanager.service.indexacion;

import java.util.Collection;

import org.apache.droids.api.Link;
import org.apache.droids.parse.ParseImpl;
import org.apache.tika.metadata.Metadata;

/**
 * @author arte
 */
public class CustomMtdParseImpl extends ParseImpl {

    protected Metadata metadata;

    public Metadata getMetadata() {
        return metadata;
    }

    public CustomMtdParseImpl() {
    }

    public CustomMtdParseImpl(String text, Collection<Link> outlinks) {
        super(text, outlinks);
    }

    public CustomMtdParseImpl(String text, Object data, Collection<Link> outlinks) {
        super(text, data, outlinks);
    }

    public CustomMtdParseImpl(String text, Collection<Link> outlinks, Metadata metadata) {
        this.text = text;
        this.outlinks = outlinks;
        this.metadata = metadata;
    }

}
