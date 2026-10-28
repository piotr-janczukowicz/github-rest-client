package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Credits.
 *
 * @param user A GitHub user.
 * @param type The type of credit the user is receiving.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/global-advisory/properties/credits/items")
@RecordBuilder
public record GitHubGlobalAdvisoryCredit(GitHubSimpleUser user, GitHubSecurityAdvisoryCreditType type) {

    public GitHubGlobalAdvisoryCredit {
        requireNonNull(user, "user");
        requireNonNull(type, "type");
    }
}
