package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubCreateUserPayload
 *
 * @param login The user's username.
 * @param email **Required for built-in authentication.** The user's email address. This parameter can be omitted when
 *     using CAS, LDAP, or SAML. For more information, see "[About authentication for your
 *     enterprise](https://docs.github.com/enterprise-server@3.22/admin/identity-and-access-management/managing-iam-for-your-enterprise/about-authentication-for-your-enterprise)."
 * @param suspended Whether to set the user as suspended when the user is created.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/create-user/requestBody")
@Flavors(GitHubFlavor.GHES)
public record GitHubCreateUserPayload(
        String login, @Nullable String email, @Nullable Boolean suspended) {
    public GitHubCreateUserPayload {
        requireNonNull(login, "login");
    }
}
