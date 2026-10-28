package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Created artifact deployment records.
 *
 * @param totalCount The number of deployment records created
 * @param deploymentRecords Deployment records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/orgs/create-artifact-deployment-record/200",
    "operations/orgs/set-cluster-deployment-records/200"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactCreatedDeploymentRecords(
        int totalCount, @Nullable List<GitHubArtifactDeploymentRecord> deploymentRecords) {}
