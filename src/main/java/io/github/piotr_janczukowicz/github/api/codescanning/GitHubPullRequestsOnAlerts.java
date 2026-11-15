package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A pull request linked to a code scanning alert.
 *
 * @param number The number of the linked pull request.
 * @param id The ID of the linked pull request.
 * @param repository A GitHub repository.
 * @param title The title of the linked pull request.
 * @param state The state of the referenced pull request.
 * @param mergedAt The time the linked pull request was merged. Null if it has not been merged.
 * @param htmlUrl The URL of the linked pull request on GitHub.
 * @param url The REST API URL for the linked pull request.
 * @param draft Whether the linked pull request is a draft.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/pull-requests-on-alerts")
public record GitHubPullRequestsOnAlerts(
        int number,
        long id,
        GitHubSimpleRepository repository,
        String title,
        GitHubPullRequestsOnAlertsState state,
        @Nullable Instant mergedAt,
        String htmlUrl,
        String url,
        boolean draft) {
    public GitHubPullRequestsOnAlerts {
        requireNonNull(repository, "repository");
        requireNonNull(title, "title");
        requireNonNull(state, "state");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(url, "url");
    }
}
