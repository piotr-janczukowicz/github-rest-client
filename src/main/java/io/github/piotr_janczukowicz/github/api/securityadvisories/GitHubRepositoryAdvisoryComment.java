package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A comment on a repository security advisory.
 *
 * @param id Unique identifier of the advisory comment.
 * @param nodeId node id
 * @param url API URL for the advisory comment.
 * @param htmlUrl Web URL for the advisory comment.
 * @param body Contents of the advisory comment.
 * @param user user
 * @param createdAt created at
 * @param updatedAt updated at
 * @param advisoryUrl advisory url
 * @param authorAssociation How the author is associated with the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/repository-advisory-comment")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepositoryAdvisoryComment(
        long id,
        String nodeId,
        String url,
        String htmlUrl,
        String body,
        @Nullable GitHubSimpleUser user,
        Instant createdAt,
        Instant updatedAt,
        String advisoryUrl,
        GitHubAuthorAssociation authorAssociation) {
    public GitHubRepositoryAdvisoryComment {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(body, "body");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(advisoryUrl, "advisoryUrl");
        requireNonNull(authorAssociation, "authorAssociation");
    }
}
