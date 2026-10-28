package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Result of creating a dependency snapshot.
 *
 * @param id ID of the created snapshot.
 * @param createdAt The time at which the snapshot was created.
 * @param result Either "SUCCESS", "ACCEPTED", or "INVALID". "SUCCESS" indicates that the snapshot was successfully
 *     created and the repository's dependencies were updated. "ACCEPTED" indicates that the snapshot was successfully
 *     created, but the repository's dependencies were not updated. "INVALID" indicates that the snapshot was malformed.
 * @param message A message providing further details about the result, such as why the dependencies were not updated.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/dependency-graph/create-repository-snapshot/201")
@RecordBuilder
public record GitHubCreateRepositorySnapshotResponse(long id, String createdAt, String result, String message) {

    public GitHubCreateRepositorySnapshotResponse {
        requireNonNull(createdAt, "createdAt");
        requireNonNull(result, "result");
        requireNonNull(message, "message");
    }
}
