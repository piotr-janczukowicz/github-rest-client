package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A public Automation trigger and its configurable fields.
 *
 * @param id The stable public trigger identifier.
 * @param title The human-readable trigger title.
 * @param description A short description of when the trigger fires.
 * @param fields The trigger-specific public configuration fields.
 * @param supportsRunNow Whether this trigger supports event-specific Run now simulation. Omit to indicate support.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-automation-triggers/200/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationTriggersItem(
        String id,
        String title,
        String description,
        List<GitHubListAvailableAutomationTriggersItemField> fields,
        @Nullable Boolean supportsRunNow) {
    public GitHubListAvailableAutomationTriggersItem {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
        requireNonNull(fields, "fields");
    }
}
