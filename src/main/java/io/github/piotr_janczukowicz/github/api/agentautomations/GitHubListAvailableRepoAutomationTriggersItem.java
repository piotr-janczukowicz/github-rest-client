package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A public Automation trigger and its configurable fields.
 *
 * @param id The stable public trigger identifier.
 * @param title The human-readable trigger title.
 * @param description A short description of when the trigger fires.
 * @param fields The trigger-specific public configuration fields.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-repo-automation-triggers/200/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationTriggersItem(
        String id, String title, String description, List<GitHubListAvailableRepoAutomationTriggersItemField> fields) {
    public GitHubListAvailableRepoAutomationTriggersItem {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(fields, "fields");
    }
}
