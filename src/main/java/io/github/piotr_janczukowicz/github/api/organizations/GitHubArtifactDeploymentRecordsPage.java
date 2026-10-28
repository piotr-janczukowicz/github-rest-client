package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Artifact deployment records page.
 *
 * @param totalCount Total count
 * @param deploymentRecords Deployment records
 * @param errors Errors for deployments that could not be processed
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-cluster-deployment-records/207")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactDeploymentRecordsPage(
        int totalCount,
        @Nullable List<GitHubArtifactDeploymentRecord> deploymentRecords,
        @Nullable List<GitHubArtifactDeploymentError> errors) {}
