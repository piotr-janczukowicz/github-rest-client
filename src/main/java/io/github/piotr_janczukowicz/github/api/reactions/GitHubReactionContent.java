package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reaction to use. */
@CatalogSchema({
    "components/schemas/reaction/properties/content",
    "operations/reactions/create-for-commit-comment/requestBody/properties/content",
    "operations/reactions/create-for-issue-comment/requestBody/properties/content",
    "operations/reactions/create-for-issue/requestBody/properties/content",
    "operations/reactions/create-for-pull-request-review-comment/requestBody/properties/content",
    "operations/reactions/list-for-commit-comment/parameters/3",
    "operations/reactions/list-for-issue-comment/parameters/3",
    "operations/reactions/list-for-issue/parameters/3",
    "operations/reactions/list-for-pull-request-review-comment/parameters/3"
})
public enum GitHubReactionContent {
    PLUS_ONE("+1"),
    MINUS_ONE("-1"),
    LAUGH("laugh"),
    CONFUSED("confused"),
    HEART("heart"),
    HOORAY("hooray"),
    ROCKET("rocket"),
    EYES("eyes");

    private static final EnumUtils.WireIndex<GitHubReactionContent> WIRE_INDEX =
            EnumUtils.index(GitHubReactionContent.class, GitHubReactionContent::wireValue);

    private final String wireValue;

    GitHubReactionContent(final String wireValue) {
        this.wireValue = wireValue;
    }

    /**
     * Value GitHub sends and expects in JSON and query parameters.
     *
     * @return wire string
     */
    @JsonValue
    public String wireValue() {
        return wireValue;
    }

    /**
     * Reaction content for GitHub's {@code content} string.
     *
     * @param value JSON or query value
     * @return matching content
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known content
     */
    @JsonCreator
    public static GitHubReactionContent fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub reaction content: " + value));
    }
}
