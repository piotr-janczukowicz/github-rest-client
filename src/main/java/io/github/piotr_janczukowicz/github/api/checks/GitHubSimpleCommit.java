package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A commit.
 *
 * @param id SHA for the commit
 * @param treeId SHA for the commit's tree
 * @param message Message describing the purpose of the commit
 * @param timestamp Timestamp of the commit
 * @param author Information about the Git author
 * @param committer Information about the Git committer
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/simple-commit")
public record GitHubSimpleCommit(
        String id,
        String treeId,
        String message,
        Instant timestamp,
        @Nullable GitHubSimpleCommitPerson author,
        @Nullable GitHubSimpleCommitPerson committer) {

    public GitHubSimpleCommit {
        requireNonNull(id, "id");
        requireNonNull(treeId, "treeId");
        requireNonNull(message, "message");
        requireNonNull(timestamp, "timestamp");
    }
}
