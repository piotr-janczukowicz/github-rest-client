package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * The location type. Because secrets may be found in different types of resources (ie. code, comments, issues, pull
 * requests, discussions), this field identifies the type of resource where the secret was found.
 */
@CatalogSchema("components/schemas/secret-scanning-location/properties/type")
public enum GitHubSecretScanningLocationType {
    COMMIT("commit"),
    WIKI_COMMIT("wiki_commit"),
    ISSUE_TITLE("issue_title"),
    ISSUE_BODY("issue_body"),
    ISSUE_COMMENT("issue_comment"),
    DISCUSSION_TITLE("discussion_title"),
    DISCUSSION_BODY("discussion_body"),
    DISCUSSION_COMMENT("discussion_comment"),
    PULL_REQUEST_TITLE("pull_request_title"),
    PULL_REQUEST_BODY("pull_request_body"),
    PULL_REQUEST_COMMENT("pull_request_comment"),
    PULL_REQUEST_REVIEW("pull_request_review"),
    PULL_REQUEST_REVIEW_COMMENT("pull_request_review_comment"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubSecretScanningLocationType> WIRE_INDEX =
            EnumUtils.index(GitHubSecretScanningLocationType.class, GitHubSecretScanningLocationType::wireValue);

    private final String wireValue;

    GitHubSecretScanningLocationType(final String wireValue) {
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
     * Resolves secret scanning location type from its wire value.
     *
     * @param value JSON value
     * @return matching GitHubSecretScanningLocationType, or {@link #UNKNOWN} for a value this library does not know
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubSecretScanningLocationType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
