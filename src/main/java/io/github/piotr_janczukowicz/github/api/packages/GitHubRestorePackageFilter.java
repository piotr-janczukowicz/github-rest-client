package io.github.piotr_janczukowicz.github.api.packages;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code POST /orgs/{org}/packages/{package_type}/{package_name}/restore}, {@code POST
 * /user/packages/{package_type}/{package_name}/restore}, and {@code POST
 * /users/{username}/packages/{package_type}/{package_name}/restore}, excluding pagination.
 *
 * @param token package token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "operations/packages/restore-package-for-org/parameters",
    "operations/packages/restore-package-for-authenticated-user/parameters",
    "operations/packages/restore-package-for-user/parameters"
})
@RecordBuilder
public record GitHubRestorePackageFilter(@Nullable String token) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubRestorePackageFilter empty() {
        return GitHubRestorePackageFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (token == null) {
            return Map.of();
        }

        return Map.of("token", token);
    }

    /**
     * Describes this record without its secret.
     *
     * @return text with {@code token} redacted
     */
    @Override
    public String toString() {
        return "GitHubRestorePackageFilter[token=" + (token == null ? "null" : "<redacted>") + "]";
    }
}
