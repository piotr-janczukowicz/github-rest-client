package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Update a label.
 *
 * @param newName The new name of the label. Emoji can be added to label names, using either native emoji or colon-style
 *     markup.
 * @param color The hexadecimal color code for the label, without the leading `#`.
 * @param description A short description of the label. Must be 100 characters or fewer.
 * @param archived Whether to archive or unarchive the label. Archived labels cannot be added to issues or pull
 *     requests.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/update-label/requestBody")
@RecordBuilder
public record GitHubUpdateLabelPayload(
        @Nullable String newName,
        @Nullable String color,
        @Nullable String description,
        @Nullable Boolean archived) {}
