package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.StringUtils.isBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/attestations/{subject_digest}}, excluding pagination.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsFilter}.
 *
 * @param predicateType Optional predicate type to filter attestations
 */
@CatalogSchema("operations/repos/list-attestations/parameters")
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAttestationsFilter(@Nullable String predicateType) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListAttestationsFilter empty() {
        return GitHubListAttestationsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (isBlank(predicateType)) {
            return Map.of();
        }
        return Map.of("predicate_type", predicateType);
    }
}
