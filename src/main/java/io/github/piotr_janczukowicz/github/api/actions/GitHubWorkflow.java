package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub Actions workflow.
 *
 * @param id The ID of the workflow
 * @param nodeId The node ID of the workflow
 * @param name The name of the workflow
 * @param path The path of the workflow file
 * @param state The state of the workflow
 * @param createdAt The creation time of the workflow
 * @param updatedAt The last update time of the workflow
 * @param url The API URL of the workflow
 * @param htmlUrl The web URL of the workflow
 * @param badgeUrl The badge URL of the workflow
 * @param deletedAt The deletion time of the workflow
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/workflow")
public record GitHubWorkflow(
        long id,
        String nodeId,
        String name,
        String path,
        GitHubWorkflowState state,
        Instant createdAt,
        Instant updatedAt,
        String url,
        String htmlUrl,
        String badgeUrl,
        @Nullable Instant deletedAt) {

    public GitHubWorkflow {
        requireNonNull(nodeId, "nodeId");
        requireNonNull(name, "name");
        requireNonNull(path, "path");
        requireNonNull(state, "state");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(badgeUrl, "badgeUrl");
    }
}
