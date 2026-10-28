package io.github.piotr_janczukowicz.github.api.teams;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.Map;
import org.jspecify.annotations.Nullable;

/**
 * Query filters for {@code GET /orgs/{org}/teams/{team_slug}/members}, excluding pagination.
 *
 * @param role Filters members returned by their role in the team.
 */
@CatalogSchema({"operations/teams/list-members-in-org/parameters", "operations/teams/list-members-legacy/parameters"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
public record GitHubListTeamMembersFilter(@Nullable GitHubTeamMembersListRole role) {

    /**
     * Filter with all query parameters unset.
     *
     * @return empty filter
     */
    public static GitHubListTeamMembersFilter empty() {
        return GitHubListTeamMembersFilterBuilder.builder().build();
    }

    /**
     * Non-null query parameters for this filter.
     *
     * @return query map
     */
    public Map<String, String> toQueryParameters() {
        if (role == null) {
            return Map.of();
        }
        return Map.of("role", role.wireValue());
    }
}
