package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import io.github.piotr_janczukowicz.openapi.model.SuppressSchemaTypeMismatch;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Create gist request body.
 *
 * @param description Description of the gist
 * @param files Names and content for the files that make up the gist
 * @param isPublic Flag indicating whether the gist is public. The spec also allows the strings {@code "true"} and
 *     {@code "false"}, which mean the same; this client sends the boolean
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/gists/create/requestBody")
@RecordBuilder
public record GitHubCreatePayload(
        @Nullable String description,
        Map<String, GitHubCreateGistFile> files,

        @SuppressSchemaTypeMismatch("spec allows the strings true and false too; only the boolean is modelled")
        @JsonProperty("public")
        @Nullable Boolean isPublic) {

    public GitHubCreatePayload {
        files = Map.copyOf(requireNonNull(files, "files"));
    }
}
