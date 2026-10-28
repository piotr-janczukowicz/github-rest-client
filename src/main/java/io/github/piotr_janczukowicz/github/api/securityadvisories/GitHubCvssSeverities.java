package io.github.piotr_janczukowicz.github.api.securityadvisories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Cvss severities.
 *
 * @param cvssV3 Cvss v3
 * @param cvssV4 Cvss v4
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/cvss-severities")
@RecordBuilder
public record GitHubCvssSeverities(
        @Nullable GitHubCvssScore cvssV3, @Nullable GitHubCvssScore cvssV4) {}
