package io.github.piotr_janczukowicz.github.api.teams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Teams REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Interact with organization teams and the authenticated user's teams.
 */
public final class GitHubTeamsApi {

    private final LazyValue<GitHubTeamApi> team;
    private final LazyValue<GitHubTeamsMembersApi> members;
    private final LazyValue<GitHubTeamsExternalGroupsApi> externalGroups;
    private final LazyValue<GitHubTeamsTeamSyncApi> teamSync;

    /**
     * Teams resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubTeamsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.team = new LazyValue<>(() -> new GitHubTeamApi(caller));
        this.members = new LazyValue<>(() -> new GitHubTeamsMembersApi(caller));
        this.externalGroups = new LazyValue<>(() -> new GitHubTeamsExternalGroupsApi(caller));
        this.teamSync = new LazyValue<>(() -> new GitHubTeamsTeamSyncApi(caller));
    }

    /**
     * Team operations ({@code subcategory=teams}).
     *
     * @return team API
     */
    public GitHubTeamApi team() {
        return team.get();
    }

    /**
     * Team member operations ({@code subcategory=members}).
     *
     * @return members API
     */
    public GitHubTeamsMembersApi members() {
        return members.get();
    }

    /**
     * External group operations ({@code subcategory=external-groups}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return external groups API
     */
    public GitHubTeamsExternalGroupsApi externalGroups() {
        return externalGroups.get();
    }

    /**
     * Team sync operations ({@code subcategory=team-sync}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return team sync API
     */
    public GitHubTeamsTeamSyncApi teamSync() {
        return teamSync.get();
    }
}
