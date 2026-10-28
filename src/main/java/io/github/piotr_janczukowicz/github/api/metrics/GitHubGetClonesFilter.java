package io.github.piotr_janczukowicz.github.api.metrics;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/traffic/clones}, excluding pagination.
 *
 * @param per The time frame to display results for.
 */
@CatalogSchema({"operations/repos/get-clones/parameters", "operations/repos/get-views/parameters"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetClonesFilter(@Nullable GitHubTrafficPer per) {

    /**
     * Filter with default time frame unset.
     *
     * @return empty filter
     */
    public static GitHubGetClonesFilter empty() {
        return GitHubGetClonesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (per == null) {
            return Map.of();
        }

        return Map.of("per", per.wireValue());
    }
}
