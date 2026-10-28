package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern. A custom pattern for secret scanning.
 *
 * @param id The ID of the custom pattern.
 * @param name The name of the custom pattern.
 * @param pattern The regular expression of the custom pattern.
 * @param slug A URL-friendly identifier for the custom pattern, derived from its name.
 * @param state The state of the custom pattern.
 * @param pushProtectionEnabled Whether push protection is enabled for this custom pattern.
 * @param startDelimiter The start delimiter regex for the custom pattern.
 * @param endDelimiter The end delimiter regex for the custom pattern.
 * @param mustMatch List of regexes that the secret must match.
 * @param mustNotMatch List of regexes that the secret must not match.
 * @param customPatternVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 * @param createdAt The date and time the custom pattern was created in ISO 8601 format.
 * @param updatedAt The date and time the custom pattern was last updated in ISO 8601 format.
 */
@CatalogSchema("components/schemas/secret-scanning-custom-pattern")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningCustomPattern(
        long id,
        String name,
        String pattern,
        String slug,
        GitHubSecretScanningCustomPatternState state,
        boolean pushProtectionEnabled,
        @Nullable String startDelimiter,
        @Nullable String endDelimiter,
        @Nullable List<String> mustMatch,
        @Nullable List<String> mustNotMatch,
        @Nullable String customPatternVersion,
        @Nullable Instant createdAt,
        @Nullable Instant updatedAt) {

    public GitHubSecretScanningCustomPattern {
        requireNonNull(name, "name");
        requireNonNull(pattern, "pattern");
        requireNonNull(slug, "slug");
        requireNonNull(state, "state");
    }
}
