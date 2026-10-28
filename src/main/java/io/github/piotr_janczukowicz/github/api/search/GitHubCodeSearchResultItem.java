package io.github.piotr_janczukowicz.github.api.search;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Code Search Result Item.
 *
 * @param name Name
 * @param path Path
 * @param sha Sha
 * @param url Url
 * @param gitUrl Git url
 * @param htmlUrl Html url
 * @param repository Repository
 * @param score Score
 * @param fileSize File size
 * @param language Language
 * @param lastModifiedAt Last modified at
 * @param lineNumbers Line numbers
 * @param textMatches Text matches
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-search-result-item")
public record GitHubCodeSearchResultItem(
        String name,
        String path,
        String sha,
        String url,
        String gitUrl,
        String htmlUrl,
        GitHubMinimalRepository repository,
        double score,
        @Nullable Integer fileSize,
        @Nullable String language,
        @Nullable Instant lastModifiedAt,
        @Nullable List<String> lineNumbers,
        @Nullable List<GitHubSearchTextMatchItem> textMatches) {

    public GitHubCodeSearchResultItem {
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(gitUrl, "gitUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(repository, "repository");
    }
}
