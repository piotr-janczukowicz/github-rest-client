package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;

/**
 * Starred repository.
 *
 * @param starredAt The time the repository was starred
 * @param repo The starred repository
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/starred-repository")
@RecordBuilder
public record GitHubStarredRepository(Instant starredAt, GitHubRepository repo) {

    public GitHubStarredRepository {
        requireNonNull(starredAt, "starredAt");
        requireNonNull(repo, "repo");
    }
}
