package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum AliasLanguageName {
    EMPTY, ENGLISH_US;

    @JsonValue
    public String toValue() {
        switch (this) {
            case EMPTY: return "";
            case ENGLISH_US: return "English-US";
        }
        return null;
    }

    @JsonCreator
    public static AliasLanguageName forValue(String value) throws IOException {
        if (value.equals("")) return EMPTY;
        if (value.equals("English-US")) return ENGLISH_US;
        throw new IOException("Cannot deserialize AliasLanguageName");
    }
}