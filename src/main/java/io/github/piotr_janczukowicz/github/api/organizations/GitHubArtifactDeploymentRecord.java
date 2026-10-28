package io.github.piotr_janczukowicz.github.api.organizations;

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
 * Artifact metadata deployment record.
 *
 * @param id Id
 * @param digest Digest
 * @param logicalEnvironment Logical environment
 * @param physicalEnvironment Physical environment
 * @param cluster Cluster
 * @param deploymentName Deployment name
 * @param tags Tags
 * @param runtimeRisks Runtime risks
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param attestationId Attestation id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/artifact-deployment-record")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactDeploymentRecord(
        @Nullable Long id,
        @Nullable String digest,
        @Nullable String logicalEnvironment,
        @Nullable String physicalEnvironment,
        @Nullable String cluster,
        @Nullable String deploymentName,
        @Nullable Map<String, String> tags,
        @Nullable List<GitHubArtifactRuntimeRisk> runtimeRisks,
        @Nullable String createdAt,
        @Nullable String updatedAt,
        @Nullable Long attestationId) {

    private static final String PARAM_RUNTIME_RISKS = "runtimeRisks";

    public GitHubArtifactDeploymentRecord {
        if (runtimeRisks != null) {
            requireUniqueItems(requireSizeAtMost(runtimeRisks, 4, PARAM_RUNTIME_RISKS), PARAM_RUNTIME_RISKS);
        }
    }
}
