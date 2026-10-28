package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Organization actions secrets.
 *
 * @param totalCount Total number of secrets
 * @param secrets The secrets
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-org-secrets/200")
public record GitHubOrganizationActionsSecrets(int totalCount, List<GitHubOrganizationActionsSecret> secrets) {

    public GitHubOrganizationActionsSecrets {
        requireNonNull(secrets, "secrets");
    }
}
