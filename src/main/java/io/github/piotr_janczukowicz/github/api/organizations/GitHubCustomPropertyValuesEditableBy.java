package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Who can edit custom property values. */
@CatalogSchema("components/schemas/custom-property/properties/values_editable_by")
public enum GitHubCustomPropertyValuesEditableBy {
    ORG_ACTORS("org_actors"),
    ORG_AND_REPO_ACTORS("org_and_repo_actors");

    private static final EnumUtils.WireIndex<GitHubCustomPropertyValuesEditableBy> WIRE_INDEX = EnumUtils.index(
            GitHubCustomPropertyValuesEditableBy.class, GitHubCustomPropertyValuesEditableBy::wireValue);

    private final String wireValue;

    GitHubCustomPropertyValuesEditableBy(final String wireValue) {
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
     * Values editable by policy from a wire value.
     *
     * @param value JSON value
     * @return matching policy
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known policy
     */
    @JsonCreator
    public static GitHubCustomPropertyValuesEditableBy fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub custom property values editable by policy: " + value));
    }
}
