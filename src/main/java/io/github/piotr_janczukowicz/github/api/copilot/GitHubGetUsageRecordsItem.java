package io.github.piotr_janczukowicz.github.api.copilot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Copilot usage record of an enterprise.
 *
 * @param type The record type (request or response).
 * @param userId The ID of the user.
 * @param enterpriseId The ID of the enterprise.
 * @param githubRequestId The GitHub request ID.
 * @param endpoint The endpoint that was called.
 * @param body The request or response body.
 * @param timestamp Milliseconds since Unix epoch.
 * @param eventId The event ID.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/copilot/get-usage-records/200/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetUsageRecordsItem(
        @Nullable String type,
        @Nullable Long userId,
        @Nullable Long enterpriseId,
        @Nullable String githubRequestId,
        @Nullable String endpoint,
        @Nullable String body,
        @JsonProperty("@timestamp") @Nullable Long timestamp,
        @Nullable String eventId) {}
