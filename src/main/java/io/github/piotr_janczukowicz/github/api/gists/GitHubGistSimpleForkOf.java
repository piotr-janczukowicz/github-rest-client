package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * The gist a simple gist was forked from.
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
 * @param user User
 * @param commentsUrl Comments url
 * @param owner Owner
 * @param truncated Truncated
 * @param forks Forks
 * @param history History
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-simple/properties/fork_of")
@RecordBuilder
public record GitHubGistSimpleForkOf(
        String url,
        String forksUrl,
        String commitsUrl,
        String id,
        String nodeId,
        String gitPullUrl,
        String gitPushUrl,
        String htmlUrl,
        Map<String, GitHubGistSimpleForkOfFile> files,
        @JsonProperty("public") boolean isPublic,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String description,
        int comments,
        @Nullable Boolean commentsEnabled,
        @Nullable GitHubSimpleUser user,
        String commentsUrl,
        @Nullable GitHubSimpleUser owner,
        @Nullable Boolean truncated,

        @SuppressSchemaTypeMismatch("dump items are empty object; java is Map") @Nullable List<Map<String, Object>> forks,

        @SuppressSchemaTypeMismatch("dump items are empty object; java is Map") @Nullable List<Map<String, Object>> history) {

    public GitHubGistSimpleForkOf {
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
