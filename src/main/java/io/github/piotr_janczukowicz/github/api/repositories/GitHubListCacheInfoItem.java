package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Status of a cache replica.
 *
 * @param host host name of the replica
 * @param location location of the replica
 * @param git Git synchronization state
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/list-cache-info/200/items")
@Flavors(GitHubFlavor.GHES)
public record GitHubListCacheInfoItem(String host, String location, GitHubListCacheInfoItemGit git) {
    public GitHubListCacheInfoItem {
        requireNonNull(host, "host");
        requireNonNull(location, "location");
        requireNonNull(git, "git");
    }
}
