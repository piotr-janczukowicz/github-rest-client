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
        "operations/agent-automations/list-automations/200/items/properties/triggers/properties/discussion_comment/properties/comment_types/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public enum GitHubListAutomationsItemTriggersDiscussionCommentCommentType {
    COMMENT("comment"),
    REPLY("reply"),
    /** A value this library version does not know. */
    UNKNOWN("unknown");

    private static final EnumUtils.WireIndex<GitHubListAutomationsItemTriggersDiscussionCommentCommentType> WIRE_INDEX =
            EnumUtils.index(
                    GitHubListAutomationsItemTriggersDiscussionCommentCommentType.class,
                    GitHubListAutomationsItemTriggersDiscussionCommentCommentType::wireValue);

    private final String wireValue;

    GitHubListAutomationsItemTriggersDiscussionCommentCommentType(final String wireValue) {
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
    public static GitHubListAutomationsItemTriggersDiscussionCommentCommentType fromWire(final String value) {
        requireNonNull(value, "value");

        return WIRE_INDEX.fromWire(value, UNKNOWN);
    }
}
