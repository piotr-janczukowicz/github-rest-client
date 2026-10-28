package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Image added to the output of a check run when updating it.
 *
 * @param alt The alternative text for the image.
 * @param imageUrl The full URL of the image.
 * @param caption A short image description.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/checks/update/requestBody/properties/output/properties/images/items")
public record GitHubUpdateCheckRunImage(
        String alt, String imageUrl, @Nullable String caption) {

    public GitHubUpdateCheckRunImage {
        requireNonNull(alt, "alt");
        requireNonNull(imageUrl, "imageUrl");
    }
}
