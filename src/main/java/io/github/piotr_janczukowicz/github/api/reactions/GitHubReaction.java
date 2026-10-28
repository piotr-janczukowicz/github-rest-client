package io.github.piotr_janczukowicz.github.api.reactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Reactions to conversations provide a way to help people express their feelings more simply and effectively.
 *
 * @param id Id
 * @param nodeId Node id
 * @param user User
 * @param content The reaction to use
 * @param createdAt Created at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/reaction")
public record GitHubReaction(
        long id, String nodeId, @Nullable GitHubSimpleUser user, GitHubReactionContent content, Instant createdAt) {

    public GitHubReaction {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(content, "content");
        requireNonNull(createdAt, "createdAt");
    }
}
