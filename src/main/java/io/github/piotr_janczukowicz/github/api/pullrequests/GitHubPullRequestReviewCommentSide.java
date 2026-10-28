package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Side of a pull request diff for a review comment. */
@CatalogSchema({
    "operations/pulls/create-review-comment/requestBody/properties/side",
    "components/schemas/pull-request-review-comment/properties/side",
    "components/schemas/review-comment/properties/side"
})
public enum GitHubPullRequestReviewCommentSide {
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private static final EnumUtils.WireIndex<GitHubPullRequestReviewCommentSide> WIRE_INDEX =
            EnumUtils.index(GitHubPullRequestReviewCommentSide.class, GitHubPullRequestReviewCommentSide::wireValue);

    private final String wireValue;

    GitHubPullRequestReviewCommentSide(final String wireValue) {
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
     * Side from a wire value.
     *
     * @param value JSON value
     * @return matching side
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known side
     */
    @JsonCreator
    public static GitHubPullRequestReviewCommentSide fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() ->
                        new IllegalArgumentException("Unknown GitHub pull request review comment side: " + value));
    }
}
