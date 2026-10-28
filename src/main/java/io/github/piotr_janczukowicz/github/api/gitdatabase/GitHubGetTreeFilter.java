package io.github.piotr_janczukowicz.github.api.gitdatabase;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/git/trees/{tree_sha}}, excluding pagination.
 *
 * @param recursive Setting this parameter to any value returns the objects or subtrees referenced by the tree specified
 *     in {@code :tree_sha}. For example, setting {@code recursive} to any of the following will enable returning
 *     objects or subtrees: {@code 0}, {@code 1}, {@code "true"}, and {@code "false"}. Omit this parameter to prevent
 *     recursively returning objects or subtrees.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/git/get-tree/parameters")
@RecordBuilder
public record GitHubGetTreeFilter(@Nullable String recursive) {

    /**
     * Filter with all query parameters unset: the tree is not read recursively.
     *
     * @return empty filter
     */
    public static GitHubGetTreeFilter empty() {
        return GitHubGetTreeFilterBuilder.builder().build();
    }

    /**
     * Filter that returns the objects and subtrees below the tree. GitHub enables recursion for any value of
     * {@code recursive}, so {@code new GitHubGetTreeFilter("false")} and {@code ("0")} recurse too; to turn recursion
     * off, use {@link #empty()}.
     *
     * @return filter that sends {@code recursive=1}
     */
    public static GitHubGetTreeFilter recursively() {
        return GitHubGetTreeFilterBuilder.builder().recursive("1").build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (recursive == null) {
            return Map.of();
        }

        return Map.of("recursive", recursive);
    }
}
