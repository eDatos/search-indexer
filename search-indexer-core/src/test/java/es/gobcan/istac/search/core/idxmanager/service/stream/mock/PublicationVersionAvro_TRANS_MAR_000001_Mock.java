package es.gobcan.istac.search.core.idxmanager.service.stream.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.siemac.metamac.statistical.resources.core.stream.messages.DatetimeAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ExternalItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.IdentifiableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.InternationalStringItemAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.LifecycleStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NameableStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.NextVersionTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.ProcStatusEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.PublicationVersionAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.RelatedResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.SiemacMetadataStatisticalResourceAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.StatisticalResourceTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeExternalArtefactsEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.TypeRelatedResourceEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionRationaleTypeEnumAvro;
import org.siemac.metamac.statistical.resources.core.stream.messages.VersionableStatisticalResourceAvro;

public class PublicationVersionAvro_TRANS_MAR_000001_Mock {

    public static PublicationVersionAvro createPublicationVersionAvro() {

        // @formatter:off
        return PublicationVersionAvro.newBuilder()
            .setSiemacMetadataStatisticalResource(createSiemacMetadataStatisticalResourceAvro())
            .setPublication(createPublicationAvro())
            .setHasPart(createHasPart())
            .build();
        // @formatter:on
    }

    private static SiemacMetadataStatisticalResourceAvro createSiemacMetadataStatisticalResourceAvro() {

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
            .setType(StatisticalResourceTypeEnumAvro.COLLECTION)
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

    private static LifecycleStatisticalResourceAvro createLifecycleStatisticalResourceAvro() {

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
                .setReplacesVersion(null)
                .setMaintainer(createMaintainer_ISTAC())
                .setIsReplacedByVersion(null)
                .build();
        // @formatter:on
    }

    private static VersionableStatisticalResourceAvro createVersionableStatisticalResourceAvro() {

        // @formatter:off
        return VersionableStatisticalResourceAvro.newBuilder()
                .setNameableStatisticalResource(createNameableStatisticalResourceAvro())
                .setNextVersionDate(null)
                .setValidFrom(null)
                .setValidTo(null)
                .setVersionRationale(null)
                .setNextVersion(NextVersionTypeEnumAvro.NO_UPDATES)
                .setVersionLogic("001.000")
                .setVersionRationaleTypes(Arrays.asList(VersionRationaleTypeEnumAvro.MINOR_METADATA))
                .build();
        // @formatter:on
    }

    private static NameableStatisticalResourceAvro createNameableStatisticalResourceAvro() {

        // @formatter:off
        return NameableStatisticalResourceAvro.newBuilder()
                .setIdentifiableStatisticalResource(createIdentifiableStatisticalResourceAvro())
                .setTitle(createInternationalStringAvro("collection1", "es"))
                .setDescription(createInternationalStringAvro("Descripcion<span class=\"Apple-tab-span\" style=\"white-space:pre\">\t</span>", "es"))
                .build();
        // @formatter:on
    }

    private static IdentifiableStatisticalResourceAvro createIdentifiableStatisticalResourceAvro() {

        // @formatter:off
        return IdentifiableStatisticalResourceAvro.newBuilder()
                .setCode("PUB_000001")
                .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Collection=DREM:TRANS_MAR_000001(001.000)")
                .setStatisticalOperation(createStatisticalOperation_TRANS_MAR())
                .build();
        // @formatter:on
    }

    private static PublicationAvro createPublicationAvro() {

        // @formatter:off
        return PublicationAvro.newBuilder()
                .setIdentifiableStatisticalResource(createIdentifiableStatisticalResourceAvro())
                .setPublicationVersionsUrns(createPublicationVersionsUrns())
                .build();
        // @formatter:on
    }

    private static List<String> createPublicationVersionsUrns() {
        List<String> result = new ArrayList<>();
        result.add("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=DREM:TRANS_MAR_000002(001.000)");
        result.add("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=DREM:TRANS_MAR_000002(001.001)");
        return result;
    }

    private static List<RelatedResourceAvro> createHasPart() {
        List<RelatedResourceAvro> results = new ArrayList<>();

        // @formatter:off
        results.add(RelatedResourceAvro.newBuilder()
                .setCode("TRANS_MAR_000001")
                .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=DREM:TRANS_MAR_000001(001.001)")
                .setTitle(createInternationalStringAvro("Transportes Maritimos 1995 - 2000", "es", "Série dos Transportes Marítimos 1995-2000", "pt"))
                .setStatisticalOperationUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=TRANS_MAR")
                .setType(TypeRelatedResourceEnumAvro.DATASET_VERSION)
                .build());
        // @formatter:on

        // @formatter:off
        results.add(RelatedResourceAvro.newBuilder()
                .setCode("TRANS_MAR_000002")
                .setUrn("urn:siemac:org.siemac.metamac.infomodel.statisticalresources.Dataset=DREM:TRANS_MAR_000002(001.002)")
                .setTitle(createInternationalStringAvro("Transportes Marítimos a partir del 2001", "es", "Série dos Transportes Marítimos desde 2001", "pt"))
                .setStatisticalOperationUrn("urn:siemac:org.siemac.metamac.infomodel.statisticaloperations.Operation=TRANS_MAR")
                .setType(TypeRelatedResourceEnumAvro.DATASET_VERSION)
                .build());
        // @formatter:on

        return results;
    }

    private static DatetimeAvro createDatetimeAvro(long instant, String timezone) {
        // @formatter:off
        return DatetimeAvro.newBuilder()
                .setInstant(instant)
                .setTimezone(timezone)
                .build();
        // @formatter:on
    }

    private static ExternalItemAvro createStatisticalOperation_TRANS_MAR() {

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

    private static ExternalItemAvro createMaintainer_ISTAC() {

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

    private static ExternalItemAvro createLanguage_ES() {

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

    private static ExternalItemAvro createCommonMetadata_ISTAC() {

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

    private static ExternalItemAvro createExternalItemAvro_CONSEJERIA_INDUSTRIA() {

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

    private static InternationalStringAvro createInternationalStringAvro(String label, String locale) {
        InternationalStringItemAvro item = InternationalStringItemAvro.newBuilder().setLabel(label).setLocale(locale).build();
        InternationalStringAvro internationalStringAvro = InternationalStringAvro.newBuilder().setLocalisedStrings(Arrays.asList(item)).build();
        return internationalStringAvro;
    }

    private static InternationalStringAvro createInternationalStringAvro(String label1, String locale1, String label2, String locale2) {
        InternationalStringItemAvro item = InternationalStringItemAvro.newBuilder().setLabel(label1).setLocale(locale1).build();
        InternationalStringItemAvro item2 = InternationalStringItemAvro.newBuilder().setLabel(label2).setLocale(locale2).build();
        InternationalStringAvro internationalStringAvro = InternationalStringAvro.newBuilder().setLocalisedStrings(Arrays.asList(item, item2)).build();
        return internationalStringAvro;
    }

}
