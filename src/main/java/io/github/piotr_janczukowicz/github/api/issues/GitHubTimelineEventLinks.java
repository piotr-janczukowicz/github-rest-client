package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Timeline review event links.
 *
 * @param html Html
 * @param pullRequest Pull request
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/timeline-reviewed-event/properties/_links")
public record GitHubTimelineEventLinks(
        GitHubLink html, @JsonProperty("pull_request") GitHubLink pullRequest) {

    public GitHubTimelineEventLinks {
        requireNonNull(html, "html");
        requireNonNull(pullRequest, "pullRequest");
    }
}
