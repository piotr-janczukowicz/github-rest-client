package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub organization custom properties REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsCustomPropertiesApi extends GitHubResourceApi {

    private static final PathTemplate PROPERTIES_SCHEMA = PathTemplate.of("/orgs/{org}/properties/schema");
    private static final PathTemplate PROPERTIES_SCHEMA_CUSTOM_PROPERTY =
            PathTemplate.of("/orgs/{org}/properties/schema/{custom_property_name}");
    private static final PathTemplate PROPERTIES_VALUES = PathTemplate.of("/orgs/{org}/properties/values");
    private static final PathTemplate PROPERTIES_INSTALLATIONS =
            PathTemplate.of("/orgs/{org}/properties/installations");
    private static final PathTemplate PROPERTIES_INSTALLATIONS_SCHEMA =
            PathTemplate.of("/orgs/{org}/properties/installations/schema");
    private static final PathTemplate PROPERTIES_INSTALLATIONS_VALUES =
            PathTemplate.of("/orgs/{org}/properties/installations/values");
    private static final PathTemplate PROPERTIES_INSTALLATIONS_VALUES_PROPERTY =
            PathTemplate.of("/orgs/{org}/properties/installations/values/{property_name}");

    private static final JsonType<List<GitHubCustomProperty>> CUSTOM_PROPERTIES_TYPE =
            JsonTypes.listOf(GitHubCustomProperty.class);
    private static final JsonType<List<GitHubOrgRepoCustomPropertyValues>> ORG_REPO_VALUES_TYPE =
            JsonTypes.listOf(GitHubOrgRepoCustomPropertyValues.class);
    private static final JsonType<List<GitHubExternalProperty>> EXTERNAL_PROPERTIES_TYPE =
            JsonTypes.listOf(GitHubExternalProperty.class);
    private static final JsonType<List<GitHubOrganizationExternalPropertyInstallation>> EXTERNAL_INSTALLATIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationExternalPropertyInstallation.class);

    /**
     * Custom properties resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsCustomPropertiesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get all custom properties for an organization ({@code GET /orgs/{org}/properties/schema}).
     *
     * @param org organization name
     * @return custom property definitions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCustomProperty> listDefinitions(final String org) throws IOException {
        return caller.fetch(get(PROPERTIES_SCHEMA, org), CUSTOM_PROPERTIES_TYPE);
    }

    /**
     * Create or update custom properties for an organization ({@code PATCH /orgs/{org}/properties/schema}).
     *
     * @param org organization name
     * @param payload custom properties
     * @return updated custom property definitions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubCustomProperty> createOrUpdateDefinitions(
            final String org, final GitHubCreateOrUpdateOrganizationDefinitionsPayload payload) throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(PROPERTIES_SCHEMA, org), payload), CUSTOM_PROPERTIES_TYPE);
    }

    /**
     * Get a custom property for an organization ({@code GET /orgs/{org}/properties/schema/{custom_property_name}}).
     *
     * @param org organization name
     * @param customPropertyName custom property name
     * @return custom property definition, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code customPropertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubCustomProperty> getDefinition(final String org, final String customPropertyName)
            throws IOException {

        return caller.fetchOrEmpty(
                get(PROPERTIES_SCHEMA_CUSTOM_PROPERTY, org, customPropertyName), GitHubCustomProperty.class);
    }

    /**
     * Create or update a custom property for an organization ({@code PUT
     * /orgs/{org}/properties/schema/{custom_property_name}}).
     *
     * @param org organization name
     * @param customPropertyName custom property name
     * @param payload custom property fields
     * @return updated custom property definition
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code customPropertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubCustomProperty createOrUpdateDefinition(
            final String org, final String customPropertyName, final GitHubCustomPropertySetPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(PROPERTIES_SCHEMA_CUSTOM_PROPERTY, org, customPropertyName), payload),
                GitHubCustomProperty.class);
    }

    /**
     * Remove a custom property for an organization ({@code DELETE
     * /orgs/{org}/properties/schema/{custom_property_name}}).
     *
     * @param org organization name
     * @param customPropertyName custom property name
     * @throws IllegalArgumentException if {@code org} or {@code customPropertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deleteDefinition(final String org, final String customPropertyName) throws IOException {
        caller.send(delete(PROPERTIES_SCHEMA_CUSTOM_PROPERTY, org, customPropertyName));
    }

    /**
     * List custom property values for organization repositories ({@code GET /orgs/{org}/properties/values}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listValues(String,
     * GitHubOrganizationsCustomPropertyValuesFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of repository custom property values
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgRepoCustomPropertyValues> listValues(final String org) throws IOException {
        return listValues(org, GitHubOrganizationsCustomPropertyValuesFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List custom property values for organization repositories ({@code GET /orgs/{org}/properties/values}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #listValues(String,
     * GitHubOrganizationsCustomPropertyValuesFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param filter optional query filters
     * @return first HTTP page of repository custom property values
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgRepoCustomPropertyValues> listValues(
            final String org, final GitHubOrganizationsCustomPropertyValuesFilter filter) throws IOException {
        return listValues(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List custom property values for organization repositories ({@code GET /orgs/{org}/properties/values}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of repository custom property values
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgRepoCustomPropertyValues> listValues(final String org, final GitHubPageRequest request)
            throws IOException {
        return listValues(org, GitHubOrganizationsCustomPropertyValuesFilter.empty(), request);
    }

    /**
     * List custom property values for organization repositories ({@code GET /orgs/{org}/properties/values}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter optional query filters
     * @param request pagination token
     * @return first HTTP page of repository custom property values
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubOrgRepoCustomPropertyValues> listValues(
            final String org,
            final GitHubOrganizationsCustomPropertyValuesFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return pageList(PROPERTIES_VALUES, filter.toQueryParameters(), request, ORG_REPO_VALUES_TYPE, org);
    }

    /**
     * Create or update custom property values for organization repositories ({@code PATCH
     * /orgs/{org}/properties/values}).
     *
     * @param org organization name
     * @param payload repository names and property values
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void createOrUpdateValues(final String org, final GitHubCreateOrUpdateOrganizationValuesPayload payload)
            throws IOException {
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(PROPERTIES_VALUES, org), payload));
    }

    /**
     * Get registered app installations for external custom properties ({@code GET
     * /orgs/{org}/properties/installations}).
     *
     * <p>A GitHub App installation token only sees its own registration; an authenticated user sees all registrations
     * for the organization.
     *
     * @param org organization name
     * @return registered app installations with their display names
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubOrganizationExternalPropertyInstallation> listExternalAppInstallations(final String org)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(PROPERTIES_INSTALLATIONS, org), EXTERNAL_INSTALLATIONS_TYPE);
    }

    /**
     * Register an app installation for external custom properties ({@code POST /orgs/{org}/properties/installations}).
     *
     * @param org organization name
     * @param payload installation id and display name
     * @return registered app installation
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubOrganizationExternalPropertyInstallation registerExternalAppInstallation(
            final String org, final GitHubExternalPropertiesRegisterInstallationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(PROPERTIES_INSTALLATIONS, org), payload),
                GitHubOrganizationExternalPropertyInstallation.class);
    }

    /**
     * Get all external custom properties for a GitHub App installation in an organization ({@code GET
     * /orgs/{org}/properties/installations/schema}).
     *
     * @param org organization name
     * @return external custom property definitions
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public List<GitHubExternalProperty> listExternalDefinitions(final String org) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(PROPERTIES_INSTALLATIONS_SCHEMA, org), EXTERNAL_PROPERTIES_TYPE);
    }

    /**
     * Create or update external custom property values for organization repositories ({@code PATCH
     * /orgs/{org}/properties/installations/values}).
     *
     * @param org organization name
     * @param payload repository names and external property values
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void createOrUpdateExternalValues(
            final String org, final GitHubExternalPropertiesCreateOrUpdateValuesPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(PROPERTIES_INSTALLATIONS_VALUES, org), payload));
    }

    /**
     * Create or update external custom property values for a property across organization repositories ({@code PATCH
     * /orgs/{org}/properties/installations/values/{property_name}}).
     *
     * @param org organization name
     * @param propertyName external custom property name
     * @param payload repository names and the values of the named property
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code propertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void createOrUpdateExternalValuesForProperty(
            final String org,
            final String propertyName,
            final GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(PROPERTIES_INSTALLATIONS_VALUES_PROPERTY, org, propertyName), payload));
    }

    /**
     * Remove all external custom property values for a property across all organization repositories ({@code DELETE
     * /orgs/{org}/properties/installations/values/{property_name}}).
     *
     * @param org organization name
     * @param propertyName external custom property name
     * @throws IllegalArgumentException if {@code org} or {@code propertyName} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteExternalValues(final String org, final String propertyName) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(PROPERTIES_INSTALLATIONS_VALUES_PROPERTY, org, propertyName));
    }
}
