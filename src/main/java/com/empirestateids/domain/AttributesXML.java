package com.empirestateids.domain;

import java.util.Objects;

import com.fasterxml.jackson.annotation.*;

public class AttributesXML {

	private Attributes attributes;

    @JsonProperty("Attributes")
    public Attributes getAttributes() { return attributes; }
    @JsonProperty("Attributes")
    public void setAttributes(Attributes value) { this.attributes = value; }

    @Override
	public String toString() {
		return "AttributesXML [attributes=" + attributes + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(attributes);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributesXML other = (AttributesXML) obj;
		return Objects.equals(attributes, other.attributes);
	}
}