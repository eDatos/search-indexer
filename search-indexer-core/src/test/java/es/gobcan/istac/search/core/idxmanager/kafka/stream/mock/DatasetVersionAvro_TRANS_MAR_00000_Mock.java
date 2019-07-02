package es.gobcan.istac.search.core.idxmanager.kafka.stream.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.stream.messages.AttributeValueAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CategorisationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.CodeDimensionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatasetVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticOfficialityAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TemporalCodeAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeRelatedResourceEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class DatasetVersionAvro_TRANS_MAR_00000_Mock {

    public static DatasetVersionAvro createDataSetVersionAvro() {

        // @formatter:off
        return DatasetVersionAvro.newBuilder()
            .setSiemacMetadataStatisticalResource(createSiemacMetadataStatisticalResourceAvro())
            .setDateStart(createDatetimeAvro(978307200000l, null))
            .setDateEnd(createDatetimeAvro(1388534399999l, null))
            .setDatasetRepositoryId("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .setFormatExtentDimensions(3)
            .setDateNextUpdate(null)
            .setUserModifiedDateNextUpdate(false)
            .setDataset(createDatasetAvro())
            .setRelatedDsd(createRelateDsd_DSD_MARINE_TRANSPORT())
            .setUpdateFrequency(createStatisticalOperation_TRANS_MAR())
            .setStatisticOfficiality(createStatisticOfficialityAvro())
            .setBibliographicCitation(null)
            .setDimensionsCoverage(createCodeDimensionAvroList())
            .setAttributesCoverage(new ArrayList<>())
            .setCategorisations(new ArrayList<>())
            .setGeographicCoverage(createGeographicCoverageList())
            .setTemporalCoverage(createTemporalCodeAvroList())
            .setMeasureCoverage(createMeasureCoverageAvroList())
            .setGeographicGranularities(createGeographicGranularitiesAvroList())
            .setTemporalGranularities(createTemporalGranularitiesAvroList())
            .setStatisticalUnit(new ArrayList<>())
            .setIsPartOf(null)
            .build();
        // @formatter:on
    }

    public static SiemacMetadataStatisticalResourceAvro createSiemacMetadataStatisticalResourceAvro() {

        // @formatter:off
        return SiemacMetadataStatisticalResourceAvro.newBuilder()
            .setLifecycleStatisticalResource(createLifecycleStatisticalResourceAvro())
            .setResourceCreatedDate(null)
            .setLastUpdate(createDatetimeAvro(1478261089449l, null))
            .setNewnessUntilDate(null)
            .setCopyrightedDate(null)
            .setLanguage(createLanguage_ES())
            .setSubtitle(null)
            .setTitleAlternative(null)
            .setAbstractLogic(null)
            .setKeywords(null)
            .setCommonMetadata(createCommonMetadata_ISTAC())
            .setType(StatisticalResourceTypeEnumAvro.DATASET)
            .setCreator(createExternalItemAvro_CONSEJERIA_INDUSTRIA())
            .setConformsTo(null)
            .setConformsToInternal(null)
            .setReplaces(null)
            .setIsReplacedBy(null)
            .setAccessRights(null)
            .setLanguages(new ArrayList<>(Arrays.asList(createLanguage_ES())))
            .setStatisticalOperationInstances(new ArrayList<>())
            .setContributors(new ArrayList<>())
            .setPublishers(new ArrayList<>(Arrays.asList(createExternalItemAvro_CONSEJERIA_INDUSTRIA())))
            .setPublisherContributors(new ArrayList<>())
            .setMediators(new ArrayList<>())
            .build();
        // @formatter:on
    }

    public static LifecycleStatisticalResourceAvro createLifecycleStatisticalResourceAvro() {

        // @formatter:off
        return LifecycleStatisticalResourceAvro.newBuilder()
                .setVersionableStatisticalResource(createVersionableStatisticalResourceAvro())
                .setCreationDate(createDatetimeAvro(1478095061486l, null))
                .setCreationUser("METAMAC_ADMIN")
                .setProductionValidationDate(null)
                .setProductionValidationUser(null)
                .setDiffusionValidationDate(null)
                .setDiffusionValidationUser(null)
                .setRejectValidationDate(null)
                .setRejectValidationUser(null)
                .setPublicationDate(null)
                .setPublicationUser(null)
                .setLastVersion(true)
                .setProcStatus(ProcStatusEnumAvro.DRAFT)
                .setReplacesVersion(createReplacesVersion_TRANS_MAR_00000())
                .setMaintainer(createMaintainer_ISTAC())
                .setIsReplacedByVersion(null)
                .build();
        // @formatter:on
    }

    public static VersionableStatisticalResourceAvro createVersionableStatisticalResourceAvro() {

        // @formatter:off
        return VersionableStatisticalResourceAvro.newBuilder()
                .setNameableStatisticalResource(createNameableStatisticalResourceAvro())
                .setNextVersionDate(null)
                .setValidFrom(null)
                .setValidTo(null)
                .setVersionRationale(null)
                .setNextVersion(NextVersionTypeEnumAvro.NO_UPDATES)
                .setVersionLogic("002.000")
                .setVersionRationaleTypes(Arrays.asList(VersionRationaleTypeEnumAvro.MINOR_METADATA))
                .build();
        // @formatter:on
    }

    public static NameableStatisticalResourceAvro createNameableStatisticalResourceAvro() {

        // @formatter:off
        return NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(createIdentifiableStatisticalResourceAvro())
                .setTitle(createInternationalStringAvro("dataset1", "es"))
                .setDescription(createInternationalStringAvro("Descripcion<span class=\"Apple-tab-span\" style=\"white-space:pre\">\t</span>", "es"))
                .build();
        // @formatter:on
    }

    public static IdentifiableStatisticalResourceAvro createIdentifiableStatisticalResourceAvro() {

        // @formatter:off
        return IdentifiableStatisticalResourceAvro.newBuilder()
                .setCode("TRANS_MAR_000001")
                .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
                .setStatisticalOperation(createStatisticalOperation_TRANS_MAR())
                .build();
        // @formatter:on
    }

    public static DatasetAvro createDatasetAvro() {

        // @formatter:off
        return DatasetAvro.newBuilder()
                .setIdentifiableStatisticalResource(createIdentifiableStatisticalResourceAvro())
                .setDatasetVersionsUrns(Arrays.asList("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(001.000)", "urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)"))
                .setDimensionRepresentationMappings(new ArrayList<>())
                .build();
        // @formatter:on
    }

    public static StatisticOfficialityAvro createStatisticOfficialityAvro() {

        // @formatter:off
        return StatisticOfficialityAvro.newBuilder()
                .setIdentifier("OFFICIAL")
                .setDescription(createInternationalStringAvro("Estadística oficial", "es"))
                .build();
        // @formatter:on
    }

    public static List<TemporalCodeAvro> createTemporalCodeAvroList() {

        List<TemporalCodeAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2013")
                .setTitle("2013")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2012")
                .setTitle("2012")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2011")
                .setTitle("2011")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2010")
                .setTitle("2010")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2009")
                .setTitle("2009")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2008")
                .setTitle("2008")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2007")
                .setTitle("2007")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2006")
                .setTitle("2006")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2005")
                .setTitle("2005")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2004")
                .setTitle("2004")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2003")
                .setTitle("2003")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2002")
                .setTitle("2002")
                .build());
        // @formatter:on

        // @formatter:off
        results.add(TemporalCodeAvro.newBuilder()
                .setIdentifier("2001")
                .setTitle("2001")
                .build());
        // @formatter:on

        return results;
    }

    public static List<ExternalItemAvro> createMeasureCoverageAvroList() {

        List<ExternalItemAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("COMMERCIAL_VESSELES_ENTRIES")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=COMMERCIAL_VESSELES_ENTRIES")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Entradas de buques comerciales", "es", "Embarcações comerciais entradas (n.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).COMMERCIAL_VESSELES_ENTRIES")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CRUISE_SHIPS_DOCKED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=PASSENGERS_LANDED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Pasajeros desembarcados", "es", "Passageiros desembarcados (n.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).CRUISE_SHIPS_DOCKED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("TRANSIT_PASSENGERS_ON_CRUISES")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=TRANSIT_PASSENGERS_ON_CRUISES")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Pasajeros en tránsito en cruceros", "es", "Passageiros em trânsito em navios de cruzeiro", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).TRANSIT_PASSENGERS_ON_CRUISES")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("PASSENGERS_ONBOARD")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=PASSENGERS_ONBOARD")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Pasajeros embarcados", "es", "Passageiros embarcados (n.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).PASSENGERS_ONBOARD")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CONTAINERS_LOADED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=CONTAINERS_LOADED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Contenedores cargados", "es", "Contentores carregados (n.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).CONTAINERS_LOADED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("GOODS_UNLOADED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=GOODS_UNLOADED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Mercancías descargadas", "es", "Mercadorias descarregadas (ton.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).GOODS_UNLOADED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("GOODS_LOADED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=GOODS_LOADED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Mercancías cargadas", "es", "Mercadorias carregadas (ton.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).GOODS_LOADED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("DANGEROUS_GOODS_LOADED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=DANGEROUS_GOODS_LOADED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Mercancías peligrosas cargadas", "es", "Mercadorias perigosas carregadas (ton.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).DANGEROUS_GOODS_LOADED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("DANGEROUS_GOODS_UNLOADED")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=DANGEROUS_GOODS_UNLOADED")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Mercancías peligrosas descargadas", "es", "Mercadorias perigosas descarregadas (ton.)", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).DANGEROUS_GOODS_UNLOADED")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CONTAINERS_DOWNLOADED_COMPLETLY")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=CONTAINERS_DOWNLOADED_COMPLETLY")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Contenedores descargados completamente", "es", "dos quais descarregados cheios", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).CONTAINERS_DOWNLOADED_COMPLETLY")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CONTAINERS_LOADED_COMPLETLY")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/conceptSchemes/conceptScheme;id=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000)/concept;id=CONTAINERS_LOADED_COMPLETLY")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Contenedores cargados completamente", "es", "dos quais carregados cheios", "pt"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ISTAC:MARINE_TRANSPORTS_INDICATORS(01.000).CONTAINERS_LOADED_COMPLETLY")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONCEPT)
            .build());
        // @formatter:on
        return null;
    }

    public static List<CategorisationAvro> createCategorisationAvro() {

        return null;
    }

    public static List<AttributeValueAvro> createAttributeValueAvro() {

        return null;
    }

    public static List<CodeDimensionAvro> createCodeDimensionAvroList() {
        List<CodeDimensionAvro> result = new ArrayList<>();

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("TRANSIT_PASSENGERS_ON_CRUISES")
            .setTitle("Pasajeros en tránsito en cruceros")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("DANGEROUS_GOODS_LOADED")
            .setTitle("Mercancías peligrosas cargadas")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("DANGEROUS_GOODS_UNLOADED")
            .setTitle("Mercancías peligrosas descargadas")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("GOODS_LOADED")
            .setTitle("Mercancías cargadas")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("GOODS_UNLOADED")
            .setTitle("Mercancías descargadas")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("CONTAINERS_LOADED")
            .setTitle("Contenedores cargados")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("CRUISE_SHIPS_DOCKED")
            .setTitle("Cruceros atracados")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("CONTAINERS_LOADED_COMPLETLY")
            .setTitle("Contenedores cargados completamente")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("COMMERCIAL_VESSELES_ENTRIES")
            .setTitle("Entradas de buques comerciales")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("PASSENGERS_ONBOARD")
            .setTitle("Pasajeros embarcados")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("CONTAINERS_DOWNLOADED")
            .setTitle("Contenedores descargados")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("CONTAINERS_DOWNLOADED_COMPLETLY")
            .setTitle("Contenedores descargados completamente")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("INDICATORS")
            .setIdentifier("PASSENGERS_LANDED")
            .setTitle("Pasajeros desembarcados")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2013")
            .setTitle("2013")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2012")
            .setTitle("2012")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2011")
            .setTitle("2011")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2009")
            .setTitle("2009")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2008")
            .setTitle("2008")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2007")
            .setTitle("2007")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2006")
            .setTitle("2006")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2005")
            .setTitle("2005")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2004")
            .setTitle("2004")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2003")
            .setTitle("2003")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2002")
            .setTitle("2002")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("TIME_PERIOD")
            .setIdentifier("2001")
            .setTitle("2001")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("REGION")
            .setIdentifier("PORTO_SANTO")
            .setTitle("Porto Santo")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("REGION")
            .setIdentifier("FUNCHAL")
            .setTitle("Funchal")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        // @formatter:off
        result.add(CodeDimensionAvro.newBuilder()
            .setDsdComponentId("REGION")
            .setIdentifier("CANICAL")
            .setTitle("Caniçal")
            .setDatasetVersionUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(002.000)")
            .build());
        // @formatter:on

        return result;
    }

    public static List<ExternalItemAvro> createGeographicCoverageList() {
        List<ExternalItemAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CANICAL")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_MADEIRA_ISLANDS(01.000)/code;id=CANICAL")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Caniçal", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_MADEIRA_ISLANDS(01.000).CANICAL")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("FUNCHAL")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_MADEIRA_ISLANDS(01.000)/code;id=FUNCHAL")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Funchal", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_MADEIRA_ISLANDS(01.000).FUNCHAL")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build());
        // @formatter:on

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("PORTO_SANTO")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_MADEIRA_ISLANDS(01.000)/code;id=PORTO_SANTO")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Porto Santo", "es"))
            .setUrn("\"urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_MADEIRA_ISLANDS(01.000).PORTO_SANTO")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build());
        // @formatter:on

        return results;
    }

    public static List<ExternalItemAvro> createGeographicGranularitiesAvroList() {
        List<ExternalItemAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CANICAL")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_MADEIRA_ISLANDS(01.000)/code;id=CANICAL")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Caniçal", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_MADEIRA_ISLANDS(01.000).CANICAL")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build());
        // @formatter:on

        return results;
    }

    public static List<ExternalItemAvro> createTemporalGranularitiesAvroList() {
        List<ExternalItemAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(ExternalItemAvro.newBuilder()
            .setCode("CANICAL")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_MADEIRA_ISLANDS(01.000)/code;id=CANICAL")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Caniçal", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_MADEIRA_ISLANDS(01.000).CANICAL")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build());
        // @formatter:on

        return results;
    }

    public static DatetimeAvro createDatetimeAvro(long instant, String timezone) {
        // @formatter:off
        return DatetimeAvro.newBuilder()
                .setInstant(instant)
                .setTimezone(timezone)
                .build();
        // @formatter:on
    }

    public static ExternalItemAvro createStatisticalOperation_TRANS_MAR() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("TRANS_MAR")
            .setCodeNested(null)
            .setManagementAppUrl("/#operations/operation;id=TRANS_MAR")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Transportes Marítimos", "pt", "Transporte Marítimo", "es"))
            .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=TRANS_MAR")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.STATISTICAL_OPERATION)
            .build();
        // @formatter:on
    }

    public static RelatedResourceAvro createReplacesVersion_TRANS_MAR_00000() {
        // @formatter:off
        return RelatedResourceAvro.newBuilder()
                .setCode("TRANS_MAR_000001")
                .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=ISTAC:TRANS_MAR_000001(001.000)")
                .setTitle(createInternationalStringAvro("dataset1", "es"))
                .setStatisticalOperationUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=TRANS_MAR")
                .setType(TypeRelatedResourceEnumAvro.DATASET_VERSION)
                .build();
        // @formatter:on
    }

    public static ExternalItemAvro createMaintainer_ISTAC() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("ISTAC")
            .setCodeNested("ISTAC")
            .setManagementAppUrl("/#structuralResources/organisationSchemes/organisationScheme;type=AGENCY_SCHEME;id=SDMX:AGENCIES(1.0)/organisation;id=ISTAC")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Instituto Canario de Estadística", "es", "ISTAC", "en"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.base.Agency=SDMX:AGENCIES(1.0).ISTAC")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.AGENCY)
            .build();
        // @formatter:on
    }

    public static ExternalItemAvro createLanguage_ES() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("ES")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/codelists/codelist;id=ISTAC:CL_LANGUAJES(01.000)/code;id=ES")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Español", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.codelist.Code=ISTAC:CL_LANGUAJES(01.000).ES")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CODE)
            .build();
        // @formatter:on
    }

    public static ExternalItemAvro createCommonMetadata_ISTAC() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("ISTAC")
            .setCodeNested(null)
            .setManagementAppUrl("/#configurations/configuration;id=ISTAC")
            .setSelfLink(null)
            .setTitle(null)
            .setUrn("urn:siemac:org.siemac.metamac.infomodel.commonmetadata.CommonMetadata=ISTAC")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.CONFIGURATION)
            .build();
        // @formatter:on
    }

    public static ExternalItemAvro createExternalItemAvro_CONSEJERIA_INDUSTRIA() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("CONSEJERIA_CULTURA")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/organisationSchemes/organisationScheme;type=ORGANISATION_UNIT_SCHEME;id=ISTAC:UNIDADES_GOBCAN(02.001)/organisation;id=CONSEJERIA_CULTURA")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("Consejería de Cultura, Deportes, Políticas Sociales y Vivienda", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.base.OrganisationUnit=ISTAC:UNIDADES_GOBCAN(02.001).CONSEJERIA_CULTURA")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.ORGANISATION_UNIT)
            .build();
        // @formatter:on
    }

    public static ExternalItemAvro createRelateDsd_DSD_MARINE_TRANSPORT() {

        // @formatter:off
        return ExternalItemAvro.newBuilder()
            .setCode("DSD_MARINE_TRANSPORT")
            .setCodeNested(null)
            .setManagementAppUrl("/#structuralResources/dsds/dsd;id=ISTAC:DSD_MARINE_TRANSPORT(01.000)")
            .setSelfLink(null)
            .setTitle(createInternationalStringAvro("DSD de Transporte Marítimo", "es"))
            .setUrn("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ISTAC:DSD_MARINE_TRANSPORT(01.000)")
            .setUrnProvider(null)
            .setType(TypeExternalArtefactsEnumAvro.DATASTRUCTURE)
            .build();
        // @formatter:on
    }

    public static InternationalStringAvro createInternationalStringAvro(String label, String locale) {
        InternationalStringItemAvro item = InternationalStringItemAvro.newBuilder().setLabel(label).setLocale(locale).build();
        InternationalStringAvro internationalStringAvro = InternationalStringAvro.newBuilder().setLocalisedStrings(Arrays.asList(item)).build();
        return internationalStringAvro;
    }

    public static InternationalStringAvro createInternationalStringAvro(String label1, String locale1, String label2, String locale2) {
        InternationalStringItemAvro item = InternationalStringItemAvro.newBuilder().setLabel(label1).setLocale(locale1).build();
        InternationalStringItemAvro item2 = InternationalStringItemAvro.newBuilder().setLabel(label2).setLocale(locale2).build();
        InternationalStringAvro internationalStringAvro = InternationalStringAvro.newBuilder().setLocalisedStrings(Arrays.asList(item, item2)).build();
        return internationalStringAvro;
    }

}
