package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Secret Scanning Custom Pattern To Create. A custom pattern to create in a bulk operation.
 *
 * @param name The name of the custom pattern.
 * @param pattern The regular expression of the custom pattern.
 * @param startDelimiter The start delimiter regex for the custom pattern. Defaults to `\A|[^0-9A-Za-z]` when not
 *     specified.
 * @param endDelimiter The end delimiter regex for the custom pattern. Defaults to `\z|[^0-9A-Za-z]` when not specified.
 * @param mustMatch List of regexes that the secret must match.
 * @param mustNotMatch List of regexes that the secret must not match.
 */
@CatalogSchema("components/schemas/secret-scanning-custom-pattern-to-create")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubSecretScanningCustomPatternToCreate(
        String name,
        String pattern,
        @Nullable String startDelimiter,
        @Nullable String endDelimiter,
        @Nullable List<String> mustMatch,
        @Nullable List<String> mustNotMatch) {

    public GitHubSecretScanningCustomPatternToCreate {
        requireNonNull(name, "name");
        requireNonNull(pattern, "pattern");
        if (mustMatch != null) {
            mustMatch = List.copyOf(mustMatch);
        }
        if (mustNotMatch != null) {
            mustNotMatch = List.copyOf(mustNotMatch);
        }
    }
}
