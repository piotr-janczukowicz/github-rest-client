package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Raw and HTML formatted text.
 *
 * @param raw Raw text
 * @param html Html text
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/projects-v2-iteration-settings/properties/title",
    "components/schemas/projects-v2-single-select-options/properties/name",
    "components/schemas/projects-v2-single-select-options/properties/description"
})
public record GitHubProjectFormattedText(String raw, String html) {

    public GitHubProjectFormattedText {
        requireNonNull(raw, "raw");
        requireNonNull(html, "html");
    }
}
