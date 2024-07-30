package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;
import java.util.List;
import java.util.Objects;

public class Attributes {

	private AttributeUnion attribute;

    @JsonProperty("Attribute")
    public AttributeUnion getAttribute() { return attribute; }
    @JsonProperty("Attribute")
    public void setAttribute(AttributeUnion value) { this.attribute = value; }

    @Override
	public String toString() {
		return "Attributes [attribute=" + attribute + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(attribute);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attributes other = (Attributes) obj;
		return Objects.equals(attribute, other.attribute);
	}
}