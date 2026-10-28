package io.github.piotr_janczukowicz.github.api.ratelimit;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Rate Limit.
 *
 * @param limit Limit
 * @param remaining Remaining
 * @param reset Reset
 * @param used Used
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/rate-limit")
public record GitHubRateLimit(int limit, int remaining, long reset, int used) {}
