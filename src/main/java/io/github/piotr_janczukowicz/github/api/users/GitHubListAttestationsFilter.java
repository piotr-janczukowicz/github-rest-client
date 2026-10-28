package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.StringUtils.isBlank;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /users/{username}/attestations/{subject_digest}}, excluding pagination.
 *
 * <p>Distinct from {@link io.github.piotr_janczukowicz.github.api.repositories.GitHubListAttestationsFilter}.
 *
 * @param predicateType Optional filter for fetching attestations with a given predicate type. This option accepts
 *     `provenance`, `sbom`, `release`, or freeform text for custom predicate types.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/users/list-attestations/parameters",
    "operations/orgs/list-attestations/parameters",
    "operations/orgs/list-attestation-repositories/parameters"
})
@RecordBuilder
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
