package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * GitHubUpdateUsernameForUserPayload
 *
 * @param login The user's new username.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/update-username-for-user/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubUpdateUsernameForUserPayload(String login) {
    public GitHubUpdateUsernameForUserPayload {
        requireNonNull(login, "login");
    }
}
