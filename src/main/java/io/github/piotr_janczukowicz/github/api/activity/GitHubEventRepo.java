package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Repository of an event. */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/event/properties/repo")
@RecordBuilder
public record GitHubEventRepo(long id, String name, String url) {
    public GitHubEventRepo {
        requireNonNull(name, "name");
        requireNonNull(url, "url");
    }
}
