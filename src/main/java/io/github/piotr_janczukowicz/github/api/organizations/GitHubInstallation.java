package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * Installation.
 *
 * @param id The ID of the installation
 * @param account Account
 * @param repositorySelection Repository selection
 * @param accessTokensUrl Access tokens url
 * @param repositoriesUrl Repositories url
 * @param htmlUrl Html url
 * @param appId App id
 * @param clientId Client id
 * @param targetId Target id
 * @param targetType Target type
 * @param permissions Permissions
 * @param events Events
 * @param createdAt Created at
 * @param updatedAt Updated at
 * @param singleFileName Single file name
 * @param hasMultipleSingleFiles Has multiple single files
 * @param singleFilePaths Single file paths
 * @param appSlug App slug
 * @param suspendedBy Suspended by
 * @param suspendedAt Suspended at
 * @param contactEmail Contact email
 */
@RecordBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@CatalogSchema("components/schemas/installation")
public record GitHubInstallation(
        long id,
        @Nullable GitHubInstallationAccount account,
        GitHubInstallationRepositorySelection repositorySelection,
        String accessTokensUrl,
        String repositoriesUrl,
        String htmlUrl,
        long appId,
        @Nullable String clientId,
        long targetId,
        String targetType,
        GitHubAppPermissions permissions,
        List<String> events,
        Instant createdAt,
        Instant updatedAt,
        @Nullable String singleFileName,
        @Nullable Boolean hasMultipleSingleFiles,
        @Nullable List<String> singleFilePaths,
        String appSlug,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable GitHubSimpleUser suspendedBy,
        @JsonInclude(JsonInclude.Include.ALWAYS) @Nullable Instant suspendedAt,
        @Nullable String contactEmail) {

    public GitHubInstallation {
        requireNonNull(repositorySelection, "repositorySelection");
        requireNonNull(accessTokensUrl, "accessTokensUrl");
        requireNonNull(repositoriesUrl, "repositoriesUrl");
        requireNonNull(htmlUrl, "htmlUrl");
        requireNonNull(targetType, "targetType");
        requireNonNull(permissions, "permissions");
        requireNonNull(events, "events");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(appSlug, "appSlug");
    }
}
