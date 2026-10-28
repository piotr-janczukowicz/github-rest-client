package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Repository actions caches.
 *
 * @param totalCount Total number of caches
 * @param actionsCaches Array of caches
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/actions-cache-list")
public record GitHubActionsCacheList(int totalCount, List<GitHubActionsCache> actionsCaches) {

    public GitHubActionsCacheList {
        requireNonNull(actionsCaches, "actionsCaches");
    }
}
