package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Git synchronization state of a cache replica.
 *
 * @param syncStatus synchronization status
 * @param lastSync time of the last synchronization
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/list-cache-info/200/items/properties/git")
@Flavors(GitHubFlavor.GHES)
public record GitHubListCacheInfoItemGit(GitHubListCacheInfoItemGitSyncStatus syncStatus, Instant lastSync) {
    public GitHubListCacheInfoItemGit {
        requireNonNull(syncStatus, "syncStatus");
        requireNonNull(lastSync, "lastSync");
    }
}
