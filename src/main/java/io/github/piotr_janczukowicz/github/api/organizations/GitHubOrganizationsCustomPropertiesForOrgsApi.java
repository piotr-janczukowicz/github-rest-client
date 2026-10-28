package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;

/** GitHub organization custom property values REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsCustomPropertiesForOrgsApi extends GitHubResourceApi {

    private static final PathTemplate ORG_PROPERTY_VALUES =
            PathTemplate.of("/organizations/{org}/org-properties/values");

    private static final JsonType<List<GitHubCustomPropertyValue>> ORG_PROPERTY_VALUES_TYPE =
            JsonTypes.listOf(GitHubCustomPropertyValue.class);

    /**
     * Custom property values of an organization resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsCustomPropertiesForOrgsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all custom property values for an organization ({@code GET /organizations/{org}/org-properties/values}).
     *
     * @param org organization name
     * @return custom property values of the organization
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubCustomPropertyValue> getOrganizationValues(final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ORG_PROPERTY_VALUES, org), ORG_PROPERTY_VALUES_TYPE);
    }

    /**
     * Create or update custom property values for an organization ({@code PATCH
     * /organizations/{org}/org-properties/values}).
     *
     * @param org organization name
     * @param payload the custom property values to apply
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void createOrUpdateOrganizationValues(
            final String org, final GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(ORG_PROPERTY_VALUES, org), payload));
    }
}
