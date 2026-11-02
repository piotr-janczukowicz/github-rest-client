package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A client product associated with an automation task.
 *
 * @param integrationId Server-resolved integration identity for the client product.
 * @param slug Stable client product identifier, not an agent or host identifier. Clients must tolerate unknown future
 *     slugs.
 * @param creator True only when the service has explicit evidence this client created the containing session or task.
 *     False does not imply the original client is known.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-repo-automation-tasks/200/properties/tasks/items/properties/clients/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepoAutomationTasksTaskClient(String integrationId, String slug, boolean creator) {
    public GitHubListRepoAutomationTasksTaskClient {
        requireNonNull(integrationId, "integrationId");
        requireNonNull(slug, "slug");
    }
}
