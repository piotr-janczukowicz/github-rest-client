package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * An active concurrency group of a repository.
 *
 * @param groupName The name of the concurrency group
 * @param groupUrl API URL for this concurrency group
 * @param lastAcquiredAt The time the concurrency group was last acquired
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/concurrency-group-list/properties/concurrency_groups/items")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubConcurrencyGroupListEntry(
        String groupName, String groupUrl, @Nullable Instant lastAcquiredAt) {

    public GitHubConcurrencyGroupListEntry {
        requireNonNull(groupName, "groupName");
        requireNonNull(groupUrl, "groupUrl");
    }
}
