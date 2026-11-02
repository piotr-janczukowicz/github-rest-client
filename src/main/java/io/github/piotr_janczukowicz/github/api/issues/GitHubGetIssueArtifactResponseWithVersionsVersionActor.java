package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/**
 * The user or integration that created this version.
 *
 * @param login login
 * @param id id
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/issues/get-issue-artifact/200/oneOf/1/properties/versions/items/properties/actor")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubGetIssueArtifactResponseWithVersionsVersionActor(String login, long id) {
    public GitHubGetIssueArtifactResponseWithVersionsVersionActor {
        requireNonNull(login, "login");
    }
}
