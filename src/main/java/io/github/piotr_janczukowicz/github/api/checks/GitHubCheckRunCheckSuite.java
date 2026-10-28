package io.github.piotr_janczukowicz.github.api.checks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Check suite that the check run belongs to.
 *
 * @param id The id of the check suite.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/check-run/properties/check_suite")
public record GitHubCheckRunCheckSuite(long id) {}
