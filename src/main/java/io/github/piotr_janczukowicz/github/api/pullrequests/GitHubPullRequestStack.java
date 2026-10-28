package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Pull request stack.
 *
 * @param base Base ref and SHA of the stack
 * @param size Total number of pull requests in the stack
 * @param position One-based position within the stack
 * @param id Stack id
 * @param number Stack number
 */
@CatalogSchema("components/schemas/pull-request-stack")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestStack(
        GitHubPullRequestStackBase base,
        @Nullable Integer size,
        @Nullable Integer position,
        @Nullable Long id,
        @Nullable Integer number) {

    public GitHubPullRequestStack {
        requireNonNull(base, "base");
    }
}
