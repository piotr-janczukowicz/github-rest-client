package io.github.piotr_janczukowicz.github.api.dependabot;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Result of reviewing a dismissal request.
 *
 * @param dismissalReviewId The identifier of the dismissal review
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/dependabot/review-dismissal-request-for-repo/200")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubReviewDismissalRequestResult(@Nullable Long dismissalReviewId) {}
