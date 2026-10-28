package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.List;

/**
 * Repositories with access to a self-hosted runner group.
 *
 * @param totalCount Total number of repositories
 * @param repositories The repositories
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/actions/list-repo-access-to-self-hosted-runner-group-in-org/200")
public record GitHubRunnerGroupRepositories(
        @SuppressSchemaTypeMismatch("dump declares number; total_count is an integer count")
        int totalCount,

        List<GitHubMinimalRepository> repositories) {

    public GitHubRunnerGroupRepositories {
        requireNonNull(repositories, "repositories");
    }
}
