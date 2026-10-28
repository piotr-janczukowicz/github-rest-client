package io.github.piotr_janczukowicz.github.api.securityadvisories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A credit summary on a repository security advisory.
 *
 * @param login The username of the user credited.
 * @param type The type of credit the user is receiving.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/repository-advisory/properties/credits/items")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisoryCreditSummary(
        @Nullable String login, @Nullable GitHubSecurityAdvisoryCreditType type) {}
