package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * Self-hosted runner groups of an enterprise.
 *
 * @param totalCount Total number of runner groups
 * @param runnerGroups The runner groups
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/list-self-hosted-runner-groups-for-enterprise/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubRunnerGroupsForEnterprise(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubRunnerGroupsEnterprise> runnerGroups) {

    public GitHubRunnerGroupsForEnterprise {
        requireNonNull(runnerGroups, "runnerGroups");
    }
}
