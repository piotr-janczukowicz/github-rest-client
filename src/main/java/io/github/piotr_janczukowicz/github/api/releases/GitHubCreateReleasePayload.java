package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create release.
 *
 * @param tagName Tag name
 * @param targetCommitish Target commitish
 * @param name Name
 * @param body Body
 * @param draft Draft
 * @param prerelease Prerelease
 * @param discussionCategoryName Discussion category name
 * @param generateReleaseNotes Generate release notes
 * @param makeLatest Make latest
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-release/requestBody")
public record GitHubCreateReleasePayload(
        String tagName,
        @Nullable String targetCommitish,
        @Nullable String name,
        @Nullable String body,
        @Nullable Boolean draft,
        @Nullable Boolean prerelease,
        @Nullable String discussionCategoryName,
        @Nullable Boolean generateReleaseNotes,
        @Nullable GitHubReleaseMakeLatest makeLatest) {

    public GitHubCreateReleasePayload {
        requireNonNull(tagName, "tagName");
    }
}
