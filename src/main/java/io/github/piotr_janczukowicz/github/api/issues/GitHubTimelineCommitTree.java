package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Tree of a timeline committed event.
 *
 * @param sha SHA for the commit
 * @param url Url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/timeline-committed-event/properties/tree")
public record GitHubTimelineCommitTree(String sha, String url) {

    public GitHubTimelineCommitTree {
        requireNonNull(sha, "sha");
        requireNonNull(url, "url");
    }
}
