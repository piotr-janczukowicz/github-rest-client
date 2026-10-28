package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Commit Search Result Item.
 *
 * @param url Url
 * @param sha Sha
 * @param htmlUrl Html url
 * @param commentsUrl Comments url
 * @param commit Commit
 * @param author Author
 * @param committer Committer
 * @param parents Parents
 * @param repository Repository
 * @param score Score
 * @param nodeId Node id
 * @param textMatches Text matches
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/commit-search-result-item")
public record GitHubCommitSearchResultItem(
        String url,
        String sha,
        String htmlUrl,
        String commentsUrl,
        Commit commit,
        @Nullable GitHubSimpleUser author,
        @Nullable GitHubGitUser committer,
        List<Parent> parents,
        GitHubMinimalRepository repository,
        double score,
        String nodeId,
        @Nullable List<GitHubSearchTextMatchItem> textMatches) {

    public GitHubCommitSearchResultItem {
        requireNonNull(url, "url");
        requireNonNull(sha, "sha");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(commentsUrl, "commentsUrl");
        requireNonNull(commit, "commit");
        requireNonNull(parents, "parents");
        requireNonNull(repository, "repository");
        requireNonNull(nodeId, "nodeId");
    }

    /**
     * Commit payload returned with commit search results.
     *
     * @param author Author
     * @param committer Committer
     * @param commentCount Comment count
     * @param message Message
     * @param tree Tree
     * @param url Url
     * @param verification Verification
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/commit-search-result-item/properties/commit")
    public record Commit(
            CommitAuthor author,
            @Nullable GitHubGitUser committer,
            int commentCount,
            String message,
            CommitTree tree,
            String url,
            @Nullable GitHubVerification verification) {
        public Commit {
            requireNonNull(author, "author");
            requireNonNull(message, "message");
            requireNonNull(tree, "tree");
            requireNonNull(url, "url");
        }
    }

    /**
     * Git author metadata on a commit search result.
     *
     * @param name Name
     * @param email Email
     * @param date Date
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/commit-search-result-item/properties/commit/properties/author")
    public record CommitAuthor(String name, String email, Instant date) {
        public CommitAuthor {
            requireNonNull(name, "name");
            requireNonNull(email, "email");
            requireNonNull(date, "date");
        }
    }

    /**
     * Git tree metadata on a commit search result.
     *
     * @param sha Sha
     * @param url Url
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/commit-search-result-item/properties/commit/properties/tree")
    public record CommitTree(String sha, String url) {
        public CommitTree {
            requireNonNull(sha, "sha");
            requireNonNull(url, "url");
        }
    }

    /**
     * Parent commit reference on a commit search result.
     *
     * @param url Url
     * @param htmlUrl Html url
     * @param sha Sha
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/commit-search-result-item/properties/parents/items")
    public record Parent(
            @Nullable String url,
            @Nullable String htmlUrl,
            @Nullable String sha) {}
}
