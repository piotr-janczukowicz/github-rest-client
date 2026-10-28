package io.github.piotr_janczukowicz.github.api.scim;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /scim/v2/organizations/{org}/Users}, excluding pagination.
 *
 * @param filter Filters results using the equals query parameter operator ({@code eq}). You can filter results that are
 *     equal to {@code id}, {@code userName}, {@code emails}, and {@code externalId}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/scim/list-provisioned-identities/parameters")
@Flavors(GitHubFlavor.GHEC)
@RecordBuilder
public record GitHubListProvisionedIdentitiesFilter(
        @Nullable String filter) {

    /**
     * Filter with no query parameter set: every provisioned identity is listed.
     *
     * @return empty filter
     */
    public static GitHubListProvisionedIdentitiesFilter empty() {
        return GitHubListProvisionedIdentitiesFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (filter == null) {
            return Map.of();
        }

        return Map.of("filter", filter);
    }
}
