package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/packages}, {@code GET /user/packages}, and {@code GET
 * /users/{username}/packages}, excluding pagination.
 *
 * @param packageType The type of supported package. Packages in GitHub's Gradle registry have the type {@code maven}.
 *     Docker images pushed to GitHub's Container registry ({@code ghcr.io}) have the type {@code container}. You can
 *     use the type {@code docker} to find images that were pushed to GitHub's Docker registry
 *     ({@code docker.pkg.github.com}), even if these have now been migrated to the Container registry.
 * @param visibility The selected visibility of the packages. This parameter is optional and only filters an existing
 *     result set.
 */
@CatalogSchema({
    "operations/packages/list-packages-for-organization/parameters",
    "operations/packages/list-packages-for-authenticated-user/parameters",
    "operations/packages/list-packages-for-user/parameters"
})
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubListPackagesFilter(
        GitHubPackageType packageType, @Nullable GitHubPackageListVisibility visibility) {

    public GitHubListPackagesFilter {
        requireNonNull(packageType, "packageType");
    }

    /**
     * Filter with only the required package type set.
     *
     * @param packageType package type
     * @return filter
     */
    public static GitHubListPackagesFilter of(final GitHubPackageType packageType) {
        return GitHubListPackagesFilterBuilder.builder()
                .packageType(packageType)
                .build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        final Map<String, String> query = new LinkedHashMap<>();

        query.put("package_type", packageType.wireValue());
        if (visibility != null) {
            query.put("visibility", visibility.wireValue());
        }

        return Collections.unmodifiableMap(query);
    }
}
