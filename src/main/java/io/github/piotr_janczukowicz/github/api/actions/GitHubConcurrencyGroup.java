package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A concurrency group with the workflow runs and jobs that are either currently holding or waiting for the lease.
 *
 * @param groupName The name of the concurrency group
 * @param groupUrl API URL for this concurrency group
 * @param totalCount Total number of items in the concurrency group
 * @param groupMembers Items holding or waiting for the lease
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/concurrency-group")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroup(
        String groupName, String groupUrl, int totalCount, List<GitHubConcurrencyGroupMember> groupMembers) {

    public GitHubConcurrencyGroup {
        requireNonNull(groupName, "groupName");
        requireNonNull(groupUrl, "groupUrl");
        requireNonNull(groupMembers, "groupMembers");
    }
}
