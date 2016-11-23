package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.apache.avro.specific.SpecificRecordBase;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface MetamacIndexerService<T extends SpecificRecordBase> {

    public void index(T message) throws ServiceExcepcion;

}
