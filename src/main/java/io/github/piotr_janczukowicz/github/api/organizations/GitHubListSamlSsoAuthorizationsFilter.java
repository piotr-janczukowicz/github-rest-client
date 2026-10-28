package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/credential-authorizations}, excluding pagination.
 *
 * @param login Limits the list of credential authorizations to a specific login
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/list-saml-sso-authorizations/parameters")
@Flavors(GitHubFlavor.GHEC)
public record GitHubListSamlSsoAuthorizationsFilter(
        @Nullable String login) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListSamlSsoAuthorizationsFilter empty() {
        return GitHubListSamlSsoAuthorizationsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        return login == null ? Map.of() : Map.of("login", login);
    }
}
