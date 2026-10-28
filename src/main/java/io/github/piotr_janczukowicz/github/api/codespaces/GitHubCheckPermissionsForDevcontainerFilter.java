package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Query filters for {@code GET /repos/{owner}/{repo}/codespaces/permissions_check}, excluding pagination.
 *
 * @param ref The git reference that points to the location of the devcontainer configuration to use for the permission
 *     check.
 * @param devcontainerPath Path to the devcontainer.json configuration to use for the permission check.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/check-permissions-for-devcontainer/parameters")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCheckPermissionsForDevcontainerFilter(String ref, String devcontainerPath) {

    public GitHubCheckPermissionsForDevcontainerFilter {
        requireNonNull(ref, "ref");
        requireNonNull(devcontainerPath, "devcontainerPath");
    }

    /**
     * Filter with the required query parameters.
     *
     * @param ref git reference of the devcontainer configuration
     * @param devcontainerPath path to the devcontainer.json configuration
     * @return filter
     */
    public static GitHubCheckPermissionsForDevcontainerFilter of(final String ref, final String devcontainerPath) {
        return new GitHubCheckPermissionsForDevcontainerFilter(ref, devcontainerPath);
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        query.put("ref", ref);
        query.put("devcontainer_path", devcontainerPath);

        return Collections.unmodifiableMap(query);
    }
}
