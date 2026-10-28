package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code license-upload}.
 *
 * @param apply Whether to apply the license right away
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/enterprise-admin/license-upload/parameters")
@RecordBuilder
@Flavors(GitHubFlavor.GHES)
public record GitHubLicenseUploadFilter(@Nullable Boolean apply) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubLicenseUploadFilter empty() {
        return GitHubLicenseUploadFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (apply == null) {
            return Map.of();
        }

        return Map.of("apply", apply.toString());
    }
}
