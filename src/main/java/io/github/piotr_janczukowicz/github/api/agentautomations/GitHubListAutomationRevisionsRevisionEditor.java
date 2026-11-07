package io.github.piotr_janczukowicz.github.api.agentautomations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The person who made this change. Omitted for system changes and legacy records.
 *
 * @param id The unique identifier of the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-automations/list-automation-revisions/200/properties/revisions/items/properties/editor")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListAutomationRevisionsRevisionEditor(
        @Nullable Long id) {}
