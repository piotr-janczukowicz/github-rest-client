package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Environment protection reviewer ({@code anyOf} simple-user vs team). */
@CatalogSchema(
        "components/schemas/environment/properties/protection_rules/items/anyOf/1/properties/reviewers/items/properties/reviewer")
public sealed interface GitHubEnvironmentReviewer
        permits GitHubEnvironmentReviewer.SimpleUser, GitHubEnvironmentReviewer.Team {

    /**
     * Reviewer as a simple user.
     *
     * @param user simple user
     * @return reviewer
     */
    static GitHubEnvironmentReviewer ofUser(final GitHubSimpleUser user) {
        return new SimpleUser(user);
    }

    /**
     * Reviewer as a team.
     *
     * @param team team
     * @return reviewer
     */
    static GitHubEnvironmentReviewer ofTeam(final GitHubTeam team) {
        return new Team(team);
    }

    /**
     * Simple user reviewer.
     *
     * @param user user
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubEnvironmentReviewer {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Team reviewer.
     *
     * @param team team
     */
    record Team(GitHubTeam team) implements GitHubEnvironmentReviewer {
        public Team {
            requireNonNull(team, "team");
        }
    }
}
