package io.github.piotr_janczukowicz.github.api.packages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/packages/{package_type}/{package_name}/versions} and {@code GET
 * /user/packages/{package_type}/{package_name}/versions}, excluding pagination.
 *
 * @param state The state of the package, either active or deleted.
 */
@CatalogSchema({
    "operations/packages/get-all-package-versions-for-package-owned-by-org/parameters",
    "operations/packages/get-all-package-versions-for-package-owned-by-authenticated-user/parameters"
})
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubListPackageVersionsFilter(@Nullable GitHubPackageVersionState state) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListPackageVersionsFilter empty() {
        return GitHubListPackageVersionsFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (state == null) {
            return Map.of();
        }

        return Map.of("state", state.wireValue());
    }
}
