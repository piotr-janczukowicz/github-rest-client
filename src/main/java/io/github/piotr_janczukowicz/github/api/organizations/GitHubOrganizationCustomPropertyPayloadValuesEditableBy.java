package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Who can edit the values of the custom property. */
@CatalogSchema("components/schemas/organization-custom-property-payload/properties/values_editable_by")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public enum GitHubOrganizationCustomPropertyPayloadValuesEditableBy {
    /** Enterprise actors. */
    ENTERPRISE_ACTORS("enterprise_actors"),
    /** Enterprise and org actors. */
    ENTERPRISE_AND_ORG_ACTORS("enterprise_and_org_actors");

    private static final EnumUtils.WireIndex<GitHubOrganizationCustomPropertyPayloadValuesEditableBy> WIRE_INDEX =
            EnumUtils.index(
                    GitHubOrganizationCustomPropertyPayloadValuesEditableBy.class,
                    GitHubOrganizationCustomPropertyPayloadValuesEditableBy::wireValue);

    private final String wireValue;

    GitHubOrganizationCustomPropertyPayloadValuesEditableBy(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Value from a wire string.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubOrganizationCustomPropertyPayloadValuesEditableBy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub organization custom property payload values editable by: " + value));
    }
}
