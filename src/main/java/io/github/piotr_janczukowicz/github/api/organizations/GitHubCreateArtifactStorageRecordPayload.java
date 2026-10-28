package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_DIGEST;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_GITHUB_REPOSITORY;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.HTTPS_URL;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMinLengthIfPresent;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.net.URI;
import org.jspecify.annotations.Nullable;

/**
 * Create artifact storage record request body.
 *
 * @param name Name
 * @param digest Digest
 * @param version Version
 * @param artifactUrl Artifact url
 * @param path Path
 * @param registryUrl Registry url
 * @param repository Repository
 * @param status Status
 * @param githubRepository GitHub repository
 * @param returnRecords Return records
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/orgs/create-artifact-storage-record/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateArtifactStorageRecordPayload(
        String name,
        String digest,
        @Nullable String version,
        @Nullable URI artifactUrl,
        @Nullable URI path,
        URI registryUrl,
        @Nullable String repository,
        @Nullable GitHubArtifactStorageStatus status,
        @Nullable String githubRepository,
        @Nullable Boolean returnRecords) {

    private static final String PARAM_NAME = "name";
    private static final String PARAM_DIGEST = "digest";
    private static final String PARAM_REGISTRY_URL = "registryUrl";
    private static final String PARAM_ARTIFACT_URL = "artifactUrl";
    private static final String PARAM_GITHUB_REPOSITORY = "githubRepository";
    private static final String PARAM_VERSION = "version";

    public GitHubCreateArtifactStorageRecordPayload {
        name = requireMaxLength(requireMinLength(name, 1, PARAM_NAME), 256, PARAM_NAME);
        digest = requireMatches(digest, ARTIFACT_DIGEST, PARAM_DIGEST);
        requireNonNull(registryUrl, PARAM_REGISTRY_URL);
        final String registryUrlText = registryUrl.toString();
        requireMaxLength(
                requireMinLength(requireMatches(registryUrlText, HTTPS_URL, PARAM_REGISTRY_URL), 1, PARAM_REGISTRY_URL),
                256,
                PARAM_REGISTRY_URL);
        version = requireMaxLengthIfPresent(requireMinLengthIfPresent(version, 1, PARAM_VERSION), 100, PARAM_VERSION);
        if (artifactUrl != null) {
            final String artifactUrlText = artifactUrl.toString();
            requireMaxLength(artifactUrlText, 152, PARAM_ARTIFACT_URL);
            requireMatches(artifactUrlText, HTTPS_URL, PARAM_ARTIFACT_URL);
        }
        if (path != null) {
            requireMaxLength(path.toString(), 512, "path");
        }
        repository = requireMaxLengthIfPresent(repository, 128, "repository");
        githubRepository = requireMaxLengthIfPresent(
                requireMinLengthIfPresent(githubRepository, 1, PARAM_GITHUB_REPOSITORY), 100, PARAM_GITHUB_REPOSITORY);
        if (githubRepository != null) {
            requireMatches(githubRepository, ARTIFACT_GITHUB_REPOSITORY, PARAM_GITHUB_REPOSITORY);
        }
    }
}
