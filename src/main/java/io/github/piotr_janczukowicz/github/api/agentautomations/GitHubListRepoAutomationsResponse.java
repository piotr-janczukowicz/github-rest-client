package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A page of automations for a repository and the complete filtered result count.
 *
 * @param automations List of automations in the repository
 * @param totalCount Total count of caller-visible automations matching the supplied filters (across all pages)
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/agent-automations/list-repo-automations/200")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubListRepoAutomationsResponse(List<GitHubListRepoAutomationsAutomation> automations, int totalCount) {
    public GitHubListRepoAutomationsResponse {
        requireNonNull(automations, "automations");
    }
}
