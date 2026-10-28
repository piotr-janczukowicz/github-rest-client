package io.github.piotr_janczukowicz.github.api.licenses;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * License content links.
 *
 * @param git Git
 * @param html Html
 * @param self Self
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/license-content/properties/_links")
@RecordBuilder
public record GitHubLicenseContentLinks(
        @Nullable String git, @Nullable String html, String self) {

    public GitHubLicenseContentLinks {
        requireNonNull(self, "self");
    }
}
