package io.github.piotr_janczukowicz.github.api.agenttasks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Billing units consumed by the session.
 *
 * @param type Type
 * @param amount Amount
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/get-task-by-id/200/properties/sessions/items/properties/usage")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetTaskByIdResponseSessionUsage(GitHubGetTaskByIdResponseSessionUsageType type, double amount) {
    public GitHubGetTaskByIdResponseSessionUsage {
        requireNonNull(type, "type");
    }
}
