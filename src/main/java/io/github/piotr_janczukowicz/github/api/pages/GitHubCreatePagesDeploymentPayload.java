package io.github.piotr_janczukowicz.github.api.pages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * The object used to create GitHub Pages deployment
 *
 * @param artifactId The ID of an artifact that contains the .zip or .tar of static assets to deploy. The artifact
 *     belongs to the repository. Either {@code artifact_id} or {@code artifact_url} are required.
 * @param artifactUrl The URL of an artifact that contains the .zip or .tar of static assets to deploy. The artifact
 *     belongs to the repository. Either {@code artifact_id} or {@code artifact_url} are required.
 * @param environment The target environment for this GitHub Pages deployment.
 * @param pagesBuildVersion A unique string that represents the version of the build for this deployment.
 * @param oidcToken The OIDC token issued by GitHub Actions certifying the origin of the deployment.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/repos/create-pages-deployment/requestBody")
public record GitHubCreatePagesDeploymentPayload(
        @Nullable Double artifactId,
        @Nullable String artifactUrl,
        @Nullable String environment,
        String pagesBuildVersion,
        String oidcToken) {

    public GitHubCreatePagesDeploymentPayload {
        requireNonNull(pagesBuildVersion, "pagesBuildVersion");
        requireNonNull(oidcToken, "oidcToken");
    }

    /**
     * Describes this GitHubCreatePagesDeploymentPayload without credential material.
     *
     * @return text with {@code oidcToken} redacted
     */
    @Override
    public String toString() {
        return "GitHubCreatePagesDeploymentPayload[artifactId=" + artifactId + ", artifactUrl=" + artifactUrl
                + ", environment=" + environment + ", pagesBuildVersion=" + pagesBuildVersion
                + ", oidcToken=<redacted>]";
    }
}
