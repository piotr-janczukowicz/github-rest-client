package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * Pull request for a codespace.
 *
 * @param pullRequestNumber Pull request number.
 * @param repositoryId Repository id for this codespace.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/create-for-authenticated-user/requestBody/oneOf/1/properties/pull_request")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespacePullRequest(int pullRequestNumber, long repositoryId) {}
