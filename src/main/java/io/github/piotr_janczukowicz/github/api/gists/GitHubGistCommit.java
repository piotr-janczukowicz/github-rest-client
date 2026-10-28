package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Gist Commit.
 *
 * @param url Url
 * @param version Version
 * @param user User
 * @param changeStatus Change status
 * @param committedAt Committed at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-commit")
@RecordBuilder
public record GitHubGistCommit(
        String url,
        String version,
        @Nullable GitHubSimpleUser user,
        GitHubGistCommitChangeStatus changeStatus,
        Instant committedAt) {

    public GitHubGistCommit {
        requireNonNull(url, "url");
        requireNonNull(version, "version");
        requireNonNull(changeStatus, "changeStatus");
        requireNonNull(committedAt, "committedAt");
    }
}
