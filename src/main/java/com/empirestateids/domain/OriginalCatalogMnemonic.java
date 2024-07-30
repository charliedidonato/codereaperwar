package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum OriginalCatalogMnemonic {
	SYM_FDA_PACK, SYM_AMA_CPT,EMPTY;

    @JsonValue
    public String toValue() {
        switch (this) {
            case SYM_AMA_CPT: return "SYM_AMA_CPT";
            case SYM_FDA_PACK: return "SYM_FDA_PACK";
            case EMPTY: return "";
        }
        return null;
    }

    @JsonCreator
    public static OriginalCatalogMnemonic forValue(String value) throws IOException {
        if (value.equals("SYM_AMA_CPT")) return SYM_AMA_CPT;
        if (value.equals("SYM_FDA_PACK")) return SYM_FDA_PACK;
        if (value.equals("")) return EMPTY;
        throw new IOException("Cannot deserialize OriginalCatalogMnemonic:'"+value+"'");
    }
}