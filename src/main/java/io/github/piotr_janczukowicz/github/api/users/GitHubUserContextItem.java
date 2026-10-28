package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * User Context Item.
 *
 * @param message Message
 * @param octicon Octicon
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/hovercard/properties/contexts/items")
@RecordBuilder
public record GitHubUserContextItem(String message, String octicon) {

    public GitHubUserContextItem {
        requireNonNull(message, "message");
        requireNonNull(octicon, "octicon");
    }
}
