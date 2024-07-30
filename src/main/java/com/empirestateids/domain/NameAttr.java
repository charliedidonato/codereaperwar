package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum NameAttr {
    CATEGORY, COMMENT, CPT_CODE_ID;

    @JsonValue
    public String toValue() {
        switch (this) {
            case CATEGORY: return "Category";
            case COMMENT: return "Comment";
            case CPT_CODE_ID: return "CPT_code_ID";
        }
        return null;
    }

    @JsonCreator
    public static NameAttr forValue(String value) throws IOException {
        if (value.equals("Category")) return CATEGORY;
        if (value.equals("Comment")) return COMMENT;
        if (value.equals("CPT_code_ID")) return CPT_CODE_ID;
        throw new IOException("Cannot deserialize NameAttr:"+value);
    }
}