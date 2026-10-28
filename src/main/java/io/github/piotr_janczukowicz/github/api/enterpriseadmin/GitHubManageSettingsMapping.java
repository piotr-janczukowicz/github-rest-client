package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * GitHubManageSettingsMapping
 *
 * @param enabled enabled
 * @param tileserver tileserver
 * @param basemap basemap
 * @param token token
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/ghes-get-settings/properties/mapping")
@Flavors(GitHubFlavor.GHES)
public record GitHubManageSettingsMapping(
        @Nullable Boolean enabled,
        @Nullable String tileserver,
        @Nullable String basemap,
        @Nullable String token) {
    /**
     * Describes this record without its secret.
     *
     * @return text with {@code token} redacted
     */
    @Override
    public String toString() {
        return "GitHubManageSettingsMapping[" + "enabled=" + enabled + ", tileserver=" + tileserver + ", basemap="
                + basemap + ", token=<redacted>" + "]";
    }
}
