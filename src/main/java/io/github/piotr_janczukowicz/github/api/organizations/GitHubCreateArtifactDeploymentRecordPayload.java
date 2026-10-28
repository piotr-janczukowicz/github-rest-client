package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_DIGEST;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_GITHUB_REPOSITORY;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
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
 * Create artifact deployment record request body.
 *
 * @param name Name
 * @param digest Digest
 * @param version Version
 * @param status Status
 * @param logicalEnvironment Logical environment
 * @param physicalEnvironment Physical environment
 * @param cluster Cluster
 * @param deploymentName Deployment name
 * @param tags Tags
 * @param runtimeRisks Runtime risks
 * @param githubRepository GitHub repository
 * @param returnRecords Return records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/create-artifact-deployment-record/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateArtifactDeploymentRecordPayload(
        String name,
        String digest,
        @Nullable String version,
        GitHubArtifactDeploymentStatus status,
        String logicalEnvironment,
        @Nullable String physicalEnvironment,
        @Nullable String cluster,
        String deploymentName,
        @Nullable Map<String, String> tags,
        @Nullable List<GitHubArtifactRuntimeRisk> runtimeRisks,
        @Nullable String githubRepository,
        @Nullable Boolean returnRecords) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DIGEST = "digest";
    private static final String PARAM_LOGICAL_ENVIRONMENT = "logicalEnvironment";
    private static final String PARAM_DEPLOYMENT_NAME = "deploymentName";
    private static final String PARAM_GITHUB_REPOSITORY = "githubRepository";
    private static final String PARAM_RUNTIME_RISKS = "runtimeRisks";
    private static final String PARAM_VERSION = "version";

    public GitHubCreateArtifactDeploymentRecordPayload {
        name = requireMaxLength(requireMinLength(name, 1, PARAM_NAME), 256, PARAM_NAME);
        digest = requireMatches(digest, ARTIFACT_DIGEST, PARAM_DIGEST);
        requireNonNull(status, "status");
        logicalEnvironment = requireMaxLength(
                requireMinLength(logicalEnvironment, 1, PARAM_LOGICAL_ENVIRONMENT), 128, PARAM_LOGICAL_ENVIRONMENT);
        physicalEnvironment = requireMaxLengthIfPresent(physicalEnvironment, 128, "physicalEnvironment");
        cluster = requireMaxLengthIfPresent(cluster, 128, "cluster");
        deploymentName = requireMaxLength(deploymentName, 256, PARAM_DEPLOYMENT_NAME);
        version = requireMaxLengthIfPresent(requireMinLengthIfPresent(version, 1, PARAM_VERSION), 100, PARAM_VERSION);
        if (tags != null) {
            tags = Map.copyOf(requireSizeAtMost(tags, 5, "tags"));
        }
        if (runtimeRisks != null) {
            runtimeRisks = List.copyOf(
                    requireUniqueItems(requireSizeAtMost(runtimeRisks, 4, PARAM_RUNTIME_RISKS), PARAM_RUNTIME_RISKS));
        }
        githubRepository = requireMaxLengthIfPresent(
                requireMinLengthIfPresent(githubRepository, 1, PARAM_GITHUB_REPOSITORY), 100, PARAM_GITHUB_REPOSITORY);
        if (githubRepository != null) {
            requireMatches(githubRepository, ARTIFACT_GITHUB_REPOSITORY, PARAM_GITHUB_REPOSITORY);
        }
    }
}
