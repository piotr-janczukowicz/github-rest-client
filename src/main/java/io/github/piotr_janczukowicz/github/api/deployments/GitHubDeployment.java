package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A request for a specific ref(branch,sha,tag) to be deployed.
 *
 * @param url Url
 * @param id Unique identifier of the deployment
 * @param nodeId Node id
 * @param sha Sha
 * @param ref The ref to deploy. This can be a branch, tag, or sha.
 * @param task Parameter to specify a task to execute
 * @param payload Payload
 * @param originalEnvironment Original environment
 * @param environment Name for the target deployment environment.
 * @param description Description
 * @param creator Creator
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param statusesUrl Statuses url
 * @param repositoryUrl Repository url
 * @param transientEnvironment Specifies if the given environment is will no longer exist at some point in the future.
 *     Default: false.
 * @param productionEnvironment Specifies if the given environment is one that end-users directly interact with.
 *     Default: false.
 * @param performedViaGithubApp Performed via github app
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/deployment")
@RecordBuilder
public record GitHubDeployment(
        String url,
        long id,
        String nodeId,
        String sha,
        String ref,
        String task,
        GitHubDeploymentPayloadValue payload,
        @Nullable String originalEnvironment,
        String environment,
        @Nullable String description,
        @Nullable GitHubSimpleUser creator,
        Instant createdAt,
        Instant updatedAt,
        String statusesUrl,
        String repositoryUrl,
        @Nullable Boolean transientEnvironment,
        @Nullable Boolean productionEnvironment,
        @Nullable GitHubIntegration performedViaGithubApp) {

    public GitHubDeployment {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(sha, "sha");
        requireNonNull(ref, "ref");
        requireNonNull(task, "task");
        requireNonNull(payload, "payload");
        requireNonNull(environment, "environment");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(repositoryUrl, "repositoryUrl");
    }
}
