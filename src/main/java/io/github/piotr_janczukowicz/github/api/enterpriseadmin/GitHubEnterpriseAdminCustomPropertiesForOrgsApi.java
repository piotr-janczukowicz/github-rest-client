package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationCustomProperty;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationCustomPropertyPayload;
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
import java.util.Map;
import java.util.Optional;

/** GitHub Enterprise Cloud custom properties for organizations REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminCustomPropertiesForOrgsApi extends GitHubResourceApi {

    private static final PathTemplate DEFINITIONS = PathTemplate.of("/enterprises/{enterprise}/org-properties/schema");
    private static final PathTemplate DEFINITION =
            PathTemplate.of("/enterprises/{enterprise}/org-properties/schema/{custom_property_name}");
    private static final PathTemplate VALUES = PathTemplate.of("/enterprises/{enterprise}/org-properties/values");

    private static final JsonType<List<GitHubOrganizationCustomProperty>> DEFINITIONS_TYPE =
            JsonTypes.listOf(GitHubOrganizationCustomProperty.class);
    private static final JsonType<List<GitHubEnterpriseOrgPropertyValues>> VALUES_TYPE =
            JsonTypes.listOf(GitHubEnterpriseOrgPropertyValues.class);

    /**
     * Custom properties for organizations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminCustomPropertiesForOrgsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List the custom property definitions of an enterprise ({@code GET
     * /enterprises/{enterprise}/org-properties/schema}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return custom property definitions
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubOrganizationCustomProperty> getEnterpriseDefinitions(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(DEFINITIONS, enterprise), DEFINITIONS_TYPE);
    }

    /**
     * Create or update custom property definitions of an enterprise ({@code PATCH
     * /enterprises/{enterprise}/org-properties/schema}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the custom property definitions to create or update
     * @return the resulting custom property definitions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubOrganizationCustomProperty> createOrUpdateEnterpriseDefinitions(
            final String enterprise, final GitHubCreateOrUpdateEnterpriseDefinitionsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(DEFINITIONS, enterprise), payload), DEFINITIONS_TYPE);
    }

    /**
     * Get a custom property definition of an enterprise ({@code GET
     * /enterprises/{enterprise}/org-properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the name of the custom property
     * @return custom property definition, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code enterprise} or {@code customPropertyName} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubOrganizationCustomProperty> getEnterpriseDefinition(
            final String enterprise, final String customPropertyName) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(
                get(DEFINITION, enterprise, customPropertyName), GitHubOrganizationCustomProperty.class);
    }

    /**
     * Create or update a custom property definition of an enterprise ({@code PUT
     * /enterprises/{enterprise}/org-properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the name of the custom property
     * @param payload the custom property definition
     * @return the resulting custom property definition
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} or {@code customPropertyName} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubOrganizationCustomProperty createOrUpdateEnterpriseDefinition(
            final String enterprise,
            final String customPropertyName,
            final GitHubOrganizationCustomPropertyPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(DEFINITION, enterprise, customPropertyName), payload),
                GitHubOrganizationCustomProperty.class);
    }

    /**
     * Remove a custom property definition from an enterprise ({@code DELETE
     * /enterprises/{enterprise}/org-properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the name of the custom property
     * @throws IllegalArgumentException if {@code enterprise} or {@code customPropertyName} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void deleteEnterpriseDefinition(final String enterprise, final String customPropertyName)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(DEFINITION, enterprise, customPropertyName));
    }

    /**
     * List the custom property values of the organizations of an enterprise ({@code GET
     * /enterprises/{enterprise}/org-properties/values}).
     *
     * <p>Uses {@link GitHubPageRequest#byPage()}. See {@link #getEnterpriseValues(String, GitHubPageRequest)}.
     *
     * @param enterprise the slug version of the enterprise name
     * @return first HTTP page of organization property values
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseOrgPropertyValues> getEnterpriseValues(final String enterprise)
            throws IOException {
        return getEnterpriseValues(enterprise, GitHubPageRequest.byPage());
    }

    /**
     * List the custom property values of the organizations of an enterprise ({@code GET
     * /enterprises/{enterprise}/org-properties/values}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param enterprise the slug version of the enterprise name
     * @param request pagination token
     * @return HTTP page of organization property values
     * @throws IllegalArgumentException if {@code enterprise} is empty, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubPage<GitHubEnterpriseOrgPropertyValues> getEnterpriseValues(
            final String enterprise, final GitHubPageRequest request) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(VALUES, Map.of(), request, VALUES_TYPE, enterprise);
    }

    /**
     * Create or update custom property values of organizations of an enterprise ({@code PATCH
     * /enterprises/{enterprise}/org-properties/values}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the organizations and the values to set
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void createOrUpdateEnterpriseValues(
            final String enterprise, final GitHubCreateOrUpdateEnterpriseValuesPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(patch(VALUES, enterprise), payload));
    }
}
