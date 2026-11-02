package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A tool that can be selected for use in an automation.
 *
 * @param id The unique identifier of the tool accepted when creating or updating an automation.
 * @param title The human-readable name of the tool.
 * @param description A short description of what the tool does.
 * @param scope The tool's behavior level. {@code read} tools only retrieve data. {@code write} tools can change data.
 *     This is not a GitHub token permission.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-available-repo-automation-tools/200/properties/tool_groups/items/properties/tools/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAvailableRepoAutomationToolsResponseToolGroupTool(
        String id,
        String title,
        String description,
        @Nullable GitHubListAvailableRepoAutomationToolsResponseToolGroupToolScope scope) {
    public GitHubListAvailableRepoAutomationToolsResponseToolGroupTool {
        requireNonNull(id, "id");
        requireNonNull(title, "title");
        requireNonNull(description, "description");
    }
}
