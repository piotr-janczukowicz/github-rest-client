package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Release asset state. */
@CatalogSchema("components/schemas/release-asset/properties/state")
public enum GitHubReleaseAssetState {
    UPLOADED("uploaded"),
    OPEN("open"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubReleaseAssetState> WIRE_INDEX =
            EnumUtils.index(GitHubReleaseAssetState.class, GitHubReleaseAssetState::wireValue);

    private final String wireValue;

    GitHubReleaseAssetState(final String wireValue) {
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
     * Release asset state from a wire value.
     *
     * @param value JSON value
     * @return matching constant, or {@link #UNKNOWN} when the value is not known
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubReleaseAssetState fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
