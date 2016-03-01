package es.gobcan.istac.idxmanager.service.indexacion;

import java.io.IOException;
import java.io.InputStream;

import org.apache.solr.common.util.ContentStreamBase;

public class CustomInputStream extends ContentStreamBase {

    private InputStream inputStream = null;
    private boolean primerAcceso = true;

    public CustomInputStream(InputStream inputStreamIN) throws IOException {
        this.inputStream = inputStreamIN;
    }

    @Override
    public InputStream getStream() throws IOException {
        if (this.primerAcceso) {
            return this.inputStream;
        } else {
            throw new IOException("No se puede garantizar que el inputStream no haya sido consumdio.");
        }
    }
}