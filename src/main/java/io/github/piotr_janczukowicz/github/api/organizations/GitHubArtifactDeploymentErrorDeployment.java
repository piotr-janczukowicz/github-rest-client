package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Deployment payload included in a partial-success error.
 *
 * @param name Name
 * @param digest Digest
 * @param deploymentName Deployment name
 * @param version Version
 * @param status Status
 * @param githubRepository GitHub repository
 * @param tags Tags
 * @param runtimeRisks Runtime risks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-cluster-deployment-records/207/properties/errors/items/properties/deployment")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactDeploymentErrorDeployment(
        @Nullable String name,
        @Nullable String digest,
        @Nullable String deploymentName,
        @Nullable String version,
        @Nullable String status,
        @Nullable String githubRepository,
        @Nullable Map<String, String> tags,
        @Nullable List<String> runtimeRisks) {}
