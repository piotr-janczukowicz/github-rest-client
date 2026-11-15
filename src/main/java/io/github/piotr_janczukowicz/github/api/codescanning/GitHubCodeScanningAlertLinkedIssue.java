package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * A GitHub issue linked to a code scanning alert. The issue may belong to a different repository.
 *
 * @param number The issue number.
 * @param title The title of the issue.
 * @param state The state of the issue.
 * @param url The REST API URL of the issue.
 * @param htmlUrl The URL to view the issue on GitHub.
 * @param repository A GitHub repository.
 * @param issueType The type assigned to the issue. This is only present for issues in repositories where issue types
 *     are supported.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-linked-issue")
public record GitHubCodeScanningAlertLinkedIssue(
        int number,
        String title,
        String state,
        String url,
        String htmlUrl,
        GitHubSimpleRepository repository,
        @Nullable GitHubIssueType issueType) {
    public GitHubCodeScanningAlertLinkedIssue {
        requireNonNull(title, "title");
        requireNonNull(state, "state");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(repository, "repository");
    }
}
