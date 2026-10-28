package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List repository organization Actions secrets response.
 *
 * @param totalCount Total count
 * @param secrets Secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agents/list-repo-organization-secrets/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepoOrganizationSecretsPage(int totalCount, List<GitHubActionsSecret> secrets) {

    public GitHubRepoOrganizationSecretsPage {
        requireNonNull(secrets, "secrets");
    }
}
