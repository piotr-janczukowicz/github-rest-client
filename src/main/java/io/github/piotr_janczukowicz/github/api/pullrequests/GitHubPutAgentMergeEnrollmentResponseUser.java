package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeast;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The user who most recently enabled Agent Merge for the pull request.
 *
 * @param id The unique identifier of the user.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-merge/put-agent-merge-enrollment/200/properties/user")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPutAgentMergeEnrollmentResponseUser(long id) {
    public GitHubPutAgentMergeEnrollmentResponseUser {
        requireAtLeast(id, 1L, "id");
    }
}
