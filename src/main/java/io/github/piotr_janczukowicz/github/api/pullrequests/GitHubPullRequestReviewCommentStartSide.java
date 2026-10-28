package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Starting side for a multi-line pull request review comment. */
@CatalogSchema("operations/pulls/create-review-comment/requestBody/properties/start_side")
public enum GitHubPullRequestReviewCommentStartSide {
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    SIDE("side");

    private static final EnumUtils.WireIndex<GitHubPullRequestReviewCommentStartSide> WIRE_INDEX = EnumUtils.index(
            GitHubPullRequestReviewCommentStartSide.class, GitHubPullRequestReviewCommentStartSide::wireValue);

    private final String wireValue;

    GitHubPullRequestReviewCommentStartSide(final String wireValue) {
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
     * Start side from a wire value.
     *
     * @param value JSON value
     * @return matching start side
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known start side
     */
    @JsonCreator
    public static GitHubPullRequestReviewCommentStartSide fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub pull request review comment start side: " + value));
    }
}
