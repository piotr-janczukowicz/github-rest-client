package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A list of active concurrency groups for a repository.
 *
 * @param totalCount Total number of concurrency groups
 * @param concurrencyGroups The active concurrency groups
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/concurrency-group-list")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroupList(int totalCount, List<GitHubConcurrencyGroupListEntry> concurrencyGroups) {

    public GitHubConcurrencyGroupList {
        requireNonNull(concurrencyGroups, "concurrencyGroups");
    }
}
