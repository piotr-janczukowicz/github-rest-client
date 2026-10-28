package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Sub-issues Summary.
 *
 * @param total Total
 * @param completed Completed
 * @param percentCompleted Percent completed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/sub-issues-summary")
public record GitHubSubIssuesSummary(int total, int completed, int percentCompleted) {}
