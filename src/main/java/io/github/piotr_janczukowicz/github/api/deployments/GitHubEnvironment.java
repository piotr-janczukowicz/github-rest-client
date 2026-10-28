package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Details of a deployment environment.
 *
 * @param id The id of the environment.
 * @param nodeId Node id
 * @param name The name of the environment.
 * @param url Url
 * @param htmlUrl Html url
 * @param createdAt The time that the environment was created, in ISO 8601 format.
 * @param updatedAt The time that the environment was last updated, in ISO 8601 format.
 * @param protectionRules Built-in deployment protection rules for the environment.
 * @param deploymentBranchPolicy Deployment branch policy
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/environment")
@RecordBuilder
public record GitHubEnvironment(
        long id,
        String nodeId,
        String name,
        String url,
        String htmlUrl,
        Instant createdAt,
        Instant updatedAt,
        @Nullable List<GitHubEnvironmentProtectionRule> protectionRules,
        @Nullable GitHubDeploymentBranchPolicySettings deploymentBranchPolicy) {

    public GitHubEnvironment {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
