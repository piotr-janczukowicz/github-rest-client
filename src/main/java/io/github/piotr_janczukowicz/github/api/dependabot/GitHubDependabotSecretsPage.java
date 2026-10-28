package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * List repository Dependabot secrets response.
 *
 * @param totalCount Total count
 * @param secrets Secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/list-repo-secrets/200")
public record GitHubDependabotSecretsPage(int totalCount, List<GitHubDependabotSecret> secrets) {

    public GitHubDependabotSecretsPage {
        requireNonNull(secrets, "secrets");
    }
}
