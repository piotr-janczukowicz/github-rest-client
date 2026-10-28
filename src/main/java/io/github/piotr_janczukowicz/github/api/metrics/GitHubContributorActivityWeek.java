package io.github.piotr_janczukowicz.github.api.metrics;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Contributor activity week.
 *
 * @param w Week timestamp
 * @param a Additions
 * @param d Deletions
 * @param c Commits
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/contributor-activity/properties/weeks/items")
public record GitHubContributorActivityWeek(
        @Nullable Long w,
        @Nullable Long a,
        @Nullable Long d,
        @Nullable Long c) {}
