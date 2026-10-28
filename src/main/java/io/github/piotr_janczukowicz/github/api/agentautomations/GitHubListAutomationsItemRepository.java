package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Repository identity for an automation in cross-repository list responses.
 *
 * @param id The unique identifier of the repository.
 * @param owner The repository owner login.
 * @param name The repository name.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-automations/200/items/properties/repository")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationsItemRepository(long id, String owner, String name) {
    public GitHubListAutomationsItemRepository {
        requireNonNull(owner, "owner");
        requireNonNull(name, "name");
    }
}
