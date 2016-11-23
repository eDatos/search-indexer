package es.gobcan.istac.search.core.idxmanager.service.stream;

import java.util.ArrayList;
import java.util.List;

import org.apache.avro.specific.SpecificRecordBase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.formula.functions.T;
import org.apache.solr.common.SolrInputDocument;
import org.joda.time.DateTime;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.idxmanager.domain.dom.OrigenRecursoDomain;
import es.gobcan.istac.idxmanager.domain.dom.TypeNMDomain;
import es.gobcan.istac.idxmanager.domain.modelo.IndexacionEnumDomain;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcion;
import es.gobcan.istac.search.core.idxmanager.service.excepcion.ServiceExcepcionTipo;
import es.gobcan.istac.search.core.idxmanager.service.solr.SolrService;
import es.gobcan.istac.search.core.idxmanager.service.util.StreamUtils;

@Service
public class MetamacIndexerServiceImpl implements MetamacIndexerService<SpecificRecordBase> {

    protected static Log LOGGER = LogFactory.getLog(MetamacIndexerService.class);

    @Autowired
    private SolrService solr = null;

    @Override
    public void index(SpecificRecordBase message) throws ServiceExcepcion {
        if (message instanceof DatasetVersionAvro) {
            indexarDatasetVersion((DatasetVersionAvro) message);
        } else if (message instanceof PublicationVersionAvro) {
            indexarPublicationVersionAvro((PublicationVersionAvro) message);
        }

    }

