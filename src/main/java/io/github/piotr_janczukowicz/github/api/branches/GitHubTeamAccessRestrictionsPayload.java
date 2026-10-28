package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireExactlyOne;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Team access restrictions request body ({@code oneOf} object-with-{@code teams} vs string array).
 *
 * @param teams Team slugs in the object branch
 * @param teamSlugs Team slugs in the array branch
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema({
    "operations/repos/set-team-access-restrictions/requestBody",
    "operations/repos/add-team-access-restrictions/requestBody",
    "operations/repos/remove-team-access-restrictions/requestBody"
})
public record GitHubTeamAccessRestrictionsPayload(
        @Nullable List<String> teams, @JsonIgnore @Nullable List<String> teamSlugs) {

    private static final String PARAM_TEAMS = "teams";
    private static final String PARAM_TEAM_SLUGS = "teamSlugs";

    public GitHubTeamAccessRestrictionsPayload {
        requireExactlyOne(teams, teamSlugs, "teams or teamSlugs");

        if (teams != null) {
            teams = List.copyOf(requireNonNull(teams, PARAM_TEAMS));
        }

        if (teamSlugs != null) {
            teamSlugs = List.copyOf(requireNonNull(teamSlugs, PARAM_TEAM_SLUGS));
        }
    }

    /**
     * Request body with a {@code teams} property.
     *
     * @param teams team slugs
     * @return request body
     */
    public static GitHubTeamAccessRestrictionsPayload withTeams(final List<String> teams) {
        return new GitHubTeamAccessRestrictionsPayload(teams, null);
    }

    /**
     * Request body as a bare JSON array of team slugs.
     *
     * @param teamSlugs team slugs
     * @return request body
     */
    public static GitHubTeamAccessRestrictionsPayload withTeamSlugs(final List<String> teamSlugs) {
        return new GitHubTeamAccessRestrictionsPayload(null, teamSlugs);
    }
}
