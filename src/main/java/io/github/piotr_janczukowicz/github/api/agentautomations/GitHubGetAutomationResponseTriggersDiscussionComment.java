package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonEmpty;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration of the discussion comment trigger.
 *
 * @param commentTypes Top-level discussion comments and replies to match. Omit to match both.
 * @param categories Discussion category names matched case-insensitively.
 * @param bodySubstring A case-insensitive substring matched against the event body.
 * @param writeScope Override the Automation {@code write_scope} for runs started by this trigger. If omitted, the
 *     automation's {@code write_scope} is used. Organization or repository policy may restrict the effective scope to
 *     {@code trigger}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/get-automation/200/properties/triggers/properties/discussion_comment")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetAutomationResponseTriggersDiscussionComment(
        @Nullable List<GitHubGetAutomationResponseTriggersDiscussionCommentCommentType> commentTypes,
        @Nullable List<String> categories,
        @Nullable String bodySubstring,
        @Nullable GitHubGetAutomationResponseTriggersDiscussionCommentWriteScope writeScope) {
    public GitHubGetAutomationResponseTriggersDiscussionComment {
        if (commentTypes != null) {
            requireUniqueItems(requireSizeBetween(commentTypes, 1, 2, "commentTypes"), "commentTypes");
        }
        if (categories != null) {
            requireUniqueItems(requireNonEmpty(categories, "categories"), "categories");
        }
    }
}
