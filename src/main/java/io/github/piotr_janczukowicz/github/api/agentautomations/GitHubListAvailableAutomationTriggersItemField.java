package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A configurable field in a public Automation trigger definition.
 *
 * @param id The stable field identifier used in configured triggers.
 * @param label The human-readable field label.
 * @param required Whether the field must be provided.
 * @param description Markdown guidance for configuring the field.
 * @param schema A restricted JSON Schema fragment describing the field value accepted by Automation create and update
 *     requests.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-automation-triggers/200/items/properties/fields/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationTriggersItemField(
        String id,
        String label,
        boolean required,
        @Nullable String description,
        GitHubListAvailableAutomationTriggersItemFieldSchema schema) {
    public GitHubListAvailableAutomationTriggersItemField {
        requireNonNull(id, "id");
        requireNonNull(label, "label");
        requireNonNull(schema, "schema");
    }
}
