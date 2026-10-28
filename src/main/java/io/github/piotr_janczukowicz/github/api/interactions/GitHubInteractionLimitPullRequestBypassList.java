package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;

/**
 * A list of user logins to add or remove from the pull request creation cap bypass list.
 *
 * @param users A list of user logins to add or remove from the bypass list.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema({
    "components/schemas/interaction-limit-pull-request-bypass-list",
    "operations/interactions/set-pull-request-bypass-list-for-repo/requestBody",
    "operations/interactions/remove-pull-request-bypass-list-for-repo/requestBody"
})
@RecordBuilder
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubInteractionLimitPullRequestBypassList(List<String> users) {

    public GitHubInteractionLimitPullRequestBypassList {
        users = List.copyOf(requireNonNull(users, "users"));
    }
}
