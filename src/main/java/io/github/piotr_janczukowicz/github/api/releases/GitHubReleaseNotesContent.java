package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Generated release notes content.
 *
 * @param name Name
 * @param body Body
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/release-notes-content")
public record GitHubReleaseNotesContent(String name, String body) {

    public GitHubReleaseNotesContent {
        requireNonNull(name, "name");
        requireNonNull(body, "body");
    }
}
