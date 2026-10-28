package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Artifact and log retention settings with the maximum allowed.
 *
 * @param days The number of days checks, workflow runs, commit statuses, artifacts, and logs are retained
 * @param maximumAllowedDays The maximum number of days that can be configured
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-artifact-and-log-retention-response")
public record GitHubActionsArtifactAndLogRetentionResponse(int days, int maximumAllowedDays) {}
