package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/codespaces/new}, excluding pagination.
 *
 * @param ref The branch or commit to check for a default devcontainer path. If not specified, the default branch will
 *     be checked.
 * @param clientIp An alternative IP for default location auto-detection, such as when proxying a request.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/pre-flight-with-repo-for-authenticated-user/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPreFlightWithRepoForAuthenticatedUserFilter(
        @Nullable String ref, @Nullable String clientIp) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubPreFlightWithRepoForAuthenticatedUserFilter empty() {
        return GitHubPreFlightWithRepoForAuthenticatedUserFilterBuilder.builder()
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (ref != null) {
            query.put("ref", ref);
        }
        if (clientIp != null) {
            query.put("client_ip", clientIp);
        }

        return Collections.unmodifiableMap(query);
    }
}
