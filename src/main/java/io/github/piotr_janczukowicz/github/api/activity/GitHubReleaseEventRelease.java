package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.releases.GitHubReleaseAsset;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/** Release with the additional short description fields. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/release-event/properties/release")
@RecordBuilder
public record GitHubReleaseEventRelease(
        String url,
        String htmlUrl,
        String assetsUrl,
        String uploadUrl,
        @Nullable String tarballUrl,
        @Nullable String zipballUrl,
        long id,
        String nodeId,
        String tagName,
        String targetCommitish,
        @Nullable String name,
        @Nullable String body,
        boolean draft,
        boolean prerelease,
        @Nullable Boolean immutable,
        Instant createdAt,
        @Nullable Instant publishedAt,
        @Nullable Instant updatedAt,
        GitHubSimpleUser author,
        List<GitHubReleaseAsset> assets,
        @Nullable String bodyHtml,
        @Nullable String bodyText,
        @Nullable Integer mentionsCount,
        @Nullable String discussionUrl,
        @Nullable GitHubReactionRollup reactions,
        @Nullable Boolean isShortDescriptionHtmlTruncated,
        @Nullable String shortDescriptionHtml) {
    public GitHubReleaseEventRelease {
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(assetsUrl, "assetsUrl");
        requireNonNull(uploadUrl, "uploadUrl");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(tagName, "tagName");
        requireNonNull(targetCommitish, "targetCommitish");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(author, "author");
        requireNonNull(assets, "assets");
    }
}
