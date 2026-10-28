package io.github.piotr_janczukowicz.github.api.codescanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration for code scanning default setup.
 *
 * @param state Code scanning default setup has been configured or not.
 * @param languages Languages to be analyzed.
 * @param runnerType Runner type to be used.
 * @param runnerLabel Runner label to be used if the runner type is labeled.
 * @param querySuite CodeQL query suite to be used.
 * @param threatModel Threat model to be used for code scanning analysis.
 * @param updatedAt Timestamp of latest configuration update.
 * @param schedule The frequency of the periodic analysis.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-scanning-default-setup")
public record GitHubCodeScanningDefaultSetup(
        @Nullable GitHubCodeScanningDefaultSetupState state,
        @Nullable List<GitHubCodeScanningDefaultSetupLanguage> languages,
        @Nullable GitHubCodeScanningDefaultSetupRunnerType runnerType,
        @Nullable String runnerLabel,
        @Nullable GitHubCodeScanningDefaultSetupQuerySuite querySuite,
        @Nullable GitHubCodeScanningDefaultSetupThreatModel threatModel,
        @Nullable Instant updatedAt,
        @Nullable GitHubCodeScanningDefaultSetupSchedule schedule) {}
