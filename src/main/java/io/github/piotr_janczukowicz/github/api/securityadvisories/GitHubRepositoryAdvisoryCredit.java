package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * A credit given to a user for a repository security advisory.
 *
 * @param user A GitHub user.
 * @param type The type of credit the user is receiving.
 * @param state The state of the user's acceptance of the credit.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository-advisory-credit")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisoryCredit(
        GitHubSimpleUser user, GitHubSecurityAdvisoryCreditType type, GitHubRepositoryAdvisoryCreditState state) {

    public GitHubRepositoryAdvisoryCredit {
        requireNonNull(user, "user");
        requireNonNull(type, "type");
        requireNonNull(state, "state");
    }
}
