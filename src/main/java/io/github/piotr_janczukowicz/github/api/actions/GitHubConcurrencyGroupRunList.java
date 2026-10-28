package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A list of concurrency groups associated with a workflow run.
 *
 * @param totalCount The total number of concurrency groups this workflow run participates in
 * @param concurrencyGroups The concurrency groups of the workflow run
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/concurrency-group-run-list")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroupRunList(
        int totalCount, List<GitHubConcurrencyGroupRunListEntry> concurrencyGroups) {

    public GitHubConcurrencyGroupRunList {
        requireNonNull(concurrencyGroups, "concurrencyGroups");
    }
}
