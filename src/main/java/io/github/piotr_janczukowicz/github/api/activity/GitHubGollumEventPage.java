package io.github.piotr_janczukowicz.github.api.activity;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Wiki page that was created or updated.
 *
 * @param pageName The name of the wiki page
 * @param title The title of the wiki page
 * @param summary The summary of the change
 * @param action The action that was performed on the page
 * @param sha The SHA of the commit that changed the page
 * @param htmlUrl The URL of the wiki page
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/gollum-event/properties/pages/items")
@RecordBuilder
public record GitHubGollumEventPage(
        @Nullable String pageName,
        @Nullable String title,
        @Nullable String summary,
        @Nullable String action,
        @Nullable String sha,
        @Nullable String htmlUrl) {}
