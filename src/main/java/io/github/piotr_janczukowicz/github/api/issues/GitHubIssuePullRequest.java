package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * Pull request metadata on an issue.
 *
 * @param diffUrl Diff url
 * @param htmlUrl Html url
 * @param patchUrl Patch url
 * @param url Url
 * @param mergedAt Merged at
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/issue/properties/pull_request")
@RecordBuilder
public record GitHubIssuePullRequest(
        @Nullable String diffUrl,
        @Nullable String htmlUrl,
        @Nullable String patchUrl,
        @Nullable String url,
        @Nullable Instant mergedAt) {}
