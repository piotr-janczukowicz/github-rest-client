package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.util.EnumUtils;

/** Top-level discussion comments and replies to match. Omit to match both. */
@CatalogSchema(
        "operations/agent-automations/list-repo-automations/200/properties/automations/items/properties/triggers/properties/discussion_comment/properties/comment_types/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType {
    COMMENT("comment"),
    REPLY("reply"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType>
            WIRE_INDEX = EnumUtils.index(
                    GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType.class,
                    GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType::wireValue);

    private final String wireValue;

    GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType(final String wireValue) {
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
     */
    @JsonCreator
    public static GitHubListRepoAutomationsAutomationTriggersDiscussionCommentCommentType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
