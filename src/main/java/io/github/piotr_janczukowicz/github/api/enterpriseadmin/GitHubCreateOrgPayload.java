package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Request to create an organization on GitHub Enterprise Server.
 *
 * @param login The organization's username
 * @param admin The login of the user who will manage this organization
 * @param profileName The organization's display name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-org/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubCreateOrgPayload(
        String login, String admin, @Nullable String profileName) {
    public GitHubCreateOrgPayload {
        requireNonNull(login, "login");
        requireNonNull(admin, "admin");
    }
}
