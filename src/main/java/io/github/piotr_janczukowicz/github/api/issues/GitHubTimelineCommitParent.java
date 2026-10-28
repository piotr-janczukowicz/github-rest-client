package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Parent commit of a timeline committed event.
 *
 * @param sha SHA for the commit
 * @param url Url
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/timeline-committed-event/properties/parents/items")
public record GitHubTimelineCommitParent(String sha, String url, String htmlUrl) {

    public GitHubTimelineCommitParent {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
        requireNonNull(htmlUrl, "htmlUrl");
    }
}
