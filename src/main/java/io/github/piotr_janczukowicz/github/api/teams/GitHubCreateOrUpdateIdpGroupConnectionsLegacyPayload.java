package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Create or update IdP group connections of a team (legacy).
 *
 * @param groups The IdP groups to connect to a GitHub team. The new {@code groups} replace the original ones.
 * @param syncedAt The time the groups were last synced
 * @deprecated Endpoint closing down notice: this endpoint route is closing down and will be removed from the Teams API.
 *     Deprecation date {@code 2020-01-21}; removal date {@code 2021-02-01}.
 */
@Deprecated(since = "2020-01-21")
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/teams/create-or-update-idp-group-connections-legacy/requestBody")
@Flavors(GitHubFlavor.GHEC)
public record GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload(
        List<GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup> groups,
        @Nullable String syncedAt) {

    public GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload {
        requireNonNull(groups, "groups");
        groups = List.copyOf(groups);
    }
}
