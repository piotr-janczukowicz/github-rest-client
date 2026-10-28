package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code of Conduct Simple.
 *
 * @param url Url
 * @param key Key
 * @param name Name
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-of-conduct-simple")
@RecordBuilder
public record GitHubCodeOfConductSimple(
        String url, String key, String name, @Nullable String htmlUrl) {

    public GitHubCodeOfConductSimple {
        requireNonNull(url, "url");
        requireNonNull(key, "key");
        requireNonNull(name, "name");
    }
}
