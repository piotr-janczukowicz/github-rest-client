package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Topic.
 *
 * @param names Topic names.
 */
@RecordBuilder
@CatalogSchema("components/schemas/topic")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record GitHubRepositoryTopics(List<String> names) {

    public GitHubRepositoryTopics {
        requireNonNull(names, "names");
    }
}
