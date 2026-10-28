package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Update secret scanning pattern configs.
 *
 * @param patternConfigVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 * @param providerPatternSettings Pattern settings for provider patterns.
 * @param customPatternSettings Pattern settings for custom patterns.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/secret-scanning/update-org-pattern-configs/requestBody")
public record GitHubUpdateSecretScanningPatternConfigsPayload(
        @Nullable OptionalNull<String> patternConfigVersion,
        @Nullable List<GitHubSecretScanningProviderPatternSetting> providerPatternSettings,
        @Nullable List<GitHubSecretScanningCustomPatternSetting> customPatternSettings) {

    public GitHubUpdateSecretScanningPatternConfigsPayload {
        if (providerPatternSettings != null) {
            providerPatternSettings = List.copyOf(providerPatternSettings);
        }
        if (customPatternSettings != null) {
            customPatternSettings = List.copyOf(customPatternSettings);
        }
    }
}
