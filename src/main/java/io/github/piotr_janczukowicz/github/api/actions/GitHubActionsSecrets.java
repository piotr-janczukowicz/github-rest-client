package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsSecret;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Actions secrets of an environment or a repository.
 *
 * @param totalCount Total number of secrets
 * @param secrets The secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/actions/list-environment-secrets/200",
    "operations/actions/list-repo-secrets/200",
    "operations/actions/list-repo-organization-secrets/200"
})
public record GitHubActionsSecrets(int totalCount, List<GitHubActionsSecret> secrets) {

    public GitHubActionsSecrets {
        requireNonNull(secrets, "secrets");
    }
}
