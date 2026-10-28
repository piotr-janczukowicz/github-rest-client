package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeAtMost;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Set cluster deployment records request body.
 *
 * @param logicalEnvironment Logical environment
 * @param physicalEnvironment Physical environment
 * @param deployments Deployments
 * @param partialSuccess Partial success
 * @param returnRecords Return records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/set-cluster-deployment-records/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSetClusterDeploymentRecordsPayload(
        String logicalEnvironment,
        @Nullable String physicalEnvironment,
        List<GitHubClusterDeploymentRecordItem> deployments,
        @Nullable Boolean partialSuccess,
        @Nullable Boolean returnRecords) {

    private static final String PARAM_LOGICAL_ENVIRONMENT = "logicalEnvironment";
    private static final String PARAM_DEPLOYMENTS = "deployments";

    public GitHubSetClusterDeploymentRecordsPayload {
        logicalEnvironment = requireMaxLength(
                requireMinLength(logicalEnvironment, 1, PARAM_LOGICAL_ENVIRONMENT), 128, PARAM_LOGICAL_ENVIRONMENT);
        physicalEnvironment = requireMaxLengthIfPresent(physicalEnvironment, 128, "physicalEnvironment");
        deployments = List.copyOf(requireSizeAtMost(deployments, 1000, PARAM_DEPLOYMENTS));
    }
}
