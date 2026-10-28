package io.github.piotr_janczukowicz.github.api.issues;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Add sub-issue request body.
 *
 * @param subIssueId The id of the sub-issue to add. The sub-issue must belong to the same repository owner as the
 *     parent issue
 * @param replaceParent Option that, when true, instructs the operation to replace the sub-issues current parent issue
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/issues/add-sub-issue/requestBody")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddSubIssuePayload(
        long subIssueId, @Nullable Boolean replaceParent) {}
