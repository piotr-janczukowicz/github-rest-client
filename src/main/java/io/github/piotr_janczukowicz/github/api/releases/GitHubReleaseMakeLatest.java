package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Release make-latest setting. */
@CatalogSchema({
    "operations/repos/create-release/requestBody/properties/make_latest",
    "operations/repos/update-release/requestBody/properties/make_latest"
})
public enum GitHubReleaseMakeLatest {
    TRUE("true"),
    FALSE("false"),
    LEGACY("legacy");

    private static final EnumUtils.WireIndex<GitHubReleaseMakeLatest> WIRE_INDEX =
            EnumUtils.index(GitHubReleaseMakeLatest.class, GitHubReleaseMakeLatest::wireValue);

    private final String wireValue;

    GitHubReleaseMakeLatest(final String wireValue) {
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
     * Make-latest setting from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubReleaseMakeLatest fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub release make_latest value: " + value));
    }
}
