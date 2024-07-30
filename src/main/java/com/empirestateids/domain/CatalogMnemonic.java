package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum CatalogMnemonic {
	CDC_IMMUN_FDA_NDC_UOS, CDC_IMMUN_VIS,
	CDC_IMMUN_FDA_NDC_UOU, CDC_IMMUN_MVX,
	CDC_IMMUN_VAC_PROD, CDC_IMMUN_AMA_CPT,
	CDC_IMMUN_VAC_GRP, CDC_IMMUN_CVX,
	REF_CAT_CTL, REF_MAP_CTL,
	REF_MODEL_CTL;

    @JsonValue
    public String toValue() {
        switch (this) {
            case CDC_IMMUN_FDA_NDC_UOS: return "CDC_IMMUN_FDA_NDC_UOS";
            case CDC_IMMUN_VIS: return "CDC_IMMUN_VIS";
            case CDC_IMMUN_FDA_NDC_UOU: return "CDC_IMMUN_FDA_NDC_UOU";
            case CDC_IMMUN_VAC_PROD: return "CDC_IMMUN_VAC_PROD";
            case CDC_IMMUN_AMA_CPT: return "CDC_IMMUN_AMA_CPT";
            case CDC_IMMUN_VAC_GRP: return "CDC_IMMUN_VAC_GRP";
            case CDC_IMMUN_CVX: return "CDC_IMMUN_CVX";
            case CDC_IMMUN_MVX: return "CDC_IMMUN_MVX";
            case REF_CAT_CTL: return "REF_CAT_CTL";
            case REF_MAP_CTL: return "REF_MAP_CTL";
            case REF_MODEL_CTL: return "REF_MODEL_CTL";

        }
        return null;
    }

    @JsonCreator
    public static CatalogMnemonic forValue(String value) throws IOException {
        if (value.equals("CDC_IMMUN_FDA_NDC_UOS")) return CDC_IMMUN_FDA_NDC_UOS;
        if (value.equals("CDC_IMMUN_VIS")) return CDC_IMMUN_VIS;
        if (value.equals("CDC_IMMUN_FDA_NDC_UOU")) return CDC_IMMUN_FDA_NDC_UOU;
        if (value.equals("CDC_IMMUN_VAC_PROD")) return CDC_IMMUN_VAC_PROD;
        if (value.equals("CDC_IMMUN_AMA_CPT")) return CDC_IMMUN_AMA_CPT;
        if (value.equals("CDC_IMMUN_VAC_GRP")) return CDC_IMMUN_VAC_GRP;
        if (value.equals("CDC_IMMUN_CVX")) return CDC_IMMUN_CVX;
        if (value.equals("REF_CAT_CTL")) return REF_CAT_CTL;
        if (value.equals("REF_MAP_CTL")) return REF_MODEL_CTL;
        if (value.equals("REF_MODEL_CTL")) return REF_MODEL_CTL;
        if (value.equals("CDC_IMMUN_MVX")) return CDC_IMMUN_MVX;
        throw new IOException("Cannot deserialize CatalogMnemonic:'"+value+"'");
    }
}
