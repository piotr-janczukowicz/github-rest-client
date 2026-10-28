package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireExactlyOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Assignee in an add-assignees request ({@code oneOf} string vs object). */
@CatalogSchema("operations/issues/add-assignees/requestBody/properties/assignees/items")
public record GitHubAddAssigneesAssignee(
        @Nullable String assigneeLogin, @Nullable Details assigneeDetails) {

    public GitHubAddAssigneesAssignee {
        requireExactlyOne(assigneeLogin, assigneeDetails, "assigneeLogin or assigneeDetails");
    }

    /**
     * Assignee by login.
     *
     * @param login username
     * @return assignee reference
     */
    public static GitHubAddAssigneesAssignee ofLogin(final String login) {
        requireNonNull(login, "login");
        return new GitHubAddAssigneesAssignee(login, null);
    }

    /**
     * Assignee object with optional metadata.
     *
     * @param details assignee details
     * @return assignee reference
     */
    public static GitHubAddAssigneesAssignee ofDetails(final Details details) {
        return new GitHubAddAssigneesAssignee(null, requireNonNull(details, "details"));
    }

    /**
     * Assignee object with required login.
     *
     * @param login The login of the user to assign.
     * @param rationale Optional reasoning for adding this assignee.
     * @param suggest If {@code true}, the assignee is stored as a pending suggestion for human review rather than
     *     applied directly.
     * @param confidence The confidence level for this assignee choice.
     */
    @RecordBuilder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @CatalogSchema("operations/issues/add-assignees/requestBody/properties/assignees/items/oneOf/1")
    public record Details(
            String login,
            @Nullable String rationale,
            @Nullable Boolean suggest,
            @Nullable GitHubIssueUpdateConfidence confidence) {

        public Details {
            requireNonNull(login, "login");
        }
    }
}
