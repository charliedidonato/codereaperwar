
package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum AliasTypeName {
    ALTERNATE, CONSUMER, EMPTY, PROVIDER, SHORT,
    FULLY_SPECIFIED_NAME,CODE,TRADENAME,
    OUTERGENERICNAME,USEUNITGENERICNAME;

    @JsonValue
    public String toValue() {
        switch (this) {
            case ALTERNATE: return "Alternate";
            case CONSUMER: return "Consumer";
            case EMPTY: return "";
            case PROVIDER: return "Provider";
            case SHORT: return "Short";
            case CODE: return "Code";
            case TRADENAME: return "Trade Name";
            case OUTERGENERICNAME: return "OuterGenericName";
            case USEUNITGENERICNAME: return "UseUnitGenericName";
            case FULLY_SPECIFIED_NAME: return "Fully Specified Name";
        }
        return null;
    }

    @JsonCreator
    public static AliasTypeName forValue(String value) throws IOException {
        if (value.equals("Alternate")) return ALTERNATE;
        if (value.equals("Consumer")) return CONSUMER;
        if (value.equals("")) return EMPTY;
        if (value.equals("Provider")) return PROVIDER;
        if (value.equals("Short")) return SHORT;
        if (value.equals("Code")) return CODE;
        if (value.equals("UseUnitGenericName")) return USEUNITGENERICNAME;
        if (value.equals("Trade Name")) return TRADENAME;
        if (value.equals("OuterGenericName")) return OUTERGENERICNAME;
        if (value.equals("Fully Specified Name")) return FULLY_SPECIFIED_NAME;
        throw new IOException("Cannot deserialize AliasTypeName:'"+value+"'");
    }
}