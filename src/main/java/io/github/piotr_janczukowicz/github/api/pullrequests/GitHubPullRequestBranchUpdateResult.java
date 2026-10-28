package io.github.piotr_janczukowicz.github.api.pullrequests;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request branch update result.
 *
 * @param message Result message
 * @param url Result URL
 */
@CatalogSchema("operations/pulls/update-branch/202")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestBranchUpdateResult(
        @Nullable String message, @Nullable String url) {}
