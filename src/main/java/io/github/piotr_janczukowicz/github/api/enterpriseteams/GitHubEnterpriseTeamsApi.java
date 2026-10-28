package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Enterprise teams REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseTeamsApi {

    private final LazyValue<GitHubEnterpriseTeamApi> enterpriseTeam;
    private final LazyValue<GitHubEnterpriseTeamsEnterpriseTeamMembersApi> enterpriseTeamMembers;
    private final LazyValue<GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi> enterpriseTeamOrganizations;

    /**
     * Enterprise teams resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseTeamsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.enterpriseTeam = new LazyValue<>(() -> new GitHubEnterpriseTeamApi(caller));
        this.enterpriseTeamMembers = new LazyValue<>(() -> new GitHubEnterpriseTeamsEnterpriseTeamMembersApi(caller));
        this.enterpriseTeamOrganizations =
                new LazyValue<>(() -> new GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi(caller));
    }

    /**
     * Enterprise team operations ({@code subcategory=enterprise-teams}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return enterprise team API
     */
    public GitHubEnterpriseTeamApi enterpriseTeam() {
        return enterpriseTeam.get();
    }

    /**
     * Enterprise team membership operations ({@code subcategory=enterprise-team-members}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry parts of this resource; see each method.
     *
     * @return enterprise team members API
     */
    public GitHubEnterpriseTeamsEnterpriseTeamMembersApi enterpriseTeamMembers() {
        return enterpriseTeamMembers.get();
    }

    /**
     * Enterprise team organization assignment operations ({@code subcategory=enterprise-team-organizations}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return enterprise team organizations API
     */
    public GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApi enterpriseTeamOrganizations() {
        return enterpriseTeamOrganizations.get();
    }
}
