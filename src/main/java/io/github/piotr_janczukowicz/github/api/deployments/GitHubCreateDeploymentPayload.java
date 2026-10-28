package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create a deployment.
 *
 * @param ref The ref to deploy. This can be a branch, tag, or SHA.
 * @param task Specifies a task to execute (e.g., {@code deploy} or {@code deploy:migrations}).
 * @param autoMerge Attempts to automatically merge the default branch into the requested ref, if it's behind the
 *     default branch.
 * @param requiredContexts The status contexts to verify against commit status checks. If you omit this parameter,
 *     GitHub verifies all unique contexts before creating a deployment. To bypass checking entirely, pass an empty
 *     array. Defaults to all unique contexts.
 * @param payload Payload
 * @param environment Name for the target deployment environment (e.g., {@code production}, {@code staging},
 *     {@code qa}).
 * @param description Short description of the deployment.
 * @param transientEnvironment Specifies if the given environment is specific to the deployment and will no longer exist
 *     at some point in the future. Default: {@code false}
 * @param productionEnvironment Specifies if the given environment is one that end-users directly interact with.
 *     Default: {@code true} when {@code environment} is {@code production} and {@code false} otherwise.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-deployment/requestBody")
public record GitHubCreateDeploymentPayload(
        String ref,
        @Nullable String task,
        @Nullable Boolean autoMerge,
        @Nullable List<String> requiredContexts,
        @Nullable GitHubDeploymentPayloadValue payload,
        @Nullable String environment,
        @Nullable String description,
        @Nullable Boolean transientEnvironment,
        @Nullable Boolean productionEnvironment) {

    public GitHubCreateDeploymentPayload {
        requireNonNull(ref, "ref");
        if (requiredContexts != null) {
            requiredContexts = List.copyOf(requiredContexts);
        }
    }
}
