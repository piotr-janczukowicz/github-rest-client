package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Assignee in an update issue request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/update/requestBody/properties/assignees/items")
public record GitHubUpdateIssueAssignee(
        @Nullable String assigneeLogin, @Nullable Details assigneeDetails) {

    public GitHubUpdateIssueAssignee {
        final boolean hasLogin = assigneeLogin != null;
        final boolean hasDetails = assigneeDetails != null;

        if (hasLogin == hasDetails) {
            throw new IllegalArgumentException("exactly one of assigneeLogin or assigneeDetails is required");
        }
    }

    /**
     * Assignee by login.
     *
     * @param login username
     * @return assignee reference
     */
    public static GitHubUpdateIssueAssignee ofLogin(final String login) {
        requireNonNull(login, "login");
        return new GitHubUpdateIssueAssignee(login, null);
    }

    /**
     * Assignee object with optional metadata.
     *
     * @param details assignee details
     * @return assignee reference
     */
    public static GitHubUpdateIssueAssignee ofDetails(final Details details) {
        return new GitHubUpdateIssueAssignee(null, requireNonNull(details, "details"));
    }

    /**
     * Assignee object with optional metadata.
     *
     * @param login username
     * @param rationale optional reasoning for selecting this assignee
     * @param suggest store as pending suggestion when {@code true}
     * @param confidence confidence level for this assignee choice
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/update/requestBody/properties/assignees/items/oneOf/1")
    public record Details(
            @Nullable String login,
            @Nullable String rationale,
            @Nullable Boolean suggest,
            @Nullable GitHubIssueUpdateConfidence confidence) {}
}
