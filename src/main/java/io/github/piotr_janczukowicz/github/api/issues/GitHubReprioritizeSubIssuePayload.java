package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Reprioritize sub-issue request body.
 *
 * @param subIssueId The id of the sub-issue to reprioritize
 * @param afterId The id of the sub-issue to be prioritized after (either positional argument after OR before should be
 *     specified).
 * @param beforeId The id of the sub-issue to be prioritized before (either positional argument after OR before should
 *     be specified).
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/reprioritize-sub-issue/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubReprioritizeSubIssuePayload(
        long subIssueId, @Nullable Long afterId, @Nullable Long beforeId) {}
