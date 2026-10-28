package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Generate release notes.
 *
 * @param tagName Tag name
 * @param targetCommitish Target commitish
 * @param previousTagName Previous tag name
 * @param configurationFilePath Configuration file path
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/generate-release-notes/requestBody")
public record GitHubGenerateReleaseNotesPayload(
        String tagName,
        @Nullable String targetCommitish,
        @Nullable String previousTagName,
        @Nullable String configurationFilePath) {

    public GitHubGenerateReleaseNotesPayload {
        requireNonNull(tagName, "tagName");
    }
}
