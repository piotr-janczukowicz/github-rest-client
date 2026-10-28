package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Enterprise Issue Stats
 *
 * @param totalIssues total issues
 * @param openIssues open issues
 * @param closedIssues closed issues
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/enterprise-overview/properties/issues")
@Flavors(GitHubFlavor.GHES)
public record GitHubEnterpriseOverviewIssues(int totalIssues, int openIssues, int closedIssues) {}
