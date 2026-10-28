package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** The reaction type to add to the release. */
@CatalogSchema({
    "operations/reactions/create-for-release/requestBody/properties/content",
    "operations/reactions/list-for-release/parameters/3"
})
public enum GitHubReleaseReactionContent {
    PLUS_ONE("+1"),
    LAUGH("laugh"),
    HEART("heart"),
    HOORAY("hooray"),
    ROCKET("rocket"),
    EYES("eyes");

    private static final EnumUtils.WireIndex<GitHubReleaseReactionContent> WIRE_INDEX =
            EnumUtils.index(GitHubReleaseReactionContent.class, GitHubReleaseReactionContent::wireValue);

    private final String wireValue;

    GitHubReleaseReactionContent(final String wireValue) {
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
     * Release reaction content for GitHub's {@code content} string.
     *
     * @param value JSON or query value
     * @return matching content
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known release content
     */
    @JsonCreator
    public static GitHubReleaseReactionContent fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException("Unknown GitHub release reaction content: " + value));
    }
}
