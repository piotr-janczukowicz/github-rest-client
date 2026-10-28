package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Dismiss pull request review request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/dismiss-review/requestBody")
public record GitHubDismissReviewPayload(
        String message, @Nullable GitHubDismissReviewEvent event) {

    public GitHubDismissReviewPayload {
        requireNotBlank(message, "message");
    }
}
