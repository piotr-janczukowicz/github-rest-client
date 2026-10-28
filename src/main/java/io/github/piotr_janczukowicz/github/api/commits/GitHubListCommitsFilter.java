package io.github.piotr_janczukowicz.github.api.commits;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/commits}, excluding pagination.
 *
 * @param sha Sha or branch to start listing commits from
 * @param path Only commits containing this file path
 * @param author GitHub username or email address to filter by commit author
 * @param committer GitHub username or email address to filter by commit committer
 * @param since Only show results updated after this time
 * @param until Only commits before this date
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/repos/list-commits/parameters")
@RecordBuilder
public record GitHubListCommitsFilter(
        @Nullable String sha,
        @Nullable String path,
        @Nullable String author,
        @Nullable String committer,
        @Nullable Instant since,
        @Nullable Instant until) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListCommitsFilter empty() {
        return GitHubListCommitsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (sha != null) {
            query.put("sha", sha);
        }
        if (path != null) {
            query.put("path", path);
        }
        if (author != null) {
            query.put("author", author);
        }
        if (committer != null) {
            query.put("committer", committer);
        }
        if (since != null) {
            query.put("since", since.toString());
        }
        if (until != null) {
            query.put("until", until.toString());
        }

        return Collections.unmodifiableMap(query);
    }
}
