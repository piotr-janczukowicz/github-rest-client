package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A deployment created as the result of an Actions check run from a workflow that references an environment.
 *
 * @param url URL
 * @param id Unique identifier of the deployment
 * @param nodeId Node id
 * @param task Parameter to specify a task to execute
 * @param originalEnvironment Original environment
 * @param environment Name for the target deployment environment.
 * @param description Description
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param statusesUrl Statuses URL
 * @param repositoryUrl Repository URL
 * @param transientEnvironment Specifies if the given environment is will no longer exist at some point in the future.
 * @param productionEnvironment Specifies if the given environment is one that end-users directly interact with.
 * @param performedViaGithubApp Performed via GitHub app
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/deployment-simple")
public record GitHubDeploymentSimple(
        String url,
        long id,
        String nodeId,
        String task,
        @Nullable String originalEnvironment,
        String environment,
        @Nullable String description,
        Instant createdAt,
        Instant updatedAt,
        String statusesUrl,
        String repositoryUrl,
        @Nullable Boolean transientEnvironment,
        @Nullable Boolean productionEnvironment,
        @Nullable GitHubIntegration performedViaGithubApp) {

    public GitHubDeploymentSimple {
        requireNonNull(url, "url");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(task, "task");
        requireNonNull(environment, "environment");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(statusesUrl, "statusesUrl");
        requireNonNull(repositoryUrl, "repositoryUrl");
    }
}
