package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Status check contexts request body ({@code oneOf} object-with-{@code contexts} vs string array).
 *
 * @param contexts Status check context names in the object branch
 * @param contextList Status check context names in the array branch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/add-status-check-contexts/requestBody",
    "operations/repos/set-status-check-contexts/requestBody",
    "operations/repos/remove-status-check-contexts/requestBody"
})
public record GitHubStatusCheckContextsPayload(
        @Nullable List<String> contexts,
        @JsonIgnore @Nullable List<String> contextList) {

    private static final String PARAM_CONTEXTS = "contexts";
    private static final String PARAM_CONTEXT_LIST = "contextList";

    public GitHubStatusCheckContextsPayload {
        final boolean hasContexts = contexts != null;
        final boolean hasContextList = contextList != null;

        if (hasContexts == hasContextList) {
            throw new IllegalArgumentException("exactly one of contexts or contextList is required");
        }

        if (contexts != null) {
            contexts = List.copyOf(requireNonNull(contexts, PARAM_CONTEXTS));
        }

        if (contextList != null) {
            contextList = List.copyOf(requireNonNull(contextList, PARAM_CONTEXT_LIST));
        }
    }

    /**
     * Request body with a {@code contexts} property.
     *
     * @param contexts status check context names
     * @return request body
     */
    public static GitHubStatusCheckContextsPayload withContexts(final List<String> contexts) {
        return new GitHubStatusCheckContextsPayload(contexts, null);
    }

    /**
     * Request body as a bare JSON array of context names.
     *
     * @param contextList status check context names
     * @return request body
     */
    public static GitHubStatusCheckContextsPayload withContextList(final List<String> contextList) {
        return new GitHubStatusCheckContextsPayload(null, contextList);
    }
}
