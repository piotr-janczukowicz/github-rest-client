package io.github.piotr_janczukowicz.github.api.collaborators;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Add repository collaborator.
 *
 * @param permission The permission to grant the collaborator. Only valid on organization-owned repositories. We accept
 *     the following permissions to be set: {@code pull}, {@code triage}, {@code triage_plus}, {@code push},
 *     {@code maintain}, {@code admin} and you can also specify a custom repository role name, if the owning
 *     organization has defined any.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/add-collaborator/requestBody")
public record GitHubAddCollaboratorPayload(@Nullable String permission) {}