    private void indexarPublicationVersionAvro(PublicationVersionAvro publicationVersionAvro) throws ServiceExcepcion {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        TypeNMDomain collectionTypeNMDomain = TypeNMDomain.COLLECTION_DSP;

        // Check Avro Message
        checkIfHirarchyIsComplete(publicationVersionAvro.getSiemacMetadataStatisticalResource());
        IdentifiableStatisticalResourceAvro identifiableStatisticalResourceAvro = publicationVersionAvro.getSiemacMetadataStatisticalResource().getLifecycleStatisticalResource()
                .getVersionableStatisticalResource().getNameableStatisticalResource().getIdentifiableStatisticalResource();

        // Campos el indice auxiliares
        toIndexMetadata(solrInputDocument, identifiableStatisticalResourceAvro, collectionTypeNMDomain);

        // Identificadores
        toIdentifiersMetadata(publicationVersionAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Clasificadores tematicos de contenido
        toContentClassifiersMetadata(publicationVersionAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Descriptores de contenido
        toContentDescriptorsMetadata(publicationVersionAvro, solrInputDocument);

        // Descriptores de clase de recursos
        toClassDescriptorMetadata(solrInputDocument, collectionTypeNMDomain);

        // Descriptores de produccion de un recurso
        toProductionDescriptorMetadata(publicationVersionAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Perform Indexation
        removeReplacesVersion(publicationVersionAvro.getSiemacMetadataStatisticalResource());
        solr.insertarDoc(solrInputDocument);
        solr.commit();
        LOGGER.debug("Indexado recurso con URN: " + identifiableStatisticalResourceAvro.getUrn());
    }

    private void indexarDatasetVersion(DatasetVersionAvro datasetVersionAvroAvro) throws ServiceExcepcion {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        TypeNMDomain datasetTypeNMDomain = TypeNMDomain.DATASET_DSC;

        // Check Avro Message
        checkIfHirarchyIsComplete(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource());
        IdentifiableStatisticalResourceAvro identifiableStatisticalResourceAvro = datasetVersionAvroAvro.getSiemacMetadataStatisticalResource().getLifecycleStatisticalResource()
                .getVersionableStatisticalResource().getNameableStatisticalResource().getIdentifiableStatisticalResource();

        // Campos el indice auxiliares
        toIndexMetadata(solrInputDocument, identifiableStatisticalResourceAvro, datasetTypeNMDomain);

        // Identificadores
        toIdentifiersMetadata(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Clasificadores tematicos de contenido
        toContentClassifiersMetadata(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Descriptores de contenido
        toContentDescriptorsMetadata(datasetVersionAvroAvro, solrInputDocument);

        // Descriptores de clase de recursos
        toClassDescriptorMetadata(solrInputDocument, datasetTypeNMDomain);

        // Descriptores de produccion de un recurso
        toProductionDescriptorMetadata(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Perform Indexation
        removeReplacesVersion(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource());
        solr.insertarDoc(solrInputDocument);
        solr.commit();
        LOGGER.debug("Indexado recurso con URN: " + identifiableStatisticalResourceAvro.getUrn());
    }

    private void removeReplacesVersion(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResourceAvro) {
        RelatedResourceAvro replacesVersion = siemacMetadataStatisticalResourceAvro.getLifecycleStatisticalResource().getReplacesVersion();
        if (replacesVersion != null) {
            try {
                solr.eliminarDoc(replacesVersion.getUrn());
                LOGGER.debug("Desindexado recurso con URN: " + replacesVersion.getUrn());
            } catch (ServiceExcepcion e) {
                LOGGER.error("Imposible desindexar recurso con URN: " + replacesVersion.getUrn(), e);
            }
        }
    }

    private void toProductionDescriptorMetadata(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResourceAvro, SolrInputDocument solrInputDocument) {
        DateTime lastUpdateDateTime = StreamUtils.avro2Do(siemacMetadataStatisticalResourceAvro.getLastUpdate());
        solrInputDocument.addField(IndexacionEnumDomain.NM_LAST_UPDATE.getCampo(), lastUpdateDateTime.toDate());
    }

    private void toClassDescriptorMetadata(SolrInputDocument solrInputDocument, TypeNMDomain typeNMDomain) {
        solrInputDocument.addField(IndexacionEnumDomain.NM_TYPE.getCampo(), typeNMDomain.getSiglas());
    }

    private void toIndexMetadata(SolrInputDocument solrInputDocument, IdentifiableStatisticalResourceAvro identifiableStatisticalResourceAvro, TypeNMDomain typeNMDomain) {
        solrInputDocument.addField(IndexacionEnumDomain.ID.getCampo(), identifiableStatisticalResourceAvro.getUrn());
        solrInputDocument.addField(IndexacionEnumDomain.ORIGENRECURSO.getCampo(), OrigenRecursoDomain.METAMAC_STAT_RESOURCES.getSiglas());
        // FORMATO: En BUSCAISTAC-67 se decide que para los Dataset, el formato es NULL. Para Publicaciones, el formato es HTML
        if (TypeNMDomain.COLLECTION_DSP.equals(typeNMDomain)) {
            solrInputDocument.addField(IndexacionEnumDomain.FORMATO.getCampo(), OrigenRecursoDomain.METAMAC_STAT_RESOURCES.getSiglas());
        }
    }

    private void toContentClassifiersMetadata(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResourceAvro, SolrInputDocument solrInputDocument) {
        IdentifiableStatisticalResourceAvro identifiableStatisticalResourceAvro = siemacMetadataStatisticalResourceAvro.getLifecycleStatisticalResource().getVersionableStatisticalResource()
                .getNameableStatisticalResource().getIdentifiableStatisticalResource();

        ExternalItemAvro statisticalOperation = identifiableStatisticalResourceAvro.getStatisticalOperation();
        String nm_surveyTitle = extractLocalisedMessageFromInternationalString(statisticalOperation.getTitle());
        solrInputDocument.addField(IndexacionEnumDomain.NM_SURVEY_CODE.getCampo(), statisticalOperation.getCode());
        solrInputDocument.addField(IndexacionEnumDomain.NM_SURVEY_TITLE.getCampo(), nm_surveyTitle);

        // TODO KAFKA: Los Sujects se dejan fuera por ahora. Esta información sólo está disponible en la operación estadística asociada
        // y por tanto no viene en el DatasetVersionAvro.
        // solrInputDocument.addField(IndexacionEnumDomain.NM_SUBJECT_AREAS.getCampo(), nucleoIstacDomain.getSubjectAreas());
        // solrInputDocument.addField(IndexacionEnumDomain.NM_SUBJECT_CODES.getCampo(), nucleoIstacDomain.getSubjectCodes());
    }

    private void toIdentifiersMetadata(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResourceAvro, SolrInputDocument solrInputDocument) {
        NameableStatisticalResourceAvro nameableStatisticalResource = siemacMetadataStatisticalResourceAvro.getLifecycleStatisticalResource().getVersionableStatisticalResource()
                .getNameableStatisticalResource();

        String nm_title = extractLocalisedMessageFromInternationalString(nameableStatisticalResource.getTitle());
        if (nm_title != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_TITLE.getCampo(), nm_title);
        }

        String nm_subTitle = extractLocalisedMessageFromInternationalString(siemacMetadataStatisticalResourceAvro.getSubtitle());
        if (nm_subTitle != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_SUBTITLE.getCampo(), nm_subTitle);
        }

        String nm_titleAlternative = extractLocalisedMessageFromInternationalString(siemacMetadataStatisticalResourceAvro.getTitleAlternative());
        if (nm_titleAlternative != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_TITLE_ALTERNATIVE.getCampo(), nm_titleAlternative);
        }
    }

    private void toContentDescriptorsMetadata(DatasetVersionAvro datasetVersionAvroAvro, SolrInputDocument solrInputDocument) {
        toContentDescriptorsCommonMetadata(datasetVersionAvroAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);

        // Coverage Spatial
        List<ExternalItemAvro> geographicCoverageList = datasetVersionAvroAvro.getGeographicCoverage();
        List<String> coverageSpatial = new ArrayList<>();
        List<String> coverageSpatialCodes = new ArrayList<>();
        for (ExternalItemAvro externalItemAvro : geographicCoverageList) {
            String nm_title = extractLocalisedMessageFromInternationalString(externalItemAvro.getTitle());
            coverageSpatialCodes.add(externalItemAvro.getCode());
            coverageSpatial.add(nm_title);
        }

        if (!coverageSpatial.isEmpty()) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_COVERAGE_SPATIAL.getCampo(), coverageSpatial);
        }

        if (!coverageSpatialCodes.isEmpty()) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_COVERAGE_SPATIAL_CODES.getCampo(), coverageSpatialCodes);
        }

