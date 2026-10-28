package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Credits.
 *
 * @param login The username of the user credited.
 * @param type The type of credit the user is receiving.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/repository-advisory-create/properties/credits/items",
    "components/schemas/repository-advisory-update/properties/credits/items"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisoryCreditPayload(String login, GitHubSecurityAdvisoryCreditType type) {

    public GitHubRepositoryAdvisoryCreditPayload {
        requireNonNull(login, "login");
        requireNonNull(type, "type");
    }
}
