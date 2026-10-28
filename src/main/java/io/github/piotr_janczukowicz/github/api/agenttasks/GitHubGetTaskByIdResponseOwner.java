package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Owner of the task.
 *
 * @param id Id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-tasks/get-task-by-id/200/properties/owner")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetTaskByIdResponseOwner(@Nullable Long id) {}
