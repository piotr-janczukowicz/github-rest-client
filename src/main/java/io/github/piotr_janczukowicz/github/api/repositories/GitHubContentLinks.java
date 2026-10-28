package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Content links.
 *
 * @param git Git
 * @param html Html
 * @param self Self
 */
@CatalogSchema("components/schemas/content-file/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubContentLinks(
        @Nullable OptionalNull<String> git, @Nullable OptionalNull<String> html, String self) {
    public GitHubContentLinks {
        requireNonNull(self, "self");
    }
}
