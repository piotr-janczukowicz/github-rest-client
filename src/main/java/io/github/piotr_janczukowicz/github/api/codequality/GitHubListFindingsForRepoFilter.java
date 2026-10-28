package io.github.piotr_janczukowicz.github.api.codequality;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/code-quality/findings}, excluding pagination.
 *
 * @param direction The direction to sort the results by.
 * @param state If specified, only code quality findings with this state will be returned.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/code-quality/list-findings-for-repo/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListFindingsForRepoFilter(
        @Nullable GitHubDirection direction, @Nullable GitHubListFindingsForRepoState state) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListFindingsForRepoFilter empty() {
        return GitHubListFindingsForRepoFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (direction != null) {
            query.put("direction", direction.wireValue());
        }
        if (state != null) {
            query.put("state", state.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
