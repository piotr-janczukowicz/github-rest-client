package io.github.piotr_janczukowicz.github.api.releases;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update release.
 *
 * @param tagName Tag name
 * @param targetCommitish Target commitish
 * @param name Name
 * @param body Body
 * @param draft Draft
 * @param prerelease Prerelease
 * @param makeLatest Make latest
 * @param discussionCategoryName Discussion category name
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/update-release/requestBody")
public record GitHubUpdateReleasePayload(
        @Nullable String tagName,
        @Nullable String targetCommitish,
        @Nullable String name,
        @Nullable String body,
        @Nullable Boolean draft,
        @Nullable Boolean prerelease,
        @Nullable GitHubReleaseMakeLatest makeLatest,
        @Nullable String discussionCategoryName) {}
