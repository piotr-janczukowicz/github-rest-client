package io.github.piotr_janczukowicz.github.api.gitignore;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Gitignore Template.
 *
 * @param name Name
 * @param source Source
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gitignore-template")
@RecordBuilder
public record GitHubGitignoreTemplate(String name, String source) {

    public GitHubGitignoreTemplate {
        requireNonNull(name, "name");
        requireNonNull(source, "source");
    }
}
