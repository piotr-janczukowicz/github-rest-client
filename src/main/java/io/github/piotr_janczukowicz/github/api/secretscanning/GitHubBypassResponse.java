package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import org.jspecify.annotations.Nullable;

/**
 * A response made by a delegated bypasser to a bypass request.
 *
 * @param id The ID of the response to the bypass request
 * @param reviewer The user who reviewed the bypass request
 * @param status The response status to the bypass request until dismissed
 * @param createdAt The date and time the response to the bypass request was created
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/bypass-response")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubBypassResponse(
        @Nullable Long id,
        @Nullable GitHubBypassResponseReviewer reviewer,
        @Nullable GitHubBypassResponseStatus status,
        @Nullable Instant createdAt) {}
