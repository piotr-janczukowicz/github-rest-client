package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub Actions OIDC REST resource for API version {@code 2026-03-10}. */
public final class GitHubActionsOidcApi extends GitHubResourceApi {

    private static final PathTemplate ENTERPRISE_PROPERTY_INCLUSIONS =
            PathTemplate.of("/enterprises/{enterprise}/actions/oidc/customization/properties/repo");
    private static final PathTemplate ENTERPRISE_PROPERTY_INCLUSION = PathTemplate.of(
            "/enterprises/{enterprise}/actions/oidc/customization/properties/repo/{custom_property_name}");
    private static final PathTemplate ORG_PROPERTY_INCLUSIONS =
            PathTemplate.of("/orgs/{org}/actions/oidc/customization/properties/repo");
    private static final PathTemplate ORG_PROPERTY_INCLUSION =
            PathTemplate.of("/orgs/{org}/actions/oidc/customization/properties/repo/{custom_property_name}");
    private static final PathTemplate ORG_SUB_TEMPLATE = PathTemplate.of("/orgs/{org}/actions/oidc/customization/sub");
    private static final PathTemplate REPO_SUB_CLAIM =
            PathTemplate.of("/repos/{owner}/{repo}/actions/oidc/customization/sub");

    private static final PathTemplate ENTERPRISE_OIDC_ISSUER =
            PathTemplate.of("/enterprises/{enterprise}/actions/oidc/customization/issuer");
    private static final JsonType<List<GitHubOidcCustomPropertyInclusion>> INCLUSIONS_TYPE =
            JsonTypes.listOf(GitHubOidcCustomPropertyInclusion.class);

    /**
     * OIDC resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubActionsOidcApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List OIDC custom property inclusions for an enterprise ({@code GET
     * /enterprises/{enterprise}/actions/oidc/customization/properties/repo}).
     *
     * @param enterprise enterprise slug
     * @return inclusions
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubOidcCustomPropertyInclusion> listOidcCustomPropertyInclusionsForEnterprise(
            final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ENTERPRISE_PROPERTY_INCLUSIONS, enterprise), INCLUSIONS_TYPE);
    }

    /**
     * Create an OIDC custom property inclusion for an enterprise ({@code POST
     * /enterprises/{enterprise}/actions/oidc/customization/properties/repo}).
     *
     * @param enterprise enterprise slug
     * @param payload custom property to include
     * @return created inclusion
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOidcCustomPropertyInclusion createOidcCustomPropertyInclusionForEnterprise(
            final String enterprise, final GitHubOidcCustomPropertyInclusionInput payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ENTERPRISE_PROPERTY_INCLUSIONS, enterprise), payload),
                GitHubOidcCustomPropertyInclusion.class);
    }

    /**
     * Delete an OIDC custom property inclusion for an enterprise ({@code DELETE
     * /enterprises/{enterprise}/actions/oidc/customization/properties/repo/{custom_property_name}}).
     *
     * @param enterprise enterprise slug
     * @param customPropertyName custom property name
     * @throws IllegalArgumentException if {@code enterprise} or {@code customPropertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteOidcCustomPropertyInclusionForEnterprise(final String enterprise, final String customPropertyName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ENTERPRISE_PROPERTY_INCLUSION, enterprise, customPropertyName));
    }

    /**
     * List OIDC custom property inclusions for an organization ({@code GET
     * /orgs/{org}/actions/oidc/customization/properties/repo}).
     *
     * @param org organization name
     * @return inclusions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubOidcCustomPropertyInclusion> listOidcCustomPropertyInclusionsForOrg(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(ORG_PROPERTY_INCLUSIONS, org), INCLUSIONS_TYPE);
    }

    /**
     * Create an OIDC custom property inclusion for an organization ({@code POST
     * /orgs/{org}/actions/oidc/customization/properties/repo}).
     *
     * @param org organization name
     * @param payload custom property to include
     * @return created inclusion
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOidcCustomPropertyInclusion createOidcCustomPropertyInclusionForOrg(
            final String org, final GitHubOidcCustomPropertyInclusionInput payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(ORG_PROPERTY_INCLUSIONS, org), payload), GitHubOidcCustomPropertyInclusion.class);
    }

    /**
     * Delete an OIDC custom property inclusion for an organization ({@code DELETE
     * /orgs/{org}/actions/oidc/customization/properties/repo/{custom_property_name}}).
     *
     * @param org organization name
     * @param customPropertyName custom property name
     * @throws IllegalArgumentException if {@code org} or {@code customPropertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteOidcCustomPropertyInclusionForOrg(final String org, final String customPropertyName)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ORG_PROPERTY_INCLUSION, org, customPropertyName));
    }

    /**
     * Get the OIDC subject claim customization template of an organization ({@code GET
     * /orgs/{org}/actions/oidc/customization/sub}).
     *
     * @param org organization name
     * @return subject claim template
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOidcCustomSub getOidcCustomSubTemplateForOrg(final String org) throws IOException {
        return caller.fetch(get(ORG_SUB_TEMPLATE, org), GitHubOidcCustomSub.class);
    }

    /**
     * Update the OIDC subject claim customization template of an organization ({@code PUT
     * /orgs/{org}/actions/oidc/customization/sub}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param org organization name
     * @param payload subject claim template
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void updateOidcCustomSubTemplateForOrg(
            final String org, final GitHubUpdateOidcCustomSubTemplateForOrgPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ORG_SUB_TEMPLATE, org), payload));
    }

    /**
     * Get the custom OIDC subject claim of a repository ({@code GET
     * /repos/{owner}/{repo}/actions/oidc/customization/sub}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return subject claim settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubOidcCustomSubRepo getCustomOidcSubClaimForRepo(final String owner, final String repo)
            throws IOException {
        return caller.fetch(get(REPO_SUB_CLAIM, owner, repo), GitHubOidcCustomSubRepo.class);
    }

    /**
     * Set the custom OIDC subject claim of a repository ({@code PUT
     * /repos/{owner}/{repo}/actions/oidc/customization/sub}).
     *
     * <p>GitHub answers HTTP 201 with an empty object, so no body is returned.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload subject claim settings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void setCustomOidcSubClaimForRepo(
            final String owner, final String repo, final GitHubSetCustomOidcSubClaimForRepoPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(REPO_SUB_CLAIM, owner, repo), payload));
    }

    /**
     * Set the OIDC custom issuer policy of an enterprise ({@code PUT
     * /enterprises/{enterprise}/actions/oidc/customization/issuer}).
     *
     * @param enterprise enterprise slug
     * @param payload custom issuer policy
     * @throws IllegalArgumentException if {@code enterprise} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void setOidcCustomIssuerPolicyForEnterprise(
            final String enterprise, final GitHubActionsOidcCustomIssuerPolicyForEnterprise payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(put(ENTERPRISE_OIDC_ISSUER, enterprise), payload));
    }
}
