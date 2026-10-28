package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * Self-hosted runner groups of an organization.
 *
 * @param totalCount Total number of runner groups
 * @param runnerGroups The runner groups
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-self-hosted-runner-groups-for-org/200")
public record GitHubRunnerGroups(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubRunnerGroupsOrg> runnerGroups) {

    public GitHubRunnerGroups {
        requireNonNull(runnerGroups, "runnerGroups");
    }
}
