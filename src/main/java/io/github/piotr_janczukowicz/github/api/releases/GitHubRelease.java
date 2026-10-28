package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Release.
 *
 * @param url Url
 * @param htmlUrl Html url
 * @param assetsUrl Assets url
 * @param uploadUrl Upload url
 * @param tarballUrl Tarball url
 * @param zipballUrl Zipball url
 * @param id Id
 * @param nodeId Node id
 * @param tagName Tag name
 * @param targetCommitish Target commitish
 * @param name Name
 * @param body Body
 * @param draft Draft
 * @param prerelease Prerelease
 * @param immutable Immutable
 * @param createdAt Created at
 * @param publishedAt Published at
 * @param updatedAt Updated at
 * @param author Author
 * @param assets Assets
 * @param bodyHtml Body html
 * @param bodyText Body text
 * @param mentionsCount Mentions count
 * @param discussionUrl Discussion url
 * @param reactions Reactions
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/release")
public record GitHubRelease(
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
        @Nullable GitHubReactionRollup reactions) {

    public GitHubRelease {
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
