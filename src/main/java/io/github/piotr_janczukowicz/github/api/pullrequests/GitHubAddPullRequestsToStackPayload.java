package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireSizeBetween;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * Add pull requests to stack request body.
 *
 * @param pullRequests Ordered pull request numbers to append from the current top upward
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/pull-request-stacks/add/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubAddPullRequestsToStackPayload(List<Integer> pullRequests) {

    private static final String PARAM_PULL_REQUESTS = "pullRequests";

    public GitHubAddPullRequestsToStackPayload {
        pullRequests = List.copyOf(
                requireSizeBetween(requireNonNull(pullRequests, PARAM_PULL_REQUESTS), 1, 100, PARAM_PULL_REQUESTS));
    }
}
