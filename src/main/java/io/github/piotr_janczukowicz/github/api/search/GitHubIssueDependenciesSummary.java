package io.github.piotr_janczukowicz.github.api.search;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Issue Dependencies Summary.
 *
 * @param blockedBy Blocked by
 * @param blocking Blocking
 * @param totalBlockedBy Total blocked by
 * @param totalBlocking Total blocking
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-dependencies-summary")
public record GitHubIssueDependenciesSummary(int blockedBy, int blocking, int totalBlockedBy, int totalBlocking) {}
