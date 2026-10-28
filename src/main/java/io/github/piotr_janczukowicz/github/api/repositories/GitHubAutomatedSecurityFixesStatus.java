package io.github.piotr_janczukowicz.github.api.repositories;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Check Dependabot security updates.
 *
 * @param enabled Whether Dependabot security updates are enabled for the repository.
 * @param paused Whether Dependabot security updates are paused for the repository.
 */
@CatalogSchema("components/schemas/check-automated-security-fixes")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubAutomatedSecurityFixesStatus(boolean enabled, boolean paused) {}
