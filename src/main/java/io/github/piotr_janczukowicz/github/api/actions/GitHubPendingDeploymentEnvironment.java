package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Environment of a pending deployment.
 *
 * @param id The id of the environment
 * @param nodeId The node id of the environment
 * @param name The name of the environment
 * @param url The API URL of the environment
 * @param htmlUrl The web URL of the environment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pending-deployment/properties/environment")
public record GitHubPendingDeploymentEnvironment(
        @Nullable Long id,
        @Nullable String nodeId,
        @Nullable String name,
        @Nullable String url,
        @Nullable String htmlUrl) {}
