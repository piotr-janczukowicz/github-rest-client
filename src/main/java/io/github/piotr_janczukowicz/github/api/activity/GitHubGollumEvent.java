package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Gollum event payload.
 *
 * @param pages The wiki pages that were created or updated
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gollum-event")
@RecordBuilder
public record GitHubGollumEvent(List<GitHubGollumEventPage> pages) implements GitHubEventPayload {

    public GitHubGollumEvent {
        requireNonNull(pages, "pages");
    }
}
