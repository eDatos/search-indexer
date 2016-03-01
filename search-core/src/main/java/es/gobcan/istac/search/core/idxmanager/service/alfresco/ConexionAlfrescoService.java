package es.gobcan.istac.idxmanager.service.alfresco;

import java.util.List;

import es.gobcan.istac.idxmanager.domain.alfresco.ContenidoOperacion;
import es.gobcan.istac.idxmanager.domain.alfresco.NucleoMetadatosPublicado;
import es.gobcan.istac.idxmanager.service.excepcion.ServiceExcepcion;

public interface ConexionAlfrescoService {

    String obtenerContenidoNodo(String identifierUniv) throws ServiceExcepcion;

    List<ContenidoOperacion> obtenerRecursosPublicados() throws ServiceExcepcion;

    NucleoMetadatosPublicado obtenerNodoNucleoMetadatos(String nodeId) throws ServiceExcepcion;

    NucleoMetadatosPublicado obtenerNodoNucleoMetadatosUltimaVersionVisible(String nodeId) throws ServiceExcepcion;

}
