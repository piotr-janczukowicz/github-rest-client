package io.github.piotr_janczukowicz.github.api.agenttasks;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Artifact reference identified by ID.
 *
 * @param id Id
 * @param globalId Global id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema(
        "operations/agent-tasks/list-tasks-for-repo/200/properties/tasks/items/properties/artifacts/items/properties/data/oneOf/0")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListTasksForRepoTaskArtifactDataById(
        long id, @Nullable String globalId) implements GitHubListTasksForRepoTaskArtifactData {}
