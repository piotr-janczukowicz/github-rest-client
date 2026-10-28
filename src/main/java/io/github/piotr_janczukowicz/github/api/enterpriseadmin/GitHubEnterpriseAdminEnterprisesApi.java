package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Cloud enterprise access restriction REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminEnterprisesApi extends GitHubResourceApi {

    private static final PathTemplate DISABLE_ACCESS_RESTRICTIONS =
            PathTemplate.of("/enterprises/{enterprise}/access-restrictions/disable");
    private static final PathTemplate ENABLE_ACCESS_RESTRICTIONS =
            PathTemplate.of("/enterprises/{enterprise}/access-restrictions/enable");

    /**
     * Enterprise access restriction resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminEnterprisesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Disable the access restrictions of an enterprise ({@code POST
     * /enterprises/{enterprise}/access-restrictions/disable}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return the proxy header that the disabled restriction reports
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubEnterpriseAccessRestrictions disableAccessRestrictions(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(post(DISABLE_ACCESS_RESTRICTIONS, enterprise), GitHubEnterpriseAccessRestrictions.class);
    }

    /**
     * Enable the access restrictions of an enterprise ({@code POST
     * /enterprises/{enterprise}/access-restrictions/enable}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return the proxy header that the enabled restriction reports
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubEnterpriseAccessRestrictions enableAccessRestrictions(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(post(ENABLE_ACCESS_RESTRICTIONS, enterprise), GitHubEnterpriseAccessRestrictions.class);
    }
}
