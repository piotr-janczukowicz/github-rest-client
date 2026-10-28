package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Minimal pull request repository.
 *
 * @param id Repository id
 * @param url Repository API URL
 * @param name Repository name
 */
@CatalogSchema("components/schemas/pull-request-minimal/properties/head/properties/repo")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubPullRequestMinimalRepo(long id, String url, String name) {

    public GitHubPullRequestMinimalRepo {
        requireNonNull(url, "url");
        requireNonNull(name, "name");
    }
}
