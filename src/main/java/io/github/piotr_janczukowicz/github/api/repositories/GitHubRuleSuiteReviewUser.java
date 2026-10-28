package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The user who submitted a review of a pull request rule evaluation.
 *
 * @param id unique identifier of the user
 * @param login handle for the GitHub user account
 * @param type type of the user
 */
@CatalogSchema(
        "components/schemas/rule-suite-pull-request/properties/pull_request/properties/reviews/items/properties/user")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuiteReviewUser(
        @Nullable Long id, @Nullable String login, @Nullable String type) {}
