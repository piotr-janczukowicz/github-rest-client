package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** State filter for listing GitHub package versions. */
@CatalogSchema({
    "operations/packages/get-all-package-versions-for-package-owned-by-org/parameters/5",
    "operations/packages/get-all-package-versions-for-package-owned-by-authenticated-user/parameters/4"
})
public enum GitHubPackageVersionState {
    ACTIVE("active"),
    DELETED("deleted");

    private static final EnumUtils.WireIndex<GitHubPackageVersionState> WIRE_INDEX =
            EnumUtils.index(GitHubPackageVersionState.class, GitHubPackageVersionState::wireValue);

    private final String wireValue;

    GitHubPackageVersionState(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Package version state from a wire value.
     *
     * @param value query parameter value
     * @return matching state
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known state
     */
    @JsonCreator
    public static GitHubPackageVersionState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub package version state: " + value));
    }
}
