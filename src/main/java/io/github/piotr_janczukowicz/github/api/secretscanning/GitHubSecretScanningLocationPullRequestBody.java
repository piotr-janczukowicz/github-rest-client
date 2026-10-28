package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Represents a 'pull_request_body' secret scanning location type. This location type shows that a secret was detected
 * in the body of a pull request.
 *
 * @param pullRequestBodyUrl The API URL to get the pull request where the secret was detected.
 * @param htmlUrl The GitHub URL for the pull request where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-pull-request-body")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationPullRequestBody(
        String pullRequestBodyUrl, @Nullable String htmlUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationPullRequestBody {
        requireNonNull(pullRequestBodyUrl, "pullRequestBodyUrl");
    }
}
