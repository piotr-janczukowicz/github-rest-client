package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Feature options for code scanning.
 *
 * @param allowAdvanced Whether to allow repos which use advanced setup
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-security-configuration/properties/code_scanning_options")
@RecordBuilder
public record GitHubCodeSecurityConfigurationCodeScanningOptions(
        @Nullable Boolean allowAdvanced) {}
