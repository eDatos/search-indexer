package es.gobcan.istac.search.core.idxmanager.service.nucleoistac;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.common.SolrInputDocument;
import org.fornax.cartridges.sculptor.framework.errorhandling.ServiceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.domain.dom.FormatoRecursoIstaceDomain;
import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.client.IndexacionStatusDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.idxmanager.domain.util.Constants;
import es.gobcan.istac.jaxi.pxservice.api.dominio.NucleoMetadatos;
import es.gobcan.istac.search.core.idxmanager.service.alfresco.ConexionAlfrescoService;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;

@Service
public class NucleoIstacIndexerServiceImpl implements NucleoIstacIndexerService {

    protected static Log log = LogFactory.getLog(NucleoIstacIndexerService.class);

    @Autowired
    private SolrService solr = null;

    @Autowired
    private IndexationStatus indexationStatus = null;

    @Autowired
    private ConexionAlfrescoService conexionAlfrescoService;

    @Override
    public void indexarElementoGPE(NucleoMetadatos nucleoIstacDomain, String formatoRecurso) throws ServiceExcepcion {
        // http://localhost:8080/alfresco/service/api/node/content/workspace/SpacesStore/70a5f841-2008-4b61-91a6-eb852788be6c
        SolrInputDocument solrInputDocument;
        if (FormatoRecursoIstaceDomain.TIPO_RECURSO_ARCHIVO_PDF.getSiglas().equals(formatoRecurso)) {
            solrInputDocument = nucleoMetadatosContent2SolrDocument(nucleoIstacDomain); // Campos del indice mapeados al nucleo ISTAC
        } else {
            solrInputDocument = nucleoMetadatos2SolrDocument(nucleoIstacDomain); // Campos del indice mapeados al nucleo ISTAC

            // Facet Compuesto: Subject Area
            indexarSubjectAreaFacet(nucleoIstacDomain, solrInputDocument);

            // Facet Compuesto: Survey
            indexarSurveyFacet(nucleoIstacDomain, solrInputDocument);
        }

        // Campos el indice auxiliares
        solrInputDocument.addField(IndexacionEnumDomain.ID.getCampo(), nucleoIstacDomain.getIdentifierUniversal()); // Equivalente al IDENTIFIER_UNIVERSAL del nucleo ISTAC
        solrInputDocument.addField(IndexacionEnumDomain.FORMATO.getCampo(), formatoRecurso); // Formato recurso, alguno de las siglas definidas en TipoRecursoIstaceDomain
        solrInputDocument.addField(IndexacionEnumDomain.ORIGENRECURSO.getCampo(), OrigenRecursoDomain.RECURSO_GPE.getSiglas());

        solr.insertarDoc(solrInputDocument);
    }

    private void indexarSurveyFacet(NucleoMetadatos nucleoIstacDomain, SolrInputDocument solrInputDocument) {
        solrInputDocument.addField(IndexacionEnumDomain.FACET_SURVEY_KEYVALUE_FF.getCampo(), nucleoIstacDomain.getSurveyCode() + Constants.KEY_VALUE_SEPARATOR + nucleoIstacDomain.getSurveyTitle());
    }

    private void indexarSubjectAreaFacet(NucleoMetadatos nucleoIstacDomain, SolrInputDocument solrInputDocument) {
        List<String> subjectKeyValues = new LinkedList<String>();
        List<String> subjectAreas = nucleoIstacDomain.getSubjectAreas();
        Iterator<String> subjectCodesIterator = nucleoIstacDomain.getSubjectCodes().iterator();
        for (String value : subjectAreas) {
            if (subjectCodesIterator.hasNext()) {
                String key = subjectCodesIterator.next();
                subjectKeyValues.add(key + Constants.KEY_VALUE_SEPARATOR + value);
            }
        }
        solrInputDocument.addField(IndexacionEnumDomain.FACET_SUBJECT_KEYVALUE_FF.getCampo(), subjectKeyValues);
    }

    @Override
    public void eliminarElemento(String id) throws ServiceExcepcion {
        solr.eliminarDoc(id);
        solr.commitANDoptimize();
    }

    @Override
    public void borrarTodo() throws ServiceExcepcion {
        solr.borrarTodoAndCommit();
    }

    @Override
    public void borrar(String query) throws ServiceExcepcion {
        solr.borrarWithoutCommit(query);
    }

    @Override
    public void commitANDoptimize() throws ServiceExcepcion {
        solr.commitANDoptimize();
    }

