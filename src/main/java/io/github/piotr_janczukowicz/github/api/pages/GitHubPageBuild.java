package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Page Build
 *
 * @param url Url
 * @param status Status
 * @param error Error
 * @param pusher Pusher
 * @param commit Commit
 * @param duration Duration
 * @param createdAt Created at
 * @param updatedAt Updated at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/page-build")
public record GitHubPageBuild(
        String url,
        String status,
        GitHubPageBuildError error,
        @Nullable GitHubSimpleUser pusher,
        String commit,
        long duration,
        Instant createdAt,
        Instant updatedAt) {

    public GitHubPageBuild {
        requireNonNull(url, "url");
        requireNonNull(status, "status");
        requireNonNull(error, "error");
        requireNonNull(commit, "commit");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
    }
}
