package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Merge upstream merge type. */
@CatalogSchema("components/schemas/merged-upstream/properties/merge_type")
public enum GitHubMergedUpstreamMergeType {
    MERGE("merge"),
    FAST_FORWARD("fast-forward"),
    NONE("none");

    private static final EnumUtils.WireIndex<GitHubMergedUpstreamMergeType> WIRE_INDEX =
            EnumUtils.index(GitHubMergedUpstreamMergeType.class, GitHubMergedUpstreamMergeType::wireValue);

    private final String wireValue;

    GitHubMergedUpstreamMergeType(final String wireValue) {
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
     * Merge upstream merge type from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubMergedUpstreamMergeType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub merged upstream merge type: " + value));
    }
}
