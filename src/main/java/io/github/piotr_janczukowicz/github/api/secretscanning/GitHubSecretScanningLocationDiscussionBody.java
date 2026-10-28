package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Represents a 'discussion_body' secret scanning location type. This location type shows that a secret was detected in
 * the body of a discussion.
 *
 * @param discussionBodyUrl The URL to the discussion where the secret was detected.
 */
@CatalogSchema("components/schemas/secret-scanning-location-discussion-body")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningLocationDiscussionBody(String discussionBodyUrl)
        implements GitHubSecretScanningLocationDetails, GitHubSecretScanningFirstDetectedLocation {

    public GitHubSecretScanningLocationDiscussionBody {
        requireNonNull(discussionBodyUrl, "discussionBodyUrl");
    }
}
