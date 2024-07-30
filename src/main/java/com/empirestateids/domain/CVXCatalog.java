package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

public class CVXCatalog {

	private OffsetDateTime lastUpdatedDate;
    private UUID catalogUID;
    private long termCount;
    private String mnemonic;
    private String catalogVersion;
    private String catalogName;
    private OffsetDateTime lastPublishedDate;

    @JsonProperty("LastUpdatedDate")
    public OffsetDateTime getLastUpdatedDate() { return lastUpdatedDate; }
    @JsonProperty("LastUpdatedDate")
    public void setLastUpdatedDate(OffsetDateTime value) { this.lastUpdatedDate = value; }

    @JsonProperty("CatalogUID")
    public UUID getCatalogUID() { return catalogUID; }
    @JsonProperty("CatalogUID")
    public void setCatalogUID(UUID value) { this.catalogUID = value; }

    @JsonProperty("TermCount")
    public long getTermCount() { return termCount; }
    @JsonProperty("TermCount")
    public void setTermCount(long value) { this.termCount = value; }

    @JsonProperty("Mnemonic")
    public String getMnemonic() { return mnemonic; }
    @JsonProperty("Mnemonic")
    public void setMnemonic(String value) { this.mnemonic = value; }

    @JsonProperty("CatalogVersion")
    public String getCatalogVersion() { return catalogVersion; }
    @JsonProperty("CatalogVersion")
    public void setCatalogVersion(String value) { this.catalogVersion = value; }

    @JsonProperty("CatalogName")
    public String getCatalogName() { return catalogName; }
    @JsonProperty("CatalogName")
    public void setCatalogName(String value) { this.catalogName = value; }

    @JsonProperty("LastPublishedDate")
    public OffsetDateTime getLastPublishedDate() { return lastPublishedDate; }
    @JsonProperty("LastPublishedDate")
    public void setLastPublishedDate(OffsetDateTime value) { this.lastPublishedDate = value; }


	@Override
	public String toString() {
		return "CVXCatalog [lastUpdatedDate=" + lastUpdatedDate + ", catalogUID=" + catalogUID + ", termCount="
				+ termCount + ", mnemonic=" + mnemonic + ", catalogVersion=" + catalogVersion + ", catalogName="
				+ catalogName + ", lastPublishedDate=" + lastPublishedDate + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(catalogName, catalogUID, catalogVersion, lastPublishedDate, lastUpdatedDate, mnemonic,
				termCount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CVXCatalog other = (CVXCatalog) obj;
		return Objects.equals(catalogName, other.catalogName) && Objects.equals(catalogUID, other.catalogUID)
				&& Objects.equals(catalogVersion, other.catalogVersion)
				&& Objects.equals(lastPublishedDate, other.lastPublishedDate)
				&& Objects.equals(lastUpdatedDate, other.lastUpdatedDate) && Objects.equals(mnemonic, other.mnemonic)
				&& termCount == other.termCount;
	}
}
