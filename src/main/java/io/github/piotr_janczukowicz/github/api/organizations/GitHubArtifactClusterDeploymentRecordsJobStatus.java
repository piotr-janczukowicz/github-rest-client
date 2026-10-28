package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Cluster deployment records background job status.
 *
 * @param jobId Job id
 * @param status Status
 * @param startedAt Started at
 * @param totalCount Total count
 * @param errors Processing errors
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/get-cluster-deployment-records-job/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubArtifactClusterDeploymentRecordsJobStatus(
        long jobId,
        GitHubArtifactClusterJobStatus status,
        @Nullable Instant startedAt,
        @Nullable Integer totalCount,

        @SuppressSchemaTypeMismatch("dump items are empty object; java is Map") @Nullable List<Map<String, Object>> errors) {

    public GitHubArtifactClusterDeploymentRecordsJobStatus {
        requireNonNull(status, "status");
    }
}
