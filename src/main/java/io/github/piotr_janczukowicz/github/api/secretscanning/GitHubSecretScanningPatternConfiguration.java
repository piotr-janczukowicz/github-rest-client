package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret scanning pattern configuration. A collection of secret scanning patterns and their settings related to push
 * protection.
 *
 * @param patternConfigVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 * @param providerPatternOverrides Overrides for partner patterns.
 * @param customPatternOverrides Overrides for custom patterns defined by the organization.
 */
@CatalogSchema("components/schemas/secret-scanning-pattern-configuration")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubSecretScanningPatternConfiguration(
        @Nullable String patternConfigVersion,
        @Nullable List<GitHubSecretScanningPatternOverride> providerPatternOverrides,
        @Nullable List<GitHubSecretScanningPatternOverride> customPatternOverrides) {}
