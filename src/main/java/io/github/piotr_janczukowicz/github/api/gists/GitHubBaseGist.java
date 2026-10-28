package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Base Gist.
 *
 * @param url Url
 * @param forksUrl Forks url
 * @param commitsUrl Commits url
 * @param id Id
 * @param nodeId Node id
 * @param gitPullUrl Git pull url
 * @param gitPushUrl Git push url
 * @param htmlUrl Html url
 * @param files Files by filename
 * @param isPublic Public
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param description Description
 * @param comments Comments
 * @param commentsEnabled Comments enabled
 * @param commentsUrl Comments url
 * @param owner Owner
 * @param truncated Truncated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/base-gist")
@RecordBuilder
public record GitHubBaseGist(
        String url,
        String forksUrl,
        String commitsUrl,
        String id,
        String nodeId,
        String gitPullUrl,
        String gitPushUrl,
        String htmlUrl,
        Map<String, GitHubBaseGistFile> files,
        @JsonProperty("public") boolean isPublic,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String description,
        int comments,
        @Nullable Boolean commentsEnabled,
        String commentsUrl,
        @Nullable GitHubSimpleUser owner,
        @Nullable Boolean truncated) {

    public GitHubBaseGist {
        requireNonNull(url, "url");
        requireNonNull(forksUrl, "forksUrl");
        requireNonNull(commitsUrl, "commitsUrl");
        requireNonNull(id, "id");
        requireNonNull(nodeId, "nodeId");
        requireNonNull(gitPullUrl, "gitPullUrl");
        requireNonNull(gitPushUrl, "gitPushUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(files, "files");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(commentsUrl, "commentsUrl");
    }
}
