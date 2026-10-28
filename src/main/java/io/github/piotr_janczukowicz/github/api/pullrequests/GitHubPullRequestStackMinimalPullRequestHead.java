package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Stack list pull request head.
 *
 * @param ref Head branch ref
 * @param sha Head commit SHA
 */
@CatalogSchema("components/schemas/pull-request-stack-minimal/properties/pull_requests/items/properties/head")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubPullRequestStackMinimalPullRequestHead(String ref, String sha) {

    public GitHubPullRequestStackMinimalPullRequestHead {
        requireNonNull(ref, "ref");
        requireNonNull(sha, "sha");
    }
}
