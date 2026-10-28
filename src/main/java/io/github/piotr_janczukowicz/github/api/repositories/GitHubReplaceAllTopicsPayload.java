package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Replace all repository topics.
 *
 * @param names An array of topics to add to the repository.
 */
@RecordBuilder
@CatalogSchema("operations/repos/replace-all-topics/requestBody")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubReplaceAllTopicsPayload(List<String> names) {

    public GitHubReplaceAllTopicsPayload {
        names = List.copyOf(requireNonNull(names, "names"));
    }
}
