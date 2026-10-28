package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Submit pending pull request review request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/submit-review/requestBody")
public record GitHubSubmitReviewPayload(@Nullable String body, GitHubPullRequestReviewEvent event) {

    public GitHubSubmitReviewPayload {
        requireNonNull(event, "event");
    }
}
