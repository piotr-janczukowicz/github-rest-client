package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An environment that was approved or rejected.
 *
 * @param id The id of the environment
 * @param nodeId The node id of the environment
 * @param name The name of the environment
 * @param url The API URL of the environment
 * @param htmlUrl The web URL of the environment
 * @param createdAt The time that the environment was created
 * @param updatedAt The time that the environment was last updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/environment-approvals/properties/environments/items")
public record GitHubEnvironmentApprovalsEnvironment(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String name,
        @Nullable String url,
        @Nullable String htmlUrl,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {}
