package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Hook response.
 *
 * @param code Code
 * @param status Status
 * @param message Message
 */
@CatalogSchema("components/schemas/hook-response")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubHookResponse(
        @Nullable Integer code,
        @Nullable String status,
        @Nullable String message) {}
