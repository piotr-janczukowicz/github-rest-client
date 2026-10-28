package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;

/** Pending deployment reviewer ({@code anyOf} simple-user vs team). */
@CatalogSchema("components/schemas/pending-deployment/properties/reviewers/items/properties/reviewer")
public sealed interface GitHubPendingDeploymentReviewer
        permits GitHubPendingDeploymentReviewer.SimpleUser, GitHubPendingDeploymentReviewer.Team {

    /**
     * Reviewer as a simple user.
     *
     * @param user simple user
     * @return reviewer
     */
    static GitHubPendingDeploymentReviewer ofUser(final GitHubSimpleUser user) {
        return new SimpleUser(user);
    }

    /**
     * Reviewer as a team.
     *
     * @param team team
     * @return reviewer
     */
    static GitHubPendingDeploymentReviewer ofTeam(final GitHubTeam team) {
        return new Team(team);
    }

    /**
     * Simple user reviewer.
     *
     * @param user user
     */
    record SimpleUser(GitHubSimpleUser user) implements GitHubPendingDeploymentReviewer {
        public SimpleUser {
            requireNonNull(user, "user");
        }
    }

    /**
     * Team reviewer.
     *
     * @param team team
     */
    record Team(GitHubTeam team) implements GitHubPendingDeploymentReviewer {
        public Team {
            requireNonNull(team, "team");
        }
    }
}
