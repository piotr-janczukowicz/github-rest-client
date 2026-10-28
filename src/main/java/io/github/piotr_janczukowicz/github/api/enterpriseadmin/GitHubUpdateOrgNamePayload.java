package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Request to rename an organization on GitHub Enterprise Server.
 *
 * @param login The organization's new login name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-org-name/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateOrgNamePayload(String login) {
    public GitHubUpdateOrgNamePayload {
        requireNonNull(login, "login");
    }
}
