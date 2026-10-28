package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * File commit content links.
 *
 * @param self Self
 * @param git Git
 * @param html Html
 */
@CatalogSchema("components/schemas/file-commit/properties/content/properties/_links")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubFileCommitContentLinks(
        @Nullable String self,
        @Nullable String git,
        @Nullable String html) {}
