package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubGitUser;
import io.github.piotr_janczukowicz.github.api.search.GitHubVerification;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Commit detail.
 *
 * @param url Url
 * @param author Author
 * @param committer Committer
 * @param message Message
 * @param commentCount Comment count
 * @param tree Tree
 * @param verification Verification
 */
@CatalogSchema("components/schemas/commit/properties/commit")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubCommitDetail(
        String url,
        @Nullable OptionalNull<GitHubGitUser> author,
        @Nullable OptionalNull<GitHubGitUser> committer,
        String message,
        int commentCount,
        GitHubCommitTree tree,
        @Nullable GitHubVerification verification) {

    public GitHubCommitDetail {
        requireNonNull(url, "url");
        requireNonNull(message, "message");
        requireNonNull(tree, "tree");
    }
}
