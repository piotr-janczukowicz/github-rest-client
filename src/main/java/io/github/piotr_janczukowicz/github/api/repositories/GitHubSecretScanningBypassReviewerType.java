package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The type of the bypass reviewer. */
@CatalogSchema(
        "components/schemas/security-and-analysis/properties/secret_scanning_delegated_bypass_options/properties/reviewers/items/properties/reviewer_type")
public enum GitHubSecretScanningBypassReviewerType {
    TEAM("TEAM"),
    ROLE("ROLE"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningBypassReviewerType> WIRE_INDEX = EnumUtils.index(
            GitHubSecretScanningBypassReviewerType.class, GitHubSecretScanningBypassReviewerType::wireValue);

    private final String wireValue;

    GitHubSecretScanningBypassReviewerType(final String wireValue) {
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
     * The type of the bypass reviewer from a wire value.
     *
     * @param value JSON value
     * @return matching value
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubSecretScanningBypassReviewerType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
