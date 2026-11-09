package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/**
 * Pull request comment comment type input. Shared by the 1 schemas that have this shape:
 * pull_request_comment_comment_type.
 */
@CatalogSchema({
    "operations/agent-automations/create-repo-automation/requestBody/properties/triggers/properties/pull_request_comment/properties/comment_types/items",
    "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_comment/properties/comment_types/items"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubAutomationPullRequestCommentCommentTypeInput {
    TIMELINE_COMMENT("timeline_comment"),
    REVIEW_COMMENT("review_comment");

    private static final EnumUtils.WireIndex<GitHubAutomationPullRequestCommentCommentTypeInput> WIRE_INDEX =
            EnumUtils.index(
                    GitHubAutomationPullRequestCommentCommentTypeInput.class,
                    GitHubAutomationPullRequestCommentCommentTypeInput::wireValue);

    private final String wireValue;

    GitHubAutomationPullRequestCommentCommentTypeInput(final String wireValue) {
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
     * Resolves the constant from its wire value.
     *
     * @param value JSON value
     * @return matching constant
     * @throws NullPointerException if {@code value} is {@code null}
     * @throws IllegalArgumentException if {@code value} is not a known value
     */
    @JsonCreator
    public static GitHubAutomationPullRequestCommentCommentTypeInput fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub automation pull request comment comment type input: " + value));
    }
}
