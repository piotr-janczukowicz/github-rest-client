package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The review of the bypass request.
 *
 * @param bypassReviewId ID of the bypass review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/review-bypass-request/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubReviewBypassRequestResult(@Nullable Long bypassReviewId) {}
