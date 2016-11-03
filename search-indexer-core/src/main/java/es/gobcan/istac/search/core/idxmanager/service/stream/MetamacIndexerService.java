package es.gobcan.istac.search.core.idxmanager.service.stream;

import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;

import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;

public interface MetamacIndexerService {

    public void indexarDatasetVersion(DatasetVersionAvro datasetVersionAvroAvro) throws ServiceExcepcion;
}
