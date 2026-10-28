package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * User who committed to a repository with GitHub Advanced Security enabled.
 *
 * @param userLogin The login of the user
 * @param lastPushedDate The date of the last push by the user
 * @param lastPushedEmail The email address the user last pushed with
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/advanced-security-active-committers-user")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubAdvancedSecurityActiveCommittersUser(
        String userLogin, String lastPushedDate, String lastPushedEmail) {

    public GitHubAdvancedSecurityActiveCommittersUser {
        requireNonNull(userLogin, "userLogin");
        requireNonNull(lastPushedDate, "lastPushedDate");
        requireNonNull(lastPushedEmail, "lastPushedEmail");
    }
}
