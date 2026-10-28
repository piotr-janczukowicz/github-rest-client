package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Push-protection setting for a custom secret-scanning pattern.
 *
 * @param tokenType The ID of the pattern to configure.
 * @param customPatternVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 * @param pushProtectionSetting Push protection setting to set for the pattern.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/secret-scanning/update-enterprise-pattern-configs/requestBody/properties/custom_pattern_settings/items")
@Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
public record GitHubUpdateEnterprisePatternConfigsCustomPatternSetting(
        @Nullable String tokenType,
        @Nullable OptionalNull<String> customPatternVersion,
        @Nullable GitHubUpdateEnterprisePatternConfigsCustomPatternPushProtectionSetting pushProtectionSetting) {}
