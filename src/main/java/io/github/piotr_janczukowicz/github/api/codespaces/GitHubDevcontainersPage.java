package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List devcontainer configurations in a repository for the authenticated user response.
 *
 * @param totalCount Total count
 * @param devcontainers Devcontainer configurations
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/list-devcontainers-in-repository-for-authenticated-user/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubDevcontainersPage(int totalCount, List<GitHubDevcontainer> devcontainers) {

    public GitHubDevcontainersPage {
        requireNonNull(devcontainers, "devcontainers");
    }
}
