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
 * Query filters for {@code GET /repos/{owner}/{repo}/codespaces/machines}, excluding pagination.
 *
 * @param location The location to check for available machines. Assigned by IP if not provided.
 * @param clientIp IP for location auto-detection when proxying a request.
 * @param ref The branch or commit to check for prebuild availability and devcontainer restrictions.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/repo-machines-for-authenticated-user/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRepoMachinesForAuthenticatedUserFilter(
        @Nullable String location,
        @Nullable String clientIp,
        @Nullable String ref) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubRepoMachinesForAuthenticatedUserFilter empty() {
        return GitHubRepoMachinesForAuthenticatedUserFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        if (location != null) {
            query.put("location", location);
        }
        if (clientIp != null) {
            query.put("client_ip", clientIp);
        }
        if (ref != null) {
            query.put("ref", ref);
        }

        return Collections.unmodifiableMap(query);
    }
}
