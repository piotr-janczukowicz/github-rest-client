package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a deployment status.
 *
 * @param state The state of the status. When you set a transient deployment to {@code inactive}, the deployment will be
 *     shown as {@code destroyed} in GitHub.
 * @param targetUrl The target URL to associate with this status. This URL should contain output to keep the user
 *     updated while the task is running or serve as historical information for what happened in the deployment.
 * @param logUrl The full URL of the deployment's output. This parameter replaces {@code target_url}. We will continue
 *     to accept {@code target_url} to support legacy uses, but we recommend replacing {@code target_url} with
 *     {@code log_url}. Setting {@code log_url} will automatically set {@code target_url} to the same value. Default:
 *     {@code ""}
 * @param description A short description of the status. The maximum description length is 140 characters.
 * @param environment Name for the target deployment environment, which can be changed when setting a deploy status. For
 *     example, {@code production}, {@code staging}, or {@code qa}. If not defined, the environment of the previous
 *     status on the deployment will be used, if it exists. Otherwise, the environment of the deployment will be used.
 * @param environmentUrl Sets the URL for accessing your environment. Default: {@code ""}
 * @param autoInactive Adds a new {@code inactive} status to all prior non-transient, non-production environment
 *     deployments with the same repository and {@code environment} name as the created status's deployment. An
 *     {@code inactive} status is only added to deployments that had a {@code success} state. Default: {@code true}
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-deployment-status/requestBody")
public record GitHubCreateDeploymentStatusPayload(
        GitHubDeploymentStatusState state,
        @Nullable String targetUrl,
        @Nullable String logUrl,
        @Nullable String description,
        @Nullable String environment,
        @Nullable String environmentUrl,
        @Nullable Boolean autoInactive) {

    public GitHubCreateDeploymentStatusPayload {
        requireNonNull(state, "state");
    }
}
