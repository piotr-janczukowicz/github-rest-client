package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code DELETE /enterprises/{enterprise}/credential-authorizations}, excluding pagination.
 *
 * @param integrationId Identifier of the GitHub App integration to revoke credentials for
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/enterprise-admin/revoke-delegated-credential-authorizations/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubRevokeDelegatedCredentialAuthorizationsFilter(
        @Nullable Long integrationId) {

    /**
     * Filter with no query parameter set.
     *
     * @return empty filter
     */
    public static GitHubRevokeDelegatedCredentialAuthorizationsFilter empty() {
        return GitHubRevokeDelegatedCredentialAuthorizationsFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (integrationId == null) {
            return Map.of();
        }

        return Map.of("integration_id", String.valueOf(integrationId));
    }
}
