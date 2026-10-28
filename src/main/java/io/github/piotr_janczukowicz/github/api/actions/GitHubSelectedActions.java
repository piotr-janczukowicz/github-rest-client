package io.github.piotr_janczukowicz.github.api.actions;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Selected actions and reusable workflows that are allowed to run.
 *
 * @param githubOwnedAllowed Whether GitHub-owned actions are allowed
 * @param verifiedAllowed Whether actions from GitHub Marketplace verified creators are allowed
 * @param patternsAllowed String-matching patterns to allow specific actions and reusable workflows
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/selected-actions")
public record GitHubSelectedActions(
        @Nullable Boolean githubOwnedAllowed,
        @Nullable Boolean verifiedAllowed,
        @Nullable List<String> patternsAllowed) {

    public GitHubSelectedActions {
        if (patternsAllowed != null) {
            patternsAllowed = List.copyOf(patternsAllowed);
        }
    }
}
