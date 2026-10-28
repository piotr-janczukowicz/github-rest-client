package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code scanning alert instance message.
 *
 * @param text Text.
 * @param markdown The message text as GitHub-flavored Markdown, with placeholder links for related locations replaced
 *     by links to the relevant code.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-alert-instance/properties/message")
public record GitHubCodeScanningAlertInstanceMessage(
        @Nullable String text, @Nullable String markdown) {}
