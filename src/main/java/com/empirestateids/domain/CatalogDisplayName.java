
package com.empirestateids.domain;

import java.io.IOException;
import com.fasterxml.jackson.annotation.*;

public enum CatalogDisplayName {
    AMA_CPT_IMMUNIZATION_SUBSET,CDC_VACCINE_GROUP,
    FDA_NDC_IMMUNIZATION_UNIT_OF_USE,VIEWPOINT_MODEL_CONTROL,
    CDC_VACCINE_INFORMATION_STATEMENTS,CDC_VACCINE_PRODUCT,
    VIEWPOINT_MAP_CONTROL,CDC_VACCINE_ADMINISTERED,
    FDA_NDC_IMMUNIZATION_UNIT_OF_SALE,CDC_MANUFACTURERS_OF_VACCINE,
    VIEWPOINT_CATALOG_CONTROL;

    @JsonValue
    public String toValue() {
        switch (this) {
            case AMA_CPT_IMMUNIZATION_SUBSET: return "AMA CPT Immunization Subset";
            case CDC_VACCINE_GROUP: return "CDC Vaccine Group";
            case FDA_NDC_IMMUNIZATION_UNIT_OF_USE: return "FDA NDC Immunization Unit of Use";
            case VIEWPOINT_MODEL_CONTROL: return "Viewpoint Model Control";
            case CDC_VACCINE_INFORMATION_STATEMENTS: return "CDC Vaccine Information Statements (VIS)";
            case CDC_VACCINE_PRODUCT: return "CDC Vaccine Product";
            case VIEWPOINT_MAP_CONTROL: return "Viewpoint Map Control";
            case CDC_VACCINE_ADMINISTERED: return "CDC Vaccine Administered (CVX)";
            case FDA_NDC_IMMUNIZATION_UNIT_OF_SALE: return "FDA NDC Immunization Unit of Sale";
            case CDC_MANUFACTURERS_OF_VACCINE: return "CDC Manufacturers of Vaccine (MVX)";
            case VIEWPOINT_CATALOG_CONTROL: return "Viewpoint Catalog Control";

        }
        return null;
    }

    @JsonCreator
    public static CatalogDisplayName forValue(String value) throws IOException {
        if (value.equals("AMA CPT Immunization Subset")) return AMA_CPT_IMMUNIZATION_SUBSET;
        if (value.equals("CDC Vaccine Group")) return CDC_VACCINE_GROUP;
        if (value.equals("FDA NDC Immunization Unit of Use")) return FDA_NDC_IMMUNIZATION_UNIT_OF_USE;
        if (value.equals("Viewpoint Model Control")) return VIEWPOINT_MODEL_CONTROL;
        if (value.equals("CDC Vaccine Information Statements (VIS)")) return CDC_VACCINE_INFORMATION_STATEMENTS;
        if (value.equals("CDC Vaccine Product")) return CDC_VACCINE_PRODUCT;
        if (value.equals("Viewpoint Map Control")) return VIEWPOINT_MAP_CONTROL;
        if (value.equals("CDC Vaccine Administered (CVX)")) return CDC_VACCINE_ADMINISTERED;
        if (value.equals("FDA NDC Immunization Unit of Sale")) return FDA_NDC_IMMUNIZATION_UNIT_OF_SALE;
        if (value.equals("CDC Manufacturers of Vaccine (MVX)")) return CDC_MANUFACTURERS_OF_VACCINE;
        if (value.equals("Viewpoint Catalog Control")) return VIEWPOINT_CATALOG_CONTROL;
        throw new IOException("Cannot deserialize CatalogDisplayName:'"+value+"'");
    }
}