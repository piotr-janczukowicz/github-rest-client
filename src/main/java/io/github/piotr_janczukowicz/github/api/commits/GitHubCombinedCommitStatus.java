package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Combined commit status.
 *
 * @param state State
 * @param statuses Statuses
 * @param sha Sha
 * @param totalCount Total count
 * @param repository Repository
 * @param commitUrl Commit url
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/combined-commit-status")
@RecordBuilder
public record GitHubCombinedCommitStatus(
        String state,
        List<GitHubSimpleCommitStatus> statuses,
        String sha,
        int totalCount,
        GitHubMinimalRepository repository,
        String commitUrl,
        String url) {

    public GitHubCombinedCommitStatus {
        requireNonNull(state, "state");
        requireNonNull(statuses, "statuses");
        requireNonNull(sha, "sha");
        requireNonNull(repository, "repository");
        requireNonNull(commitUrl, "commitUrl");
        requireNonNull(url, "url");
    }
}
