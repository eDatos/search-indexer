package es.gobcan.istac.jaxi.pxservice.api.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class NucleoMetadatos implements Serializable {

    private static final long serialVersionUID = 1644159674937346057L;

    // -------------------------------------------
    // Metadatos versiones
    // -------------------------------------------
    private String version = null;
    private List<String> versionRationaleType = null;
    private String versionRationale = null;
    private Date dateVersion = null;
    private Date dateNextVersion = null;
    private String versionResponsibilityCreator = null;
    private List<String> versionResponsibilityContributor = null;
    private String versionResponsibilitySubmitted = null;
    private List<String> versionResponsibilityAccepted = null;
    private String versionResponsibilityIssued = null;
    private String versionResponsibilityOutofprint = null;
    private String procStatus = null;

    // -------------------------------------------
    // Metadatos identificadores
    // -------------------------------------------
    private String identifier = null;
    private String identifierUniversal = null;
    private String title = null;
    private List<String> subtitle = null;
    private List<String> titleAlternative = null;

    // -------------------------------------------
    // Metadatos Idioma
    // -------------------------------------------
    private String language = null;
    private List<String> languages = null;

    // --------------------------------------------------
    // Metadatos Clasificadores Temáticos del contenido
    // --------------------------------------------------
    private String surveyTitle = null;
    private String surveyAlternative = null;
    private String surveyCode = null;
    private List<String> subjectAreas = null;
    private List<String> subjectCodes = null;

    // -------------------------------------------
    // Metadatos Descriptores de Contenidos
    // -------------------------------------------
    private List<String> description = null;
    private List<String> abstractDescription = null;
    private List<String> keywords = null;
    private List<String> coverageSpatial = null;
    private List<String> coverageSpatialCodes = null;
    private List<String> coverageTemporal = null;
    private List<String> coverageTemporalCodes = null;

    // -------------------------------------------
    // Metadatos descriptores de clase
    // -------------------------------------------
    private List<String> type = null;
    private List<String> format = null;
    private List<String> formatExtent = null;

    // -------------------------------------------
    // Metadatos Descriptores de Producción
    // -------------------------------------------
    private String creator = null;
    private List<String> contributor = null;
    private Date dateCreated = null;
    private Date lastUpdate = null;
    private Date dateSubmitted = null;
    private Date dateNextUpdate = null;
    private String updateFrequency = null;
    private String rangeDatesValid = null;
    private List<String> conformsTo = null;

    // -------------------------------------------
    // Metadatos Descriptores de publicación
    // -------------------------------------------
    private List<String> publisher = null;
    private List<String> mediator = null;
    private Date dateAccepted = null;
    private Date dateIssued = null;
    private String rangeDatesAvailable = null;
    private List<String> audience = null;
    private List<String> educationLevel = null;
    private List<String> bibliographicCitation = null;

    // -------------------------------------------
    // Metadatos Descriptores de relaciones
    // -------------------------------------------
    private List<String> source = null;
    private String isVersionOf = null;
    private List<String> hasVersion = null;
    private List<String> replaces = null;
    private String isReplacedBy = null;
    private List<String> requires = null;
    private List<String> isRequiredBy = null;
    private List<String> hasPart = null;
    private List<String> isPartOf = null;
    private List<String> isReferencedBy = null;
    private List<String> references = null;
    private List<String> isFormatOf = null;
    private List<String> hasFormat = null;

    // -------------------------------------------
    // Descriptores de Propiedad Intelectual
    // -------------------------------------------
    private List<String> rightsHolder = null;
    private Date dateCopyrighted = null;
    private List<String> license = null;
    private String accessRights = null;

    // -------------------------------------------
    // Metadatos de interacción con usuarios
    // -------------------------------------------
    // Para no limitar el tamanio del número
    private String visitCount = null;
    // Contador en segundos del tiempo que ha permanecido el usuario consultando el recurso
    private String visitTime = null;
    // Fecha de la �ltima visita
    private Date visitLastDate = null;
    private String shareCount = null;
    private String downloadCount = null;
    private String bookmarkCount = null;
    private List<String> tags = null;

    /*
     * Getter and Setter
     */
    public String getVersion() {
        return this.version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getVersionRationaleType() {
        if (this.versionRationaleType == null) {
            this.versionRationaleType = new ArrayList<String>();
        }
        return this.versionRationaleType;
    }

    public void setVersionRationaleType(List<String> versionRationaleType) {
        this.versionRationaleType = versionRationaleType;
    }

    public String getVersionRationale() {
        return this.versionRationale;
    }

    public void setVersionRationale(String versionRationale) {
        this.versionRationale = versionRationale;
    }

    public Date getDateVersion() {
        return new Date(this.dateVersion.getTime());
    }

    public void setDateVersion(Date dateVersion) {
        this.dateVersion = new Date(dateVersion.getTime());
    }

    public Date getDateNextVersion() {
        return new Date(this.dateNextVersion.getTime());
    }

    public void setDateNextVersion(Date dateNextVersion) {
        this.dateNextVersion = new Date(dateNextVersion.getTime());
    }

    public String getVersionResponsibilityCreator() {
        return this.versionResponsibilityCreator;
    }

    public void setVersionResponsibilityCreator(String versionResponsibilityCreator) {
        this.versionResponsibilityCreator = versionResponsibilityCreator;
    }

    public List<String> getVersionResponsibilityContributor() {
        if (this.versionResponsibilityContributor == null) {
            this.versionResponsibilityContributor = new ArrayList<String>();
        }
        return this.versionResponsibilityContributor;
    }

    public void setVersionResponsibilityContributor(List<String> versionResponsibilityContributor) {
        this.versionResponsibilityContributor = versionResponsibilityContributor;
    }

    public String getVersionResponsibilitySubmitted() {
        return this.versionResponsibilitySubmitted;
    }

    public void setVersionResponsibilitySubmitted(String versionResponsibilitySubmitted) {
        this.versionResponsibilitySubmitted = versionResponsibilitySubmitted;
    }

    public List<String> getVersionResponsibilityAccepted() {
        if (this.versionResponsibilityAccepted == null) {
            this.versionResponsibilityAccepted = new ArrayList<String>();
        }
        return this.versionResponsibilityAccepted;
    }

    public void setVersionResponsibilityAccepted(List<String> versionResponsibilityAccepted) {
        this.versionResponsibilityAccepted = versionResponsibilityAccepted;
    }

    public String getVersionResponsibilityIssued() {
        return this.versionResponsibilityIssued;
    }

    public void setVersionResponsibilityIssued(String versionResponsibilityIssued) {
        this.versionResponsibilityIssued = versionResponsibilityIssued;
    }

    public String getVersionResponsibilityOutofprint() {
        return this.versionResponsibilityOutofprint;
    }

    public void setVersionResponsibilityOutofprint(String versionResponsibilityOutofprint) {
        this.versionResponsibilityOutofprint = versionResponsibilityOutofprint;
    }

    public String getProcStatus() {
        return this.procStatus;
    }

    public void setProcStatus(String procStatus) {
        this.procStatus = procStatus;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIdentifierUniversal() {
        return this.identifierUniversal;
    }

    public void setIdentifierUniversal(String identifierUniversal) {
        this.identifierUniversal = identifierUniversal;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSubtitle() {
        if (this.subtitle == null) {
            this.subtitle = new ArrayList<String>();
        }
        return this.subtitle;
    }

    public void setSubtitle(List<String> subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getTitleAlternative() {
        if (this.titleAlternative == null) {
            this.titleAlternative = new ArrayList<String>();
        }
        return this.titleAlternative;
    }

    public void setTitleAlternative(List<String> titlesAlternative) {
        this.titleAlternative = titlesAlternative;
    }

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<String> getLanguages() {
        if (this.languages == null) {
            this.languages = new ArrayList<String>();
        }
        return this.languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public String getSurveyTitle() {
        return this.surveyTitle;
    }

    public void setSurveyTitle(String surveyTitle) {
        this.surveyTitle = surveyTitle;
    }

    public String getSurveyAlternative() {
        return this.surveyAlternative;
    }

    public void setSurveyAlternative(String surveyAlternative) {
        this.surveyAlternative = surveyAlternative;
    }

    public String getSurveyCode() {
        return this.surveyCode;
    }

    public void setSurveyCode(String surveyCode) {
        this.surveyCode = surveyCode;
    }

    public List<String> getSubjectAreas() {
        if (this.subjectAreas == null) {
            this.subjectAreas = new ArrayList<String>();
        }
        return this.subjectAreas;
    }

    public void setSubjectAreas(List<String> subjectAreas) {
        this.subjectAreas = subjectAreas;
    }

    public List<String> getSubjectCodes() {
        if (this.subjectCodes == null) {
            this.subjectCodes = new ArrayList<String>();
        }
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public List<String> getDescription() {
        if (this.description == null) {
            this.description = new ArrayList<String>();
        }
        return this.description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getAbstract() {
        if (this.abstractDescription == null) {
            this.abstractDescription = new ArrayList<String>();
        }
        return this.abstractDescription;
    }

    public void setAbstract(List<String> abstractDescription) {
        this.abstractDescription = abstractDescription;
    }

    public List<String> getKeywords() {
        if (this.keywords == null) {
            this.keywords = new ArrayList<String>();
        }
        return this.keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public List<String> getCoverageSpatial() {
        if (this.coverageSpatial == null) {
            this.coverageSpatial = new ArrayList<String>();
        }
        return this.coverageSpatial;
    }

    public void setCoverageSpatial(List<String> coverageSpatial) {
        this.coverageSpatial = coverageSpatial;
    }

    public List<String> getCoverageSpatialCodes() {
        if (this.coverageSpatialCodes == null) {
            this.coverageSpatialCodes = new ArrayList<String>();
        }
        return this.coverageSpatialCodes;
    }

    public void setCoverageSpatialCodes(List<String> coverageSpatialCodes) {
        this.coverageSpatialCodes = coverageSpatialCodes;
    }

    public List<String> getCoverageTemporal() {
        if (this.coverageTemporal == null) {
            this.coverageTemporal = new ArrayList<String>();
        }
        return this.coverageTemporal;
    }

    public void setCoverageTemporal(List<String> coverageTemporal) {
        this.coverageTemporal = coverageTemporal;
    }

    public List<String> getCoverageTemporalCodes() {
        if (this.coverageTemporalCodes == null) {
            this.coverageTemporalCodes = new ArrayList<String>();
        }
        return this.coverageTemporalCodes;
    }

    public void setCoverageTemporalCodes(List<String> coverageTemporalCodes) {
        this.coverageTemporalCodes = coverageTemporalCodes;
    }

    public List<String> getType() {
        if (this.type == null) {
            this.type = new ArrayList<String>();
        }
        return this.type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public List<String> getFormat() {
        if (this.format == null) {
            this.format = new ArrayList<String>();
        }
        return this.format;
    }

    public void setFormat(List<String> format) {
        this.format = format;
    }

    public List<String> getFormatExtent() {
        if (this.formatExtent == null) {
            this.formatExtent = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                this.formatExtent.add(StringUtils.EMPTY);
            }
        }
        return this.formatExtent;
    }

    public void setFormatExtent(List<String> formatExtent) {
        this.formatExtent = formatExtent;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public List<String> getContributor() {
        if (this.contributor == null) {
            this.contributor = new ArrayList<String>();
        }
        return this.contributor;
    }

    public void setContributor(List<String> contributor) {
        this.contributor = contributor;
    }

    public Date getDateCreated() {
        return new Date(this.dateCreated.getTime());
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = new Date(dateCreated.getTime());
    }

    public Date getLastUpdate() {
        return new Date(this.lastUpdate.getTime());
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = new Date(lastUpdate.getTime());
    }

    public Date getDateSubmitted() {
        return new Date(this.dateSubmitted.getTime());
    }

    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = new Date(dateSubmitted.getTime());
    }

    public Date getDateNextUpdate() {
        return new Date(this.dateNextUpdate.getTime());
    }

    public void setDateNextUpdate(Date dateNextUpdate) {
        this.dateNextUpdate = new Date(dateNextUpdate.getTime());
    }

    public String getUpdateFrequency() {
        return this.updateFrequency;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getRangeDatesValid() {
        return this.rangeDatesValid;
    }

    public void setRangeDatesValid(String rangeDatesValid) {
        this.rangeDatesValid = rangeDatesValid;
    }

    public List<String> getPublisher() {
        if (this.publisher == null) {
            this.publisher = new ArrayList<String>();
        }
        return this.publisher;
    }

    public void setPublisher(List<String> publisher) {
        this.publisher = publisher;
    }

    public List<String> getMediator() {
        if (this.mediator == null) {
            this.mediator = new ArrayList<String>();
        }
        return this.mediator;
    }

    public void setMediator(List<String> mediator) {
        this.mediator = mediator;
    }

    public Date getDateAccepted() {
        return new Date(this.dateAccepted.getTime());
    }

    public void setDateAccepted(Date dateAccepted) {
        this.dateAccepted = new Date(dateAccepted.getTime());
    }

    public Date getDateIssued() {
        return new Date(this.dateIssued.getTime());
    }

    public void setDateIssued(Date dateIssued) {
        this.dateIssued = new Date(dateIssued.getTime());
    }

    public String getRangeDatesAvailable() {
        return this.rangeDatesAvailable;
    }

    public void setRangeDatesAvailable(String rangeDatesAvailable) {
        this.rangeDatesAvailable = rangeDatesAvailable;
    }

    public List<String> getAudience() {
        if (this.audience == null) {
            this.audience = new ArrayList<String>();
        }
        return this.audience;
    }

    public void setAudience(List<String> audience) {
        this.audience = audience;
    }

    public List<String> getEducationLevel() {
        if (this.educationLevel == null) {
            this.educationLevel = new ArrayList<String>();
        }
        return this.educationLevel;
    }

    public void setEducationLevel(List<String> educationLevel) {
        this.educationLevel = educationLevel;
    }

    public List<String> getBibliographicCitation() {
        if (this.bibliographicCitation == null) {
            this.bibliographicCitation = new ArrayList<String>();
        }
        return this.bibliographicCitation;
    }

    public void setBibliographicCitation(List<String> bibliographicCitation) {
        this.bibliographicCitation = bibliographicCitation;
    }

    public List<String> getConformsTo() {
        if (this.conformsTo == null) {
            this.conformsTo = new ArrayList<String>();
        }
        return this.conformsTo;
    }

    public void setConformsTo(List<String> conformsTo) {
        this.conformsTo = conformsTo;
    }

    public List<String> getSource() {
        if (this.source == null) {
            this.source = new ArrayList<String>();
        }
        return this.source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public String getIsVersionOf() {
        return this.isVersionOf;
    }

    public void setIsVersionOf(String isVersionOf) {
        this.isVersionOf = isVersionOf;
    }

    public List<String> getHasVersion() {
        if (this.hasVersion == null) {
            this.hasVersion = new ArrayList<String>();
        }
        return this.hasVersion;
    }

    public void setHasVersion(List<String> hasVersion) {
        this.hasVersion = hasVersion;
    }

    public List<String> getReplaces() {
        if (this.replaces == null) {
            this.replaces = new ArrayList<String>();
        }
        return this.replaces;
    }

    public void setReplaces(List<String> replaces) {
        this.replaces = replaces;
    }

    public String getIsReplacedBy() {
        return this.isReplacedBy;
    }

    public void setIsReplacedBy(String isReplacedBy) {
        this.isReplacedBy = isReplacedBy;
    }

    public List<String> getRequires() {
        if (this.requires == null) {
            this.requires = new ArrayList<String>();
        }
        return this.requires;
    }

    public void setRequires(List<String> requires) {
        this.requires = requires;
    }

    public List<String> getIsRequiredBy() {
        if (this.isRequiredBy == null) {
            this.isRequiredBy = new ArrayList<String>();
        }
        return this.isRequiredBy;
    }

    public void setIsRequiredBy(List<String> isRequiredBy) {
        this.isRequiredBy = isRequiredBy;
    }

    public List<String> getHasPart() {
        if (this.hasPart == null) {
            this.hasPart = new ArrayList<String>();
        }
        return this.hasPart;
    }

    public void setHasPart(List<String> hasPart) {
        this.hasPart = hasPart;
    }

    public List<String> getIsPartOf() {
        if (this.isPartOf == null) {
            this.isPartOf = new ArrayList<String>();
        }
        return this.isPartOf;
    }

    public void setIsPartOf(List<String> isPartOf) {
        this.isPartOf = isPartOf;
    }

    public List<String> getIsReferencedBy() {
        if (this.isReferencedBy == null) {
            this.isReferencedBy = new ArrayList<String>();
        }
        return this.isReferencedBy;
    }

    public void setIsReferencedBy(List<String> isReferencedBy) {
        this.isReferencedBy = isReferencedBy;
    }

    public List<String> getReferences() {
        if (this.references == null) {
            this.references = new ArrayList<String>();
        }
        return this.references;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public List<String> getIsFormatOf() {
        if (this.isFormatOf == null) {
            this.isFormatOf = new ArrayList<String>();
        }
        return this.isFormatOf;
    }

    public void setIsFormatOf(List<String> isFormatOf) {
        this.isFormatOf = isFormatOf;
    }

    public List<String> getHasFormat() {
        if (this.hasFormat == null) {
            this.hasFormat = new ArrayList<String>();
        }
        return this.hasFormat;
    }

    public void setHasFormat(List<String> hasFormat) {
        this.hasFormat = hasFormat;
    }

    public List<String> getRightsHolder() {
        if (this.rightsHolder == null) {
            this.rightsHolder = new ArrayList<String>();
        }
        return this.rightsHolder;
    }

    public void setRightsHolder(List<String> rightsHolder) {
        this.rightsHolder = rightsHolder;
    }

    public Date getDateCopyrighted() {
        return new Date(this.dateCopyrighted.getTime());
    }

    public void setDateCopyrighted(Date dateCopyrighted) {
        this.dateCopyrighted = new Date(dateCopyrighted.getTime());
    }

    public List<String> getLicense() {
        if (this.license == null) {
            this.license = new ArrayList<String>();
            for (int i = 0; i < 2; i++) {
                this.license.add(StringUtils.EMPTY);
            }
        }
        return this.license;
    }

    public void setLicense(List<String> license) {
        this.license = license;
    }

    public String getAccessRights() {
        return this.accessRights;
    }

    public void setAccessRights(String accessRights) {
        this.accessRights = accessRights;
    }

    public String getVisitCount() {
        return this.visitCount;
    }

    public void setVisitCount(String visitCount) {
        this.visitCount = visitCount;
    }

    public String getVisitTime() {
        return this.visitTime;
    }

    public Date getVisitLastDate() {
        return new Date(this.visitLastDate.getTime());
    }

    public void setVisitLastDate(Date visitLastDate) {
        this.visitLastDate = new Date(visitLastDate.getTime());
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getShareCount() {
        return this.shareCount;
    }

    public void setShareCount(String shareCount) {
        this.shareCount = shareCount;
    }

    public String getDownloadCount() {
        return this.downloadCount;
    }

    public void setDownloadCount(String downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getBookmarkCount() {
        return this.bookmarkCount;
    }

    public void setBookmarkCount(String bookmarkCount) {
        this.bookmarkCount = bookmarkCount;
    }

    public List<String> getTags() {
        if (this.tags == null) {
            this.tags = new ArrayList<String>();
        }
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        NucleoMetadatos other = (NucleoMetadatos) obj;
        if (this.identifierUniversal == null) {
            if (other.identifierUniversal != null) {
                return false;
            }
        } else if (!this.identifierUniversal.equals(other.identifierUniversal)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

}
