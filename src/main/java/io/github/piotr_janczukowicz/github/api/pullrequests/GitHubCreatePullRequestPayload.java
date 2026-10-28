package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Create pull request request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/create/requestBody")
public record GitHubCreatePullRequestPayload(
        @Nullable String title,
        String head,
        @Nullable String headRepo,
        String base,
        @Nullable String body,
        @Nullable Boolean maintainerCanModify,
        @Nullable Boolean draft,
        @Nullable Long issue) {

    public GitHubCreatePullRequestPayload {
        requireNotBlank(head, "head");
        requireNotBlank(base, "base");

        if (title == null && issue == null) {
            throw new IllegalArgumentException("title or issue is required");
        }
        if (title != null) {
            requireNotBlank(title, "title");
        }
    }
}
