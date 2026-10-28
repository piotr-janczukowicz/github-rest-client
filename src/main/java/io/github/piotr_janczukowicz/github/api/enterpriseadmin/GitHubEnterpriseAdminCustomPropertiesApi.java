package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubCustomProperty;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubCustomPropertySetPayload;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * GitHub Enterprise Cloud custom property definitions for repositories REST resource for API version
 * {@code 2026-03-10}.
 */
public final class GitHubEnterpriseAdminCustomPropertiesApi extends GitHubResourceApi {

    private static final PathTemplate DEFINITIONS = PathTemplate.of("/enterprises/{enterprise}/properties/schema");
    private static final PathTemplate PROMOTE_DEFINITION = PathTemplate.of(
            "/enterprises/{enterprise}/properties/schema/organizations/{org}/{custom_property_name}/promote");
    private static final PathTemplate DEFINITION =
            PathTemplate.of("/enterprises/{enterprise}/properties/schema/{custom_property_name}");

    private static final JsonType<List<GitHubCustomProperty>> DEFINITIONS_TYPE =
            JsonTypes.listOf(GitHubCustomProperty.class);

    /**
     * Custom property definition resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminCustomPropertiesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the custom property definitions of an enterprise ({@code GET /enterprises/{enterprise}/properties/schema}).
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
    public List<GitHubCustomProperty> getEnterpriseDefinitions(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(DEFINITIONS, enterprise), DEFINITIONS_TYPE);
    }

    /**
     * Create or update custom property definitions of an enterprise ({@code PATCH
     * /enterprises/{enterprise}/properties/schema}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload the definitions to create or update
     * @return the resulting custom property definitions
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public List<GitHubCustomProperty> createOrUpdateEnterpriseDefinitions(
            final String enterprise, final GitHubCustomPropertiesCreateOrUpdateEnterpriseDefinitionsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(DEFINITIONS, enterprise), payload), DEFINITIONS_TYPE);
    }

    /**
     * Promote an organization custom property definition to the enterprise ({@code PUT
     * /enterprises/{enterprise}/properties/schema/organizations/{org}/{custom_property_name}/promote}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param org the organization name, not case sensitive
     * @param customPropertyName the custom property name
     * @return the promoted custom property definition
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubCustomProperty promoteDefinitionToEnterprise(
            final String enterprise, final String org, final String customPropertyName) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(put(PROMOTE_DEFINITION, enterprise, org, customPropertyName), GitHubCustomProperty.class);
    }

    /**
     * Get a custom property definition of an enterprise ({@code GET
     * /enterprises/{enterprise}/properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the custom property name
     * @return custom property definition, or empty on HTTP 404
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public Optional<GitHubCustomProperty> getEnterpriseDefinition(
            final String enterprise, final String customPropertyName) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(DEFINITION, enterprise, customPropertyName), GitHubCustomProperty.class);
    }

    /**
     * Create or update a custom property definition of an enterprise ({@code PUT
     * /enterprises/{enterprise}/properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the custom property name
     * @param payload the definition to create or update
     * @return the resulting custom property definition
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if a path argument is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubCustomProperty createOrUpdateEnterpriseDefinition(
            final String enterprise, final String customPropertyName, final GitHubCustomPropertySetPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(put(DEFINITION, enterprise, customPropertyName), payload), GitHubCustomProperty.class);
    }

    /**
     * Remove a custom property definition from an enterprise ({@code DELETE
     * /enterprises/{enterprise}/properties/schema/{custom_property_name}}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param customPropertyName the custom property name
     * @throws IllegalArgumentException if a path argument is empty
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
}
