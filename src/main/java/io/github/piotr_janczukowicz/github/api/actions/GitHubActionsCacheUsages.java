package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * GitHub Actions cache usage of the repositories of an organization.
 *
 * @param totalCount Total number of repositories with caches
 * @param repositoryCacheUsages Cache usage per repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/get-actions-cache-usage-by-repo-for-org/200")
public record GitHubActionsCacheUsages(
        int totalCount, List<GitHubActionsCacheUsageByRepository> repositoryCacheUsages) {

    public GitHubActionsCacheUsages {
        requireNonNull(repositoryCacheUsages, "repositoryCacheUsages");
    }
}
