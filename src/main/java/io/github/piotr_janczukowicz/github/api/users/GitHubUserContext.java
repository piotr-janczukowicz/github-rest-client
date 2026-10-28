package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Hovercard.
 *
 * @param contexts Contexts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/hovercard")
@RecordBuilder
public record GitHubUserContext(List<GitHubUserContextItem> contexts) {

    public GitHubUserContext {
        requireNonNull(contexts, "contexts");
    }
}
