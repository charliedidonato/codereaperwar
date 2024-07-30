
package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SearchResult {

	private String searchHitDescription;
    private String searchHitSourceCode;
    private boolean searchHitIsAliasBased;
    private UUID termUID;
    private UUID termCatalogUID;
    private String termSourceCode;
    private String termDescription;
    private UUID aliasUID;
    private AliasTypeName aliasTypeName;
    private AliasLanguageName aliasLanguageName;
    private boolean isPreferred;
    private boolean isRetired;
    private boolean isLocalTerm;
    private UUID catalogUID;
    private CatalogMnemonic catalogMnemonic;
    private OriginalCatalogMnemonic originalCatalogMnemonic;
    private CatalogDisplayName catalogDisplayName;
    private AttributesXML attributesXML;
    private OffsetDateTime creationDate;
    private OffsetDateTime creationDateLocal;
    private OffsetDateTime modificationDate;
    private OffsetDateTime modificationDateLocal;
    private OffsetDateTime lastPublishDate;
    private OffsetDateTime lastPublishDateLocal;
    private long incidenceCount;
    private boolean hasDigitalAttachment;
    private String preferredDescription;
    private String preferredAliasSourceCode;
    private String preferredAliasDescription;
    private String preferredAliasTypeName;
    private String preferredAliasTypeMnemonic;
    private String preferredAliasGroupMnemonic;
    private String preferredAliasLanguageISOCode;
    private String preferredAliasLanguageName;
    private ProfileDetail profileDetail;
    private List<Object> externalCodes;
    private String aliasSourceCode;
    private String aliasDescription;
    private AliasTypeMnemonic aliasTypeMnemonic;
    private String aliasGroupMnemonic;
    private AliasLanguageISOCode aliasLanguageISOCode;

    @JsonProperty("SearchHitDescription")
    public String getSearchHitDescription() { return searchHitDescription; }
    @JsonProperty("SearchHitDescription")
    public void setSearchHitDescription(String value) { this.searchHitDescription = value; }

    @JsonProperty("SearchHitSourceCode")
    public String getSearchHitSourceCode() { return searchHitSourceCode; }
    @JsonProperty("SearchHitSourceCode")
    public void setSearchHitSourceCode(String value) { this.searchHitSourceCode = value; }

    @JsonProperty("SearchHitIsAliasBased")
    public boolean getSearchHitIsAliasBased() { return searchHitIsAliasBased; }
    @JsonProperty("SearchHitIsAliasBased")
    public void setSearchHitIsAliasBased(boolean value) { this.searchHitIsAliasBased = value; }

    @JsonProperty("TermUID")
    public UUID getTermUID() { return termUID; }
    @JsonProperty("TermUID")
    public void setTermUID(UUID value) { this.termUID = value; }

    @JsonProperty("TermCatalogUID")
    public UUID getTermCatalogUID() { return termCatalogUID; }
    @JsonProperty("TermCatalogUID")
    public void setTermCatalogUID(UUID value) { this.termCatalogUID = value; }

    @JsonProperty("TermSourceCode")
    public String getTermSourceCode() { return termSourceCode; }
    @JsonProperty("TermSourceCode")
    public void setTermSourceCode(String value) { this.termSourceCode = value; }

    @JsonProperty("TermDescription")
    public String getTermDescription() { return termDescription; }
    @JsonProperty("TermDescription")
    public void setTermDescription(String value) { this.termDescription = value; }

    @JsonProperty("AliasUID")
    public UUID getAliasUID() { return aliasUID; }
    @JsonProperty("AliasUID")
    public void setAliasUID(UUID value) { this.aliasUID = value; }

    @JsonProperty("AliasTypeName")
    public AliasTypeName getAliasTypeName() { return aliasTypeName; }
    @JsonProperty("AliasTypeName")
    public void setAliasTypeName(AliasTypeName value) { this.aliasTypeName = value; }

    @JsonProperty("AliasLanguageName")
    public AliasLanguageName getAliasLanguageName() { return aliasLanguageName; }
    @JsonProperty("AliasLanguageName")
    public void setAliasLanguageName(AliasLanguageName value) { this.aliasLanguageName = value; }

    @JsonProperty("IsPreferred")
    public boolean getIsPreferred() { return isPreferred; }
    @JsonProperty("IsPreferred")
    public void setIsPreferred(boolean value) { this.isPreferred = value; }

    @JsonProperty("IsRetired")
    public boolean getIsRetired() { return isRetired; }
    @JsonProperty("IsRetired")
    public void setIsRetired(boolean value) { this.isRetired = value; }

    @JsonProperty("IsLocalTerm")
    public boolean getIsLocalTerm() { return isLocalTerm; }
    @JsonProperty("IsLocalTerm")
    public void setIsLocalTerm(boolean value) { this.isLocalTerm = value; }

    @JsonProperty("CatalogUID")
    public UUID getCatalogUID() { return catalogUID; }
    @JsonProperty("CatalogUID")
    public void setCatalogUID(UUID value) { this.catalogUID = value; }

    @JsonProperty("CatalogMnemonic")
    public CatalogMnemonic getCatalogMnemonic() { return catalogMnemonic; }
    @JsonProperty("CatalogMnemonic")
    public void setCatalogMnemonic(CatalogMnemonic value) { this.catalogMnemonic = value; }

    @JsonProperty("OriginalCatalogMnemonic")
    public OriginalCatalogMnemonic getOriginalCatalogMnemonic() { return originalCatalogMnemonic; }
    @JsonProperty("OriginalCatalogMnemonic")
    public void setOriginalCatalogMnemonic(OriginalCatalogMnemonic value) { this.originalCatalogMnemonic = value; }

    @JsonProperty("CatalogDisplayName")
    public CatalogDisplayName getCatalogDisplayName() { return catalogDisplayName; }
    @JsonProperty("CatalogDisplayName")
    public void setCatalogDisplayName(CatalogDisplayName value) { this.catalogDisplayName = value; }

    @JsonProperty("AttributesXML")
    public AttributesXML getAttributesXML() { return attributesXML; }
    @JsonProperty("AttributesXML")
    public void setAttributesXML(AttributesXML value) { this.attributesXML = value; }

    @JsonProperty("CreationDate")
    public OffsetDateTime getCreationDate() { return creationDate; }
    @JsonProperty("CreationDate")
    public void setCreationDate(OffsetDateTime value) { this.creationDate = value; }

    @JsonProperty("CreationDateLocal")
    public OffsetDateTime getCreationDateLocal() { return creationDateLocal; }
    @JsonProperty("CreationDateLocal")
    public void setCreationDateLocal(OffsetDateTime value) { this.creationDateLocal = value; }

    @JsonProperty("ModificationDate")
    public OffsetDateTime getModificationDate() { return modificationDate; }
    @JsonProperty("ModificationDate")
    public void setModificationDate(OffsetDateTime value) { this.modificationDate = value; }

    @JsonProperty("ModificationDateLocal")
    public OffsetDateTime getModificationDateLocal() { return modificationDateLocal; }
    @JsonProperty("ModificationDateLocal")
    public void setModificationDateLocal(OffsetDateTime value) { this.modificationDateLocal = value; }

    @JsonProperty("LastPublishDate")
    public OffsetDateTime getLastPublishDate() { return lastPublishDate; }
    @JsonProperty("LastPublishDate")
    public void setLastPublishDate(OffsetDateTime value) { this.lastPublishDate = value; }

    @JsonProperty("LastPublishDateLocal")
    public OffsetDateTime getLastPublishDateLocal() { return lastPublishDateLocal; }
    @JsonProperty("LastPublishDateLocal")
    public void setLastPublishDateLocal(OffsetDateTime value) { this.lastPublishDateLocal = value; }

    @JsonProperty("IncidenceCount")
    public long getIncidenceCount() { return incidenceCount; }
    @JsonProperty("IncidenceCount")
    public void setIncidenceCount(long value) { this.incidenceCount = value; }

    @JsonProperty("HasDigitalAttachment")
    public boolean getHasDigitalAttachment() { return hasDigitalAttachment; }
    @JsonProperty("HasDigitalAttachment")
    public void setHasDigitalAttachment(boolean value) { this.hasDigitalAttachment = value; }

    @JsonProperty("PreferredDescription")
    public String getPreferredDescription() { return preferredDescription; }
    @JsonProperty("PreferredDescription")
    public void setPreferredDescription(String value) { this.preferredDescription = value; }

    @JsonProperty("PreferredAliasSourceCode")
    public String getPreferredAliasSourceCode() { return preferredAliasSourceCode; }
    @JsonProperty("PreferredAliasSourceCode")
    public void setPreferredAliasSourceCode(String value) { this.preferredAliasSourceCode = value; }

    @JsonProperty("PreferredAliasDescription")
    public String getPreferredAliasDescription() { return preferredAliasDescription; }
    @JsonProperty("PreferredAliasDescription")
    public void setPreferredAliasDescription(String value) { this.preferredAliasDescription = value; }

    @JsonProperty("PreferredAliasTypeName")
    public String getPreferredAliasTypeName() { return preferredAliasTypeName; }
    @JsonProperty("PreferredAliasTypeName")
    public void setPreferredAliasTypeName(String value) { this.preferredAliasTypeName = value; }

    @JsonProperty("PreferredAliasTypeMnemonic")
    public String getPreferredAliasTypeMnemonic() { return preferredAliasTypeMnemonic; }
    @JsonProperty("PreferredAliasTypeMnemonic")
    public void setPreferredAliasTypeMnemonic(String value) { this.preferredAliasTypeMnemonic = value; }

    @JsonProperty("PreferredAliasGroupMnemonic")
    public String getPreferredAliasGroupMnemonic() { return preferredAliasGroupMnemonic; }
    @JsonProperty("PreferredAliasGroupMnemonic")
    public void setPreferredAliasGroupMnemonic(String value) { this.preferredAliasGroupMnemonic = value; }

    @JsonProperty("PreferredAliasLanguageISOCode")
    public String getPreferredAliasLanguageISOCode() { return preferredAliasLanguageISOCode; }
    @JsonProperty("PreferredAliasLanguageISOCode")
    public void setPreferredAliasLanguageISOCode(String value) { this.preferredAliasLanguageISOCode = value; }

    @JsonProperty("PreferredAliasLanguageName")
    public String getPreferredAliasLanguageName() { return preferredAliasLanguageName; }
    @JsonProperty("PreferredAliasLanguageName")
    public void setPreferredAliasLanguageName(String value) { this.preferredAliasLanguageName = value; }

    @JsonProperty("ProfileDetail")
    public ProfileDetail getProfileDetail() { return profileDetail; }
    @JsonProperty("ProfileDetail")
    public void setProfileDetail(ProfileDetail value) { this.profileDetail = value; }

    @JsonProperty("ExternalCodes")
    public List<Object> getExternalCodes() { return externalCodes; }
    @JsonProperty("ExternalCodes")
    public void setExternalCodes(List<Object> value) { this.externalCodes = value; }

    @JsonProperty("AliasSourceCode")
    public String getAliasSourceCode() { return aliasSourceCode; }
    @JsonProperty("AliasSourceCode")
    public void setAliasSourceCode(String value) { this.aliasSourceCode = value; }

    @JsonProperty("AliasDescription")
    public String getAliasDescription() { return aliasDescription; }
    @JsonProperty("AliasDescription")
    public void setAliasDescription(String value) { this.aliasDescription = value; }

    @JsonProperty("AliasTypeMnemonic")
    public AliasTypeMnemonic getAliasTypeMnemonic() { return aliasTypeMnemonic; }
    @JsonProperty("AliasTypeMnemonic")
    public void setAliasTypeMnemonic(AliasTypeMnemonic value) { this.aliasTypeMnemonic = value; }

    @JsonProperty("AliasGroupMnemonic")
    public String getAliasGroupMnemonic() { return aliasGroupMnemonic; }
    @JsonProperty("AliasGroupMnemonic")
    public void setAliasGroupMnemonic(String value) { this.aliasGroupMnemonic = value; }

    @JsonProperty("AliasLanguageISOCode")
    public AliasLanguageISOCode getAliasLanguageISOCode() { return aliasLanguageISOCode; }
    @JsonProperty("AliasLanguageISOCode")
    public void setAliasLanguageISOCode(AliasLanguageISOCode value) { this.aliasLanguageISOCode = value; }


    @Override
	public String toString() {
		return "SearchResult [searchHitDescription=" + searchHitDescription + ", searchHitSourceCode="
				+ searchHitSourceCode + ", searchHitIsAliasBased=" + searchHitIsAliasBased + ", termUID=" + termUID
				+ ", termCatalogUID=" + termCatalogUID + ", termSourceCode=" + termSourceCode + ", termDescription="
				+ termDescription + ", aliasUID=" + aliasUID + ", aliasTypeName=" + aliasTypeName
				+ ", aliasLanguageName=" + aliasLanguageName + ", isPreferred=" + isPreferred + ", isRetired="
				+ isRetired + ", isLocalTerm=" + isLocalTerm + ", catalogUID=" + catalogUID + ", catalogMnemonic="
				+ catalogMnemonic + ", originalCatalogMnemonic=" + originalCatalogMnemonic + ", catalogDisplayName="
				+ catalogDisplayName + ", attributesXML=" + attributesXML + ", creationDate=" + creationDate
				+ ", creationDateLocal=" + creationDateLocal + ", modificationDate=" + modificationDate
				+ ", modificationDateLocal=" + modificationDateLocal + ", lastPublishDate=" + lastPublishDate
				+ ", lastPublishDateLocal=" + lastPublishDateLocal + ", incidenceCount=" + incidenceCount
				+ ", hasDigitalAttachment=" + hasDigitalAttachment + ", preferredDescription=" + preferredDescription
				+ ", preferredAliasSourceCode=" + preferredAliasSourceCode + ", preferredAliasDescription="
				+ preferredAliasDescription + ", preferredAliasTypeName=" + preferredAliasTypeName
				+ ", preferredAliasTypeMnemonic=" + preferredAliasTypeMnemonic + ", preferredAliasGroupMnemonic="
				+ preferredAliasGroupMnemonic + ", preferredAliasLanguageISOCode=" + preferredAliasLanguageISOCode
				+ ", preferredAliasLanguageName=" + preferredAliasLanguageName + ", profileDetail=" + profileDetail
				+ ", externalCodes=" + externalCodes + ", aliasSourceCode=" + aliasSourceCode + ", aliasDescription="
				+ aliasDescription + ", aliasTypeMnemonic=" + aliasTypeMnemonic + ", aliasGroupMnemonic="
				+ aliasGroupMnemonic + ", aliasLanguageISOCode=" + aliasLanguageISOCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(aliasDescription, aliasGroupMnemonic, aliasLanguageISOCode, aliasLanguageName,
				aliasSourceCode, aliasTypeMnemonic, aliasTypeName, aliasUID, attributesXML, catalogDisplayName,
				catalogMnemonic, catalogUID, creationDate, creationDateLocal, externalCodes, hasDigitalAttachment,
				incidenceCount, isLocalTerm, isPreferred, isRetired, lastPublishDate, lastPublishDateLocal,
				modificationDate, modificationDateLocal, originalCatalogMnemonic, preferredAliasDescription,
				preferredAliasGroupMnemonic, preferredAliasLanguageISOCode, preferredAliasLanguageName,
				preferredAliasSourceCode, preferredAliasTypeMnemonic, preferredAliasTypeName, preferredDescription,
				profileDetail, searchHitDescription, searchHitIsAliasBased, searchHitSourceCode, termCatalogUID,
				termDescription, termSourceCode, termUID);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SearchResult other = (SearchResult) obj;
		return Objects.equals(aliasDescription, other.aliasDescription)
				&& Objects.equals(aliasGroupMnemonic, other.aliasGroupMnemonic)
				&& aliasLanguageISOCode == other.aliasLanguageISOCode && aliasLanguageName == other.aliasLanguageName
				&& Objects.equals(aliasSourceCode, other.aliasSourceCode)
				&& aliasTypeMnemonic == other.aliasTypeMnemonic && aliasTypeName == other.aliasTypeName
				&& Objects.equals(aliasUID, other.aliasUID) && Objects.equals(attributesXML, other.attributesXML)
				&& catalogDisplayName == other.catalogDisplayName && catalogMnemonic == other.catalogMnemonic
				&& Objects.equals(catalogUID, other.catalogUID) && Objects.equals(creationDate, other.creationDate)
				&& Objects.equals(creationDateLocal, other.creationDateLocal)
				&& Objects.equals(externalCodes, other.externalCodes)
				&& hasDigitalAttachment == other.hasDigitalAttachment && incidenceCount == other.incidenceCount
				&& isLocalTerm == other.isLocalTerm && isPreferred == other.isPreferred && isRetired == other.isRetired
				&& Objects.equals(lastPublishDate, other.lastPublishDate)
				&& Objects.equals(lastPublishDateLocal, other.lastPublishDateLocal)
				&& Objects.equals(modificationDate, other.modificationDate)
				&& Objects.equals(modificationDateLocal, other.modificationDateLocal)
				&& originalCatalogMnemonic == other.originalCatalogMnemonic
				&& Objects.equals(preferredAliasDescription, other.preferredAliasDescription)
				&& Objects.equals(preferredAliasGroupMnemonic, other.preferredAliasGroupMnemonic)
				&& Objects.equals(preferredAliasLanguageISOCode, other.preferredAliasLanguageISOCode)
				&& Objects.equals(preferredAliasLanguageName, other.preferredAliasLanguageName)
				&& Objects.equals(preferredAliasSourceCode, other.preferredAliasSourceCode)
				&& Objects.equals(preferredAliasTypeMnemonic, other.preferredAliasTypeMnemonic)
				&& Objects.equals(preferredAliasTypeName, other.preferredAliasTypeName)
				&& Objects.equals(preferredDescription, other.preferredDescription)
				&& Objects.equals(profileDetail, other.profileDetail)
				&& Objects.equals(searchHitDescription, other.searchHitDescription)
				&& searchHitIsAliasBased == other.searchHitIsAliasBased
				&& Objects.equals(searchHitSourceCode, other.searchHitSourceCode)
				&& Objects.equals(termCatalogUID, other.termCatalogUID)
				&& Objects.equals(termDescription, other.termDescription)
				&& Objects.equals(termSourceCode, other.termSourceCode) && Objects.equals(termUID, other.termUID);
	}

}