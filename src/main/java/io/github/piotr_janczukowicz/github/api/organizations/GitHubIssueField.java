package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A custom attribute defined at the organization level for attaching structured data to issues.
 *
 * @param id The unique identifier of the issue field
 * @param nodeId The node identifier of the issue field
 * @param name The name of the issue field
 * @param description The description of the issue field
 * @param dataType The data type of the issue field
 * @param visibility The visibility of the issue field
 * @param options Available options for single select and multi select fields
 * @param createdAt The time the issue field was created
 * @param updatedAt The time the issue field was last updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/issue-field")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubIssueField(
        long id,
        String nodeId,
        String name,
        @Nullable String description,
        GitHubIssueFieldDataType dataType,
        @Nullable GitHubIssueFieldVisibility visibility,
        @Nullable List<GitHubIssueFieldOption> options,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {

    private static final String PARAM_NODE_ID = "nodeId";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_DATA_TYPE = "dataType";

    public GitHubIssueField {
        requireNonNull(nodeId, PARAM_NODE_ID);
        requireNonNull(name, PARAM_NAME);
        requireNonNull(dataType, PARAM_DATA_TYPE);
    }
}
