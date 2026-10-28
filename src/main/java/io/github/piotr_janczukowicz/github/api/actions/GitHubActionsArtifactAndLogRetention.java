package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Artifact and log retention settings.
 *
 * @param days The number of days to retain checks, workflow runs, commit statuses, artifacts, and logs
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-artifact-and-log-retention")
public record GitHubActionsArtifactAndLogRetention(int days) {}
