package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Server organization administration REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminOrgsApi extends GitHubResourceApi {

    private static final PathTemplate ORGANIZATIONS = PathTemplate.of("/admin/organizations");
    private static final PathTemplate ORGANIZATION = PathTemplate.of("/admin/organizations/{org}");

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminOrgsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create an organization ({@code POST /admin/organizations}).
     *
     * @param payload organization to create
     * @return created organization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubOrganizationSimple createOrg(final GitHubCreateOrgPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ORGANIZATIONS), payload), GitHubOrganizationSimple.class);
    }

    /**
     * Update an organization login ({@code PATCH /admin/organizations/{org}}).
     *
     * @param org organization login
     * @param payload new login
     * @return acknowledgement
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubUpdateOrgName updateOrgName(final String org, final GitHubUpdateOrgNamePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ORGANIZATION, org), payload), GitHubUpdateOrgName.class);
    }
}
