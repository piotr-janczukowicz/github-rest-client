package io.github.piotr_janczukowicz.github.api.codespaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import org.jspecify.annotations.Nullable;

/**
 * Create a codespace in a repository for the authenticated user.
 *
 * @param ref Git ref (typically a branch name) for this codespace.
 * @param location The requested location for a new codespace. Best efforts are made to respect this preference.
 * @param geo The geographic area for this codespace. If not specified, the value is assigned by IP.
 * @param clientIp IP for location auto-detection when proxying a request.
 * @param machine Machine type to use for this codespace.
 * @param devcontainerPath Path to devcontainer.json config to use for this codespace.
 * @param multiRepoPermissionsOptOut Whether to authorize requested permissions from devcontainer.json.
 * @param workingDirectory Working directory for this codespace.
 * @param idleTimeoutMinutes Time in minutes before codespace stops from inactivity.
 * @param displayName Display name for this codespace.
 * @param retentionPeriodMinutes Duration in minutes after codespace has gone idle in which it will be deleted.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("operations/codespaces/create-with-repo-for-authenticated-user/requestBody")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCreateWithRepoForAuthenticatedUserPayload(
        @Nullable String ref,
        @Nullable String location,
        @Nullable GitHubCodespaceGeo geo,
        @Nullable String clientIp,
        @Nullable String machine,
        @Nullable String devcontainerPath,
        @Nullable Boolean multiRepoPermissionsOptOut,
        @Nullable String workingDirectory,
        @Nullable Integer idleTimeoutMinutes,
        @Nullable String displayName,
        @Nullable Integer retentionPeriodMinutes) {}
