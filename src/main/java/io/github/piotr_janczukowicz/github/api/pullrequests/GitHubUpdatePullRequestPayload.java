package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/** Update pull request request body. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pulls/update/requestBody")
public record GitHubUpdatePullRequestPayload(
        @Nullable String title,
        @Nullable String body,
        @Nullable GitHubPullRequestUpdateState state,
        @Nullable String base,
        @Nullable Boolean maintainerCanModify) {}
