package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Error for a deployment that could not be processed during partial success.
 *
 * @param cause Cause
 * @param deployment Deployment
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-cluster-deployment-records/207/properties/errors/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactDeploymentError(
        @Nullable GitHubArtifactDeploymentErrorCause cause,
        @Nullable GitHubArtifactDeploymentErrorDeployment deployment) {}
