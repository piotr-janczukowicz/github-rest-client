package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A tool that can be selected in an Automation.
 *
 * @param id The exact tool identifier accepted by Automation create and edit requests. The catalog is authoritative and
 *     open-ended; clients must accept identifiers they have not seen before.
 * @param title The human-readable tool title.
 * @param description A short description of what the tool does.
 * @param scope The tool's behavior level. {@code read} tools only retrieve data. {@code write} tools can change data.
 *     This describes tool behavior, not a specific GitHub token permission.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-available-automation-tools/200/items/properties/tools/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableAutomationToolsItemTool(
        String id,
        String title,
        String description,
        @Nullable GitHubListAvailableAutomationToolsItemToolScope scope) {
    public GitHubListAvailableAutomationToolsItemTool {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
    }
}
