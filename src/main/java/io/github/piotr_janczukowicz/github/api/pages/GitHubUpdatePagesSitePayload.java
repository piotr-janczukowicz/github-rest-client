package io.github.piotr_janczukowicz.github.api.pages;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update GitHub Pages site.
 *
 * @param cname Cname
 * @param httpsEnforced Https enforced
 * @param buildType Build type
 * @param source Source
 * @param sitePublic Public visibility, JSON property {@code public}. If {@code true}, the site is accessible to anyone
 *     on the internet. The cloud request body schema names it only in the {@code anyOf} of required properties (see the
 *     local spec patches); the ghec schema defines it.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-information-about-pages-site/requestBody")
public record GitHubUpdatePagesSitePayload(
        @Nullable OptionalNull<String> cname,
        @Nullable Boolean httpsEnforced,
        @Nullable GitHubPagesBuildType buildType,
        @Nullable GitHubUpdatePagesSiteSource source,
        @Nullable @JsonProperty("public") Boolean sitePublic) {

    public GitHubUpdatePagesSitePayload {
        if (cname == null && httpsEnforced == null && buildType == null && source == null && sitePublic == null) {
            throw new IllegalArgumentException(
                    "at least one of cname, httpsEnforced, buildType, source, or sitePublic is required");
        }
    }
}
