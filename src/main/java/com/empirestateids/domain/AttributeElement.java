package com.empirestateids.domain;

import java.util.Objects;

public class AttributeElement {

	private String nameAttr;
    private String textAttr;

    public String getNameAttr() { return nameAttr; }
    public void setNameAttr(String value) { this.nameAttr = value; }

    public String getTextAttr() { return textAttr; }
    public void setTextAttr(String value) { this.textAttr = value; }

    @Override
	public String toString() {
		return "AttributeElement [nameAttr=" + nameAttr + ", textAttr=" + textAttr + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(nameAttr, textAttr);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeElement other = (AttributeElement) obj;
		return Objects.equals(nameAttr, other.nameAttr) && Objects.equals(textAttr, other.textAttr);
	}
}
