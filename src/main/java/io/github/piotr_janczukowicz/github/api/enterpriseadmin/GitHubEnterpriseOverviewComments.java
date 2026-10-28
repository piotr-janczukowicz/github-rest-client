package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Comment Stats
 *
 * @param totalCommitComments total commit comments
 * @param totalGistComments total gist comments
 * @param totalIssueComments total issue comments
 * @param totalPullRequestComments total pull request comments
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/comments")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewComments(
        int totalCommitComments, int totalGistComments, int totalIssueComments, int totalPullRequestComments) {}
