
package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum AliasLanguageISOCode {
    EN_US;

    @JsonValue
    public String toValue() {
        switch (this) {
            case EN_US: return "en-US";
        }
        return null;
    }

    @JsonCreator
    public static AliasLanguageISOCode forValue(String value) throws IOException {
        if (value.equals("en-US")) return EN_US;
        throw new IOException("Cannot deserialize AliasLanguageISOCode");
    }
}
