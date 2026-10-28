package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestoneState;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Update a milestone.
 *
 * @param title The title of the milestone.
 * @param state The state of the milestone. Either `open` or `closed`.
 * @param description A description of the milestone.
 * @param dueOn The milestone due date. This is a timestamp in ISO 8601 format: `YYYY-MM-DDTHH:MM:SSZ`.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/update-milestone/requestBody")
@RecordBuilder
public record GitHubUpdateMilestonePayload(
        @Nullable String title,
        @Nullable GitHubMilestoneState state,
        @Nullable String description,
        @Nullable Instant dueOn) {}
