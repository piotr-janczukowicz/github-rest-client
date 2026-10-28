package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * The pull request associated with a pull request rule evaluation.
 *
 * @param id unique identifier of the pull request
 * @param number number of the pull request
 * @param user user who created the pull request
 * @param reviews reviews associated with the pull request
 * @param proofOfPresence proof of presence evaluation for the pull request
 */
@CatalogSchema("components/schemas/rule-suite-pull-request/properties/pull_request")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRuleSuitePullRequestMetadata(
        @Nullable Long id,
        @Nullable Integer number,
        @Nullable GitHubRuleSuitePullRequestUser user,
        @Nullable List<GitHubRuleSuiteReview> reviews,
        @Nullable GitHubRuleSuiteProofOfPresence proofOfPresence) {}
