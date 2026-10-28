package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * The status of a deployment.
 *
 * @param url Url
 * @param id Id
 * @param nodeId Node id
 * @param state The state of the status.
 * @param creator Creator
 * @param description A short description of the status.
 * @param environment The environment of the deployment that the status is for.
 * @param targetUrl Closing down notice: the URL to associate with this status.
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param deploymentUrl Deployment url
 * @param repositoryUrl Repository url
 * @param environmentUrl The URL for accessing your environment.
 * @param logUrl The URL to associate with this status.
 * @param performedViaGithubApp Performed via github app
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deployment-status")
@RecordBuilder
public record GitHubDeploymentStatus(
        String url,
        long id,
        String nodeId,
        GitHubDeploymentStatusState state,
        @Nullable GitHubSimpleUser creator,
        String description,
        @Nullable String environment,
        String targetUrl,
        Instant createdAt,
        Instant updatedAt,
        String deploymentUrl,
        String repositoryUrl,
        @Nullable String environmentUrl,
        @Nullable String logUrl,
        @Nullable GitHubIntegration performedViaGithubApp) {

    public GitHubDeploymentStatus {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(state, "state");
        description = requireMaxLength(requireNonNull(description, "description"), 140, "description");
        requireNonNull(targetUrl, "targetUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(deploymentUrl, "deploymentUrl");
        requireNonNull(repositoryUrl, "repositoryUrl");
    }
}
