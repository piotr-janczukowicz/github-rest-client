package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Wire values of GitHubPullRequestReviewCommentEventCommentUserType. */
@CatalogSchema(
        "components/schemas/pull-request-review-comment-event/properties/comment/properties/user/properties/type")
public enum GitHubPullRequestReviewCommentEventCommentUserType {
    /** Bot. */
    BOT("Bot"),
    /** User. */
    USER("User"),
    /** Organization. */
    ORGANIZATION("Organization"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubPullRequestReviewCommentEventCommentUserType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubPullRequestReviewCommentEventCommentUserType.class,
                    GitHubPullRequestReviewCommentEventCommentUserType::wireValue);

    private final String wireValue;

    GitHubPullRequestReviewCommentEventCommentUserType(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * JSON {@code wireValue} of this constant.
     *
     * @return wire value
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Parses the wire value.
     *
     * @param value wire value
     * @return constant
     * @throws NullPointerException if {@code value} is {@code null}
     */
    @JsonCreator
    public static GitHubPullRequestReviewCommentEventCommentUserType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
