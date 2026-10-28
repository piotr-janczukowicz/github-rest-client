package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.github.pagination.GitHubIssueSearchType;
import io.github.piotr_janczukowicz.github.pagination.GitHubLexicalFallbackReason;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Jackson DTO for one GitHub Search API response page.
 *
 * @param totalCount total matches reported by GitHub
 * @param incompleteResults whether GitHub considers the result set incomplete
 * @param items decoded items for this page
 * @param searchType issues-only {@code search_type} field, otherwise {@code null}
 * @param lexicalFallbackReasons issues-only fallback reasons, or {@code null}
 * @param <T> item type
 */
record GitHubSearchResponseBody<T>(
        int totalCount,
        boolean incompleteResults,
        List<T> items,
        @Nullable GitHubIssueSearchType searchType,
        @Nullable @JsonProperty("lexical_fallback_reason") List<GitHubLexicalFallbackReason> lexicalFallbackReasons) {
    GitHubSearchResponseBody {
        requireNonNull(items, "items");
    }
}
