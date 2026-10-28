package io.github.piotr_janczukowicz.github.api.codesofconduct;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Code Of Conduct.
 *
 * @param key Key
 * @param name Name
 * @param url Url
 * @param body Body
 * @param htmlUrl Html url
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/code-of-conduct")
@RecordBuilder
public record GitHubCodeOfConduct(
        String key,
        String name,
        String url,
        @Nullable String body,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable String htmlUrl) {

    public GitHubCodeOfConduct {
        requireNonNull(key, "key");
        requireNonNull(name, "name");
        requireNonNull(url, "url");
    }
}
