package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Gist Simple.
 *
 * @param forkOf The gist this gist was forked from
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
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-simple")
@RecordBuilder
public record GitHubGistSimple(
        @Nullable GitHubGistSimpleForkOf forkOf,
        @Nullable String url,
        @Nullable String forksUrl,
        @Nullable String commitsUrl,
        @Nullable String id,
        @Nullable String nodeId,
        @Nullable String gitPullUrl,
        @Nullable String gitPushUrl,
        @Nullable String htmlUrl,
        @Nullable Map<String, @Nullable GitHubGistSimpleFile> files,
        @JsonProperty("public") @Nullable Boolean isPublic,
        @Nullable String createdAt,
        @Nullable String updatedAt,
        @Nullable String description,
        @Nullable Integer comments,
        @Nullable Boolean commentsEnabled,
        @Nullable String user,
        @Nullable String commentsUrl,
        @Nullable GitHubSimpleUser owner,
        @Nullable Boolean truncated) {}
