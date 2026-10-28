package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A comment made to a gist.
 *
 * @param id Id
 * @param nodeId Node id
 * @param url Url
 * @param body The comment text.
 * @param user User
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param authorAssociation How the author is associated with the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gist-comment")
@RecordBuilder
public record GitHubGistComment(
        long id,
        String nodeId,
        String url,
        String body,
        @Nullable GitHubSimpleUser user,
        Instant createdAt,
        Instant updatedAt,
        GitHubAuthorAssociation authorAssociation) {

    public GitHubGistComment {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(url, "url");
        body = requireMaxLength(requireNonNull(body, "body"), 65_535, "body");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(authorAssociation, "authorAssociation");
    }
}
