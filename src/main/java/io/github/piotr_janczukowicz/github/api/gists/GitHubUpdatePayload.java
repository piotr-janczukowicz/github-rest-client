package io.github.piotr_janczukowicz.github.api.gists;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Update gist request body.
 *
 * @param description The description of the gist.
 * @param files The gist files to be updated, renamed, or deleted. Each key must match the current filename (including
 *     extension) of the targeted gist file. To delete a file, set the whole file to {@code null}.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/update/requestBody")
@RecordBuilder
public record GitHubUpdatePayload(
        @Nullable String description, @Nullable Map<String, @Nullable GitHubUpdateGistFile> files) {

    public GitHubUpdatePayload {
        if (files != null) {
            files = Collections.unmodifiableMap(new LinkedHashMap<>(files));
        }
    }
}
