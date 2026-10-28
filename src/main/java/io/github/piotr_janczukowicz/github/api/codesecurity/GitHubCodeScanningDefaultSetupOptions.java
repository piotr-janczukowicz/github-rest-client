package io.github.piotr_janczukowicz.github.api.codesecurity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Feature options for code scanning default setup.
 *
 * @param runnerType Whether to use labeled runners or standard GitHub runners.
 * @param runnerLabel The label of the runner to use for code scanning default setup when runner_type is 'labeled'.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-scanning-default-setup-options")
@RecordBuilder
public record GitHubCodeScanningDefaultSetupOptions(
        @Nullable GitHubCodeScanningRunnerType runnerType,
        @Nullable String runnerLabel) {}
