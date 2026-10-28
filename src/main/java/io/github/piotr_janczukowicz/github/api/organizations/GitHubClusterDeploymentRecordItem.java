package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_DIGEST;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_GITHUB_REPOSITORY;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireUniqueItems;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Deployment item in a set-cluster or cluster-job request body.
 *
 * <p>Schema pointer is the set-cluster items schema; the create-job items schema is machine-identical and reuses this
 * type.
 *
 * @param name Name
 * @param digest Digest
 * @param version Version
 * @param status Status
 * @param deploymentName Deployment name
 * @param githubRepository GitHub repository
 * @param tags Tags
 * @param runtimeRisks Runtime risks
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-cluster-deployment-records/requestBody/properties/deployments/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubClusterDeploymentRecordItem(
        String name,
        String digest,
        @Nullable String version,
        @Nullable GitHubArtifactDeploymentStatus status,
        String deploymentName,
        @Nullable String githubRepository,
        @Nullable Map<String, String> tags,
        @Nullable List<GitHubArtifactRuntimeRisk> runtimeRisks) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DIGEST = "digest";
    private static final String PARAM_DEPLOYMENT_NAME = "deploymentName";
    private static final String PARAM_GITHUB_REPOSITORY = "githubRepository";
    private static final String PARAM_RUNTIME_RISKS = "runtimeRisks";

    public GitHubClusterDeploymentRecordItem {
        name = requireMaxLength(requireMinLength(name, 1, PARAM_NAME), 256, PARAM_NAME);
        digest = requireMatches(digest, ARTIFACT_DIGEST, PARAM_DIGEST);
        version = requireMaxLengthIfPresent(version, 100, "version");
        deploymentName = requireMaxLength(
                requireMinLength(deploymentName, 1, PARAM_DEPLOYMENT_NAME), 256, PARAM_DEPLOYMENT_NAME);
        githubRepository = requireMaxLengthIfPresent(githubRepository, 100, PARAM_GITHUB_REPOSITORY);
        if (githubRepository != null) {
            requireMatches(githubRepository, ARTIFACT_GITHUB_REPOSITORY, PARAM_GITHUB_REPOSITORY);
        }
        if (tags != null) {
            tags = Map.copyOf(tags);
        }
        if (runtimeRisks != null) {
            runtimeRisks = List.copyOf(
                    requireUniqueItems(requireSizeAtMost(runtimeRisks, 4, PARAM_RUNTIME_RISKS), PARAM_RUNTIME_RISKS));
        }
    }
}
