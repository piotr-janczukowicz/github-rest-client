package io.github.piotr_janczukowicz.github.api.releases;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update release asset.
 *
 * @param name Name
 * @param label Label
 * @param state State
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-release-asset/requestBody")
public record GitHubUpdateReleaseAssetPayload(
        @Nullable String name,
        @Nullable String label,
        @Nullable String state) {}
