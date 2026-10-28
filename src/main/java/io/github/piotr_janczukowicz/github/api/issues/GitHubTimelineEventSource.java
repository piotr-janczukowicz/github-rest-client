package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Cross-referenced timeline event source.
 *
 * @param type Type
 * @param issue Issue
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/timeline-cross-referenced-event/properties/source")
public record GitHubTimelineEventSource(
        @Nullable String type, @Nullable GitHubIssue issue) {}
