package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum Name {
    CDC_INTERNAL_ID;

    @JsonValue
    public String toValue() {
        switch (this) {
            case CDC_INTERNAL_ID: return "CDC_Internal_ID";
        }
        return null;
    }

    @JsonCreator
    public static Name forValue(String value) throws IOException {
        if (value.equals("CDC_Internal_ID")) return CDC_INTERNAL_ID;
        throw new IOException("Cannot deserialize Name:"+value);
    }
}
