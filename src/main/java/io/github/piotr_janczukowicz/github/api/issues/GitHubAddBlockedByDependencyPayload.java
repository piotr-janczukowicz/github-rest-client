package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Add a dependency an issue is blocked by.
 *
 * @param issueId The id of the issue that blocks the current issue
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/add-blocked-by-dependency/requestBody")
@RecordBuilder
public record GitHubAddBlockedByDependencyPayload(long issueId) {}
