package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Built-in deployment protection rule on an environment.
 *
 * @param id Id
 * @param nodeId Node id
 * @param type Type
 * @param waitTimer The amount of time to delay a job after the job is initially triggered. The time (in minutes) must
 *     be an integer between 0 and 43,200 (30 days).
 * @param preventSelfReview Whether deployments to this environment can be approved by the user who created the
 *     deployment.
 * @param reviewers The people or teams that may approve jobs that reference the environment. You can list up to six
 *     users or teams as reviewers. The reviewers must have at least read access to the repository. Only one of the
 *     required reviewers needs to approve the job for it to proceed.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/environment/properties/protection_rules/items")
@RecordBuilder
public record GitHubEnvironmentProtectionRule(
        long id,
        String nodeId,
        String type,
        @Nullable Integer waitTimer,
        @Nullable Boolean preventSelfReview,
        @Nullable List<GitHubEnvironmentReviewerEntry> reviewers) {

    public GitHubEnvironmentProtectionRule {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(type, "type");
    }
}
