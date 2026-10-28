package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update Dependabot's repository access list.
 *
 * @param repositoryIdsToAdd List of repository IDs to add.
 * @param repositoryIdsToRemove List of repository IDs to remove.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/dependabot/update-repository-access-for-org/requestBody",
    "operations/dependabot/update-repository-access-for-enterprise/requestBody"
})
public record GitHubUpdateRepositoryAccessPayload(
        @Nullable List<Long> repositoryIdsToAdd, @Nullable List<Long> repositoryIdsToRemove) {

    public GitHubUpdateRepositoryAccessPayload {
        if (repositoryIdsToAdd != null) {
            repositoryIdsToAdd = List.copyOf(repositoryIdsToAdd);
        }
        if (repositoryIdsToRemove != null) {
            repositoryIdsToRemove = List.copyOf(repositoryIdsToRemove);
        }
    }
}
