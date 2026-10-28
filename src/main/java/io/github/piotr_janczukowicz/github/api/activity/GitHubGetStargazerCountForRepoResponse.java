package io.github.piotr_janczukowicz.github.api.activity;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireAtLeast;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Number of stargazers of a repository.
 *
 * @param count The number of stargazers.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("operations/activity/get-stargazer-count-for-repo/200")
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetStargazerCountForRepoResponse(int count) {

    private static final String PARAM_COUNT = "count";

    public GitHubGetStargazerCountForRepoResponse {
        requireAtLeast(count, 0, PARAM_COUNT);
    }
}
