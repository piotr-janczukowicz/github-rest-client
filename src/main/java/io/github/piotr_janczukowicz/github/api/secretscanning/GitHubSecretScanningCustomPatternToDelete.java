package io.github.piotr_janczukowicz.github.api.secretscanning;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern To Delete. A custom pattern to delete in a bulk operation.
 *
 * @param patternId The ID of the custom pattern to delete.
 * @param customPatternVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 */
@CatalogSchema("components/schemas/secret-scanning-custom-pattern-to-delete")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningCustomPatternToDelete(
        long patternId, @Nullable String customPatternVersion) {}