    @Override
    public void reindexarGPEelementos(ServiceContext ctx) throws ServiceExcepcion {

        try {
            if (!indexationStatus.getIdxGPEStatus().equals(IndexacionStatusDomain.INDEXANDO)) {
                GpeReIndexerThread gpeReIndexerThread = new GpeReIndexerThread(conexionAlfrescoService, ctx);
                new Thread(gpeReIndexerThread).start();
            } else {
                new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_REINDEXACION_INPROGRESS);
            }
        } catch (Exception e) {
            log.error("Error: NucleoIstacIndexerServiceImpl:reindexarGPEelementos " + e.getMessage());
            new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_TICKET_ALFRESCO);
        }

    }

    /***************************************************************************
     * GETTERS/SETTERS
     ***************************************************************************/

    public void setSolr(SolrService solr) {
        this.solr = solr;
    }

    public SolrService getSolr() {
        return solr;
    }

    /***************************************************************************
     * PRIVATE
     ***************************************************************************/

    /**
     * @param nucleoIstacDomain
     * @return
     */
    private SolrInputDocument nucleoMetadatos2SolrDocument(NucleoMetadatos nucleoIstacDomain) {
        // Se crea un documento SOLR
        SolrInputDocument doc = new SolrInputDocument();

        // Identificadores
        doc.addField(IndexacionEnumDomain.NM_TITLE.getCampo(), nucleoIstacDomain.getTitle());
        doc.addField(IndexacionEnumDomain.NM_SUBTITLE.getCampo(), nucleoIstacDomain.getSubtitle());
        doc.addField(IndexacionEnumDomain.NM_TITLE_ALTERNATIVE.getCampo(), nucleoIstacDomain.getTitleAlternative());

        // Clasificadores tematicos de contenido
        doc.addField(IndexacionEnumDomain.NM_SURVEY_CODE.getCampo(), nucleoIstacDomain.getSurveyCode());
        doc.addField(IndexacionEnumDomain.NM_SURVEY_TITLE.getCampo(), nucleoIstacDomain.getSurveyTitle());
        doc.addField(IndexacionEnumDomain.NM_SURVEY_ALTERNATIVE.getCampo(), nucleoIstacDomain.getSurveyAlternative());
        doc.addField(IndexacionEnumDomain.NM_SUBJECT_AREAS.getCampo(), nucleoIstacDomain.getSubjectAreas());
        doc.addField(IndexacionEnumDomain.NM_SUBJECT_CODES.getCampo(), nucleoIstacDomain.getSubjectCodes());

        // Descriptores de contenido
        doc.addField(IndexacionEnumDomain.NM_DESCRIPTION.getCampo(), nucleoIstacDomain.getDescription());
        doc.addField(IndexacionEnumDomain.NM_ABSTRACT.getCampo(), nucleoIstacDomain.getAbstract());
        doc.addField(IndexacionEnumDomain.NM_KEYWORDS.getCampo(), nucleoIstacDomain.getKeywords());
        doc.addField(IndexacionEnumDomain.NM_COVERAGE_SPATIAL.getCampo(), nucleoIstacDomain.getCoverageSpatial());
        doc.addField(IndexacionEnumDomain.NM_COVERAGE_SPATIAL_CODES.getCampo(), nucleoIstacDomain.getCoverageSpatialCodes());
        doc.addField(IndexacionEnumDomain.NM_COVERAGE_TEMPORAL.getCampo(), nucleoIstacDomain.getCoverageTemporal());
        doc.addField(IndexacionEnumDomain.NM_COVERAGE_TEMPORAL_CODES.getCampo(), nucleoIstacDomain.getCoverageTemporalCodes());

        // Descriptores de clase de recursos
        doc.addField(IndexacionEnumDomain.NM_TYPE.getCampo(), nucleoIstacDomain.getType());

        // Descriptores de produccion de un recurso
        doc.addField(IndexacionEnumDomain.NM_LAST_UPDATE.getCampo(), nucleoIstacDomain.getLastUpdate());

        return doc;
    }

    /**
     * @param nucleoIstacDomain
     * @return
     */
    private SolrInputDocument nucleoMetadatosContent2SolrDocument(NucleoMetadatos nucleoIstacDomain) {
        // Se crea un documento SOLR
        SolrInputDocument doc = nucleoMetadatos2SolrDocument(nucleoIstacDomain);
        // Pedimos el contenido del nodo
        try {
            doc.addField(IndexacionEnumDomain.TK_CONTENIDO.getCampo(), conexionAlfrescoService.obtenerContenidoNodo(nucleoIstacDomain.getIdentifierUniversal()));
        } catch (ServiceExcepcion e) {
            log.warn("No se ha indexado el contenido del nodo ha ocurrido un error al obtener el contenido");
        }
        return doc;
    }

}
