package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern To Update. Fields to update on a custom pattern. At least one updatable field
 * (`pattern`, `start_delimiter`, `end_delimiter`, `must_match`, or `must_not_match`) must also be provided. Only
 * provided fields will be updated.
 *
 * @param pattern The updated regular expression of the custom pattern.
 * @param startDelimiter The updated start delimiter regex for the custom pattern.
 * @param endDelimiter The updated end delimiter regex for the custom pattern.
 * @param mustMatch Updated list of regexes that the secret must match.
 * @param mustNotMatch Updated list of regexes that the secret must not match.
 * @param customPatternVersion The version of the entity. This is used to confirm you're updating the current version of
 *     the entity and mitigate unintentionally overriding someone else's update.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "components/schemas/secret-scanning-custom-pattern-to-update",
    "operations/secret-scanning/update-org-custom-pattern/requestBody",
    "operations/secret-scanning/update-repo-custom-pattern/requestBody"
})
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubUpdateSecretScanningCustomPatternPayload(
        @Nullable String pattern,
        @Nullable String startDelimiter,
        @Nullable String endDelimiter,
        @Nullable List<String> mustMatch,
        @Nullable List<String> mustNotMatch,
        OptionalNull<String> customPatternVersion) {

    public GitHubUpdateSecretScanningCustomPatternPayload {
        requireNonNull(customPatternVersion, "customPatternVersion");
        if (pattern == null
                && startDelimiter == null
                && endDelimiter == null
                && mustMatch == null
                && mustNotMatch == null) {
            throw new IllegalArgumentException("at least one of the updatable fields is required");
        }
        if (mustMatch != null) {
            mustMatch = List.copyOf(mustMatch);
        }
        if (mustNotMatch != null) {
            mustNotMatch = List.copyOf(mustNotMatch);
        }
    }
}
