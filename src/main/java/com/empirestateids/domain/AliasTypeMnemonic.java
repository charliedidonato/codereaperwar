package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum AliasTypeMnemonic {
    ALTERNATE, CONSUMER, PROVIDER, SHORT,
    FSN, CODE, TRADENAME, OUTERGENERICNAME,
    USEUNITGENERICNAME;

    @JsonValue
    public String toValue() {
        switch (this) {
            case ALTERNATE: return "ALTERNATE";
            case CONSUMER: return "CONSUMER";
            case PROVIDER: return "PROVIDER";
            case SHORT: return "SHORT";
            case FSN: return "FSN";
            case CODE: return "CODE";
            case TRADENAME: return "TRADENAME";
            case OUTERGENERICNAME: return "OUTERGENERICNAME";
            case USEUNITGENERICNAME: return "USEUNITGENERICNAME";
        }
        return null;
    }

    @JsonCreator
    public static AliasTypeMnemonic forValue(String value) throws IOException {
        if (value.equals("ALTERNATE")) return ALTERNATE;
        if (value.equals("CONSUMER")) return CONSUMER;
        if (value.equals("PROVIDER")) return PROVIDER;
        if (value.equals("SHORT")) return SHORT;
        if (value.equals("FSN")) return FSN;
        if (value.equals("CODE")) return CODE;
        if (value.equals("TRADENAME")) return TRADENAME;
        if (value.equals("OUTERGENERICNAME")) return OUTERGENERICNAME;
        if (value.equals("USEUNITGENERICNAME")) return USEUNITGENERICNAME;
        throw new IOException("Cannot deserialize AliasTypeMnemonic:'"+value+"'");
    }
}