        // Coverage Temporal
        List<TemporalCodeAvro> temporalCoverageList = datasetVersionAvroAvro.getTemporalCoverage();
        List<String> coverageTemporal = new ArrayList<>();
        List<String> coverageTemporalCodes = new ArrayList<>();
        for (TemporalCodeAvro temporalCodeAvro : temporalCoverageList) {
            coverageTemporalCodes.add(temporalCodeAvro.getIdentifier());
            coverageTemporal.add(temporalCodeAvro.getTitle());
        }

        if (!coverageTemporal.isEmpty()) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_COVERAGE_TEMPORAL.getCampo(), coverageTemporal);
        }

        if (!coverageTemporalCodes.isEmpty()) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_COVERAGE_TEMPORAL_CODES.getCampo(), coverageTemporalCodes);
        }
    }

    private void toContentDescriptorsMetadata(PublicationVersionAvro publicationVersionAvro, SolrInputDocument solrInputDocument) {
        toContentDescriptorsCommonMetadata(publicationVersionAvro.getSiemacMetadataStatisticalResource(), solrInputDocument);
    }

    private void toContentDescriptorsCommonMetadata(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResource, SolrInputDocument solrInputDocument) {
        NameableStatisticalResourceAvro nameableStatisticalResource = siemacMetadataStatisticalResource.getLifecycleStatisticalResource().getVersionableStatisticalResource()
                .getNameableStatisticalResource();

        // Description
        String nm_description = extractLocalisedMessageFromInternationalString(nameableStatisticalResource.getDescription());
        if (nm_description != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_DESCRIPTION.getCampo(), nm_description);
        }

        // Abstract
        String nm_abstract = extractLocalisedMessageFromInternationalString(siemacMetadataStatisticalResource.getAbstractLogic());
        if (nm_abstract != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_ABSTRACT.getCampo(), nm_abstract);
        }

        // Keywords
        String nm_keywords = extractLocalisedMessageFromInternationalString(siemacMetadataStatisticalResource.getKeywords());
        if (nm_keywords != null) {
            solrInputDocument.addField(IndexacionEnumDomain.NM_KEYWORDS.getCampo(), nm_keywords);
        }
    }

    private void checkIfHirarchyIsComplete(SiemacMetadataStatisticalResourceAvro siemacMetadataStatisticalResource) throws ServiceExcepcion {
        String message = "Avro Message for " + T.class.getName() + " is incomplete";
        if (siemacMetadataStatisticalResource == null) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL, message);
        }

        if (siemacMetadataStatisticalResource.getLifecycleStatisticalResource() == null) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL, message);
        }

        LifecycleStatisticalResourceAvro lifecycleStatisticalResource = siemacMetadataStatisticalResource.getLifecycleStatisticalResource();
        if (lifecycleStatisticalResource.getVersionableStatisticalResource() == null) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL, message);
        }

        VersionableStatisticalResourceAvro versionableStatisticalResource = lifecycleStatisticalResource.getVersionableStatisticalResource();
        if (versionableStatisticalResource.getNameableStatisticalResource() == null) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL, message);
        }

        NameableStatisticalResourceAvro nameableStatisticalResource = versionableStatisticalResource.getNameableStatisticalResource();
        if (nameableStatisticalResource.getIdentifiableStatisticalResource() == null) {
            throw new ServiceExcepcion(ServiceExcepcionTipo.SERVICE_GENERAL, message);
        }

        return;
    }

    private String extractLocalisedMessageFromInternationalString(InternationalStringAvro internationalStringAvro) {

        if (internationalStringAvro == null) {
            return null;
        }

        for (InternationalStringItemAvro internationalStringItemAvro : internationalStringAvro.getLocalisedStrings()) {
            if ("ES".equals(internationalStringItemAvro.getLocale().toUpperCase())) {
                return internationalStringItemAvro.getLabel();
            }
        }

        return null;
    }

}