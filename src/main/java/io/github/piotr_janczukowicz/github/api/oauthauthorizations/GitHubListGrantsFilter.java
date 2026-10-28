package io.github.piotr_janczukowicz.github.api.oauthauthorizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code list-grants}, excluding pagination.
 *
 * @param clientId The client ID of your GitHub app
 * @deprecated The OAuth Authorizations API is closing down; create tokens with the web application flow. Deprecation
 *     date {@code 2020-02-14}; removal date {@code 2020-11-13}.
 */
@Deprecated(since = "2020-02-14")
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/oauth-authorizations/list-grants/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubListGrantsFilter(@Nullable String clientId) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListGrantsFilter empty() {
        return GitHubListGrantsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (clientId == null) {
            return Map.of();
        }

        return Map.of("client_id", clientId);
    }
}
