package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** App permission level {@code read}. */
@CatalogSchema("components/schemas/app-permissions/properties/organization_events")
public enum GitHubAppPermissionRead {
    READ("read");

    private static final EnumUtils.WireIndex<GitHubAppPermissionRead> WIRE_INDEX =
            EnumUtils.index(GitHubAppPermissionRead.class, GitHubAppPermissionRead::wireValue);

    private final String wireValue;

    GitHubAppPermissionRead(final String wireValue) {
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
     * Permission from a wire value.
     *
     * @param value JSON value
     * @return matching permission
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known permission
     */
    @JsonCreator
    public static GitHubAppPermissionRead fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub app permission: " + value));
    }
}
