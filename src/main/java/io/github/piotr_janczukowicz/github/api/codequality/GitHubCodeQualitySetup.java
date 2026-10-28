package io.github.piotr_janczukowicz.github.api.codequality;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Configuration for code quality setup.
 *
 * @param state Code quality setup has been configured or not.
 * @param languages Languages to be analyzed.
 * @param runnerType Runner type to be used.
 * @param runnerLabel Runner label to be used if the runner type is labeled.
 * @param updatedAt Timestamp of latest configuration update.
 * @param schedule The frequency of the periodic analysis.
 * @param aiFindingsOption The AI findings configuration for the repository.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/code-quality-setup")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodeQualitySetup(
        @Nullable GitHubCodeQualitySetupState state,
        @Nullable List<GitHubCodeQualitySetupLanguage> languages,
        @Nullable GitHubCodeQualitySetupRunnerType runnerType,
        @Nullable String runnerLabel,
        @Nullable Instant updatedAt,
        @Nullable GitHubCodeQualitySetupSchedule schedule,
        @Nullable GitHubCodeQualitySetupAiFindingsOption aiFindingsOption) {}
