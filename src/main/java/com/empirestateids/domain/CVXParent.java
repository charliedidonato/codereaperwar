package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Objects;

public class CVXParent {

	private List<SearchResult> searchResults;
	private long termSearchTotalCount;

    @JsonProperty("SearchResults")
    public List<SearchResult> getSearchResults() { return searchResults; }
    @JsonProperty("SearchResults")
    public void setSearchResults(List<SearchResult> value) { this.searchResults = value; }

    @JsonProperty("TermSearchTotalCount")
    public long getTermSearchTotalCount() { return termSearchTotalCount; }
    @JsonProperty("TermSearchTotalCount")
    public void setTermSearchTotalCount(long value) { this.termSearchTotalCount = value; }



	@Override
	public String toString() {
		return "CVXParent [searchResults=" + searchResults + ", termSearchTotalCount=" + termSearchTotalCount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(searchResults, termSearchTotalCount);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CVXParent other = (CVXParent) obj;
		return Objects.equals(searchResults, other.searchResults) && termSearchTotalCount == other.termSearchTotalCount;
	}

}