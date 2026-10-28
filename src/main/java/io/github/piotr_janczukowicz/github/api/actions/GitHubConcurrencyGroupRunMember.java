package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A workflow run or job of a concurrency group lease.
 *
 * @param runId The ID of the workflow run
 * @param runName The name of the workflow run
 * @param runUrl API URL for the workflow run
 * @param runHtmlUrl Web URL for the workflow run
 * @param position Queue position, 0 means the item holds the concurrency lease
 * @param positionUrl API URL to get items ahead of this item in the concurrency group
 * @param jobId The ID of the job, when the item represents a job-level or reusable-workflow-level lease
 * @param jobName The display name of the job, when the item represents a job-level or reusable-workflow-level lease
 * @param jobUrl API URL for the job
 * @param jobHtmlUrl Web URL for the job
 * @param status The status of the item
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "components/schemas/concurrency-group-run-list/properties/concurrency_groups/items/properties/group_members/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroupRunMember(
        long runId,
        String runName,
        @Nullable String runUrl,
        @Nullable String runHtmlUrl,
        int position,
        String positionUrl,
        @Nullable Long jobId,
        @Nullable String jobName,
        @Nullable String jobUrl,
        @Nullable String jobHtmlUrl,
        GitHubConcurrencyGroupMemberStatus status) {

    public GitHubConcurrencyGroupRunMember {
        requireNonNull(runName, "runName");
        requireNonNull(positionUrl, "positionUrl");
        requireNonNull(status, "status");
    }
}
