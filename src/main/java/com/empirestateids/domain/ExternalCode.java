package com.empirestateids.domain;

import com.fasterxml.jackson.annotation.*;

public class ExternalCode {
    private Name name;
    private String code;

    @JsonProperty("Name")
    public Name getName() { return name; }
    @JsonProperty("Name")
    public void setName(Name value) { this.name = value; }

    @JsonProperty("Code")
    public String getCode() { return code; }
    @JsonProperty("Code")
    public void setCode(String value) { this.code = value; }
}
