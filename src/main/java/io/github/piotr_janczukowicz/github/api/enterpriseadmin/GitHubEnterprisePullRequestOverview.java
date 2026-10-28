package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Pull Request Stats
 *
 * @param totalPulls total pulls
 * @param mergedPulls merged pulls
 * @param mergeablePulls mergeable pulls
 * @param unmergeablePulls unmergeable pulls
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-pull-request-overview")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterprisePullRequestOverview(
        int totalPulls, int mergedPulls, int mergeablePulls, int unmergeablePulls) {}
