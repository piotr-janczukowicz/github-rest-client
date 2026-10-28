package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A concurrency group associated with a workflow run.
 *
 * @param groupName The name of the concurrency group
 * @param groupUrl API URL for this concurrency group
 * @param groupMembers Items belonging to this workflow run that are holding or waiting for the lease
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/concurrency-group-run-list/properties/concurrency_groups/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroupRunListEntry(
        String groupName, String groupUrl, List<GitHubConcurrencyGroupRunMember> groupMembers) {

    public GitHubConcurrencyGroupRunListEntry {
        requireNonNull(groupName, "groupName");
        requireNonNull(groupUrl, "groupUrl");
        requireNonNull(groupMembers, "groupMembers");
    }
}
