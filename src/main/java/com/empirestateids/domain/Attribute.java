package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;

public class Attribute {
    private NameAttr nameAttr;
    private String textAttr;

    @JsonProperty("nameAttr")
    public NameAttr getNameAttr() { return nameAttr; }
    @JsonProperty("nameAttr")
    public void setNameAttr(NameAttr value) { this.nameAttr = value; }

    @JsonProperty("textAttr")
    public String getTextAttr() { return textAttr; }
    @JsonProperty("textAttr")
    public void setTextAttr(String value) { this.textAttr = value; }
}
