package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Pull request conversation and inline review comment types to match. */
@CatalogSchema(
        "operations/agent-automations/edit-repo-automation/requestBody/properties/triggers/properties/pull_request_comment/properties/comment_types/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubEditRepoAutomationTriggersPullRequestCommentCommentType {
    TIMELINE_COMMENT("timeline_comment"),
    REVIEW_COMMENT("review_comment");

    private static final EnumUtils.WireIndex<GitHubEditRepoAutomationTriggersPullRequestCommentCommentType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubEditRepoAutomationTriggersPullRequestCommentCommentType.class,
                    GitHubEditRepoAutomationTriggersPullRequestCommentCommentType::wireValue);

    private final String wireValue;

    GitHubEditRepoAutomationTriggersPullRequestCommentCommentType(final String wireValue) {
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
    public static GitHubEditRepoAutomationTriggersPullRequestCommentCommentType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX
                .fromWire(value)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Unknown GitHub edit repo automation triggers pull request comment comment type: " + value));
    }
}
