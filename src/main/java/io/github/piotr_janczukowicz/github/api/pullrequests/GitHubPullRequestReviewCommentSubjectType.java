package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Level at which a pull request review comment is targeted. */
@CatalogSchema({
    "operations/pulls/create-review-comment/requestBody/properties/subject_type",
    "components/schemas/pull-request-review-comment/properties/subject_type",
    "components/schemas/review-comment/properties/subject_type"
})
public enum GitHubPullRequestReviewCommentSubjectType {
    LINE("line"),
    FILE("file");

    private static final EnumUtils.WireIndex<GitHubPullRequestReviewCommentSubjectType> WIRE_INDEX = EnumUtils.index(
            GitHubPullRequestReviewCommentSubjectType.class, GitHubPullRequestReviewCommentSubjectType::wireValue);

    private final String wireValue;

    GitHubPullRequestReviewCommentSubjectType(final String wireValue) {
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
     * Subject type from a wire value.
     *
     * @param value JSON value
     * @return matching subject type
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known subject type
     */
    @JsonCreator
    public static GitHubPullRequestReviewCommentSubjectType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub pull request review comment subject type: " + value));
    }
}
