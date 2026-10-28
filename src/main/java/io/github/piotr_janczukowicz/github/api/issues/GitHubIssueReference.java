package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Issue Reference.
 *
 * @param number The number of the referenced issue.
 * @param title The title of the referenced issue.
 * @param state The state of the referenced issue.
 * @param stateReason The reason for the referenced issue's state.
 * @param repository Repository
 * @param issueType The type of the referenced issue.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue-reference")
@RecordBuilder
public record GitHubIssueReference(
        int number,
        String title,
        String state,
        @Nullable String stateReason,
        GitHubSimpleRepository repository,
        @Nullable IssueType issueType) {

    public GitHubIssueReference {
        requireNonNull(title, "title");
        requireNonNull(state, "state");
        requireNonNull(repository, "repository");
    }

    /**
     * The type of the referenced issue.
     *
     * @param id The unique identifier of the issue type.
     * @param nodeId The node identifier of the issue type.
     * @param name The name of the issue type.
     * @param color The color of the issue type.
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @RecordBuilder
    @CatalogSchema("components/schemas/issue-reference/properties/issue_type")
    public record IssueType(
            long id, String nodeId, String name, @Nullable String color) {

        public IssueType {
            requireNonNull(nodeId, "nodeId");
            requireNonNull(name, "name");
        }
    }
}
