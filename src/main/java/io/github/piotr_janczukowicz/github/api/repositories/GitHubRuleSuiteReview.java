package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A review associated with the pull request of a pull request rule evaluation.
 *
 * @param id unique identifier of the review
 * @param user user who submitted the review
 * @param state state of the review
 */
@CatalogSchema("components/schemas/rule-suite-pull-request/properties/pull_request/properties/reviews/items")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuiteReview(
        @Nullable Long id,
        @Nullable GitHubRuleSuiteReviewUser user,
        @Nullable String state) {}
