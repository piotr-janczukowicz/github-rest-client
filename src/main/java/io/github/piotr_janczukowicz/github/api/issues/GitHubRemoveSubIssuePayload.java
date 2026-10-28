package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Remove sub-issue request body.
 *
 * @param subIssueId The id of the sub-issue to remove
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/remove-sub-issue/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubRemoveSubIssuePayload(long subIssueId) {}
