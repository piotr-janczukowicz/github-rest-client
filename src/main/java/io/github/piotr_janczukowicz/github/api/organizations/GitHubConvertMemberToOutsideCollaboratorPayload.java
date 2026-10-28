package io.github.piotr_janczukowicz.github.api.organizations;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Convert an organization member to an outside collaborator request body.
 *
 * @param async When {@code true}, the request is performed asynchronously and GitHub returns HTTP 202
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/convert-member-to-outside-collaborator/requestBody")
public record GitHubConvertMemberToOutsideCollaboratorPayload(
        @Nullable Boolean async) {}
