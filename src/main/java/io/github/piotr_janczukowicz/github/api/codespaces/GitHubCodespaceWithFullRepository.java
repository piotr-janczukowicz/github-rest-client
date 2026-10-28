package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.time.Instant;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A codespace created from a full repository.
 *
 * @param id Unique identifier of the codespace.
 * @param name Automatically generated name of this codespace.
 * @param displayName Display name for this codespace.
 * @param environmentId UUID identifying this codespace's environment.
 * @param owner The owner of the codespace.
 * @param billableOwner The user who has been billed for the codespace.
 * @param repository Full repository the codespace was created from.
 * @param machine The machine powering the codespace.
 * @param devcontainerPath Path to devcontainer.json from repo root used to create Codespace.
 * @param prebuild Whether the codespace was created from a prebuild.
 * @param createdAt Timestamp when the codespace was created.
 * @param updatedAt Timestamp when the codespace was last updated.
 * @param lastUsedAt Last known time this codespace was started.
 * @param state State of this codespace.
 * @param url API URL for this codespace.
 * @param gitStatus Details about the codespace's git repository.
 * @param location The initially assigned location of a new codespace.
 * @param idleTimeoutMinutes The number of minutes of inactivity after which this codespace will be auto-stopped.
 * @param webUrl URL to access this codespace on the web.
 * @param machinesUrl API URL to access available alternate machine types for this codespace.
 * @param startUrl API URL to start this codespace.
 * @param stopUrl API URL to stop this codespace.
 * @param publishUrl API URL to publish this codespace to a new repository.
 * @param pullsUrl API URL for the Pull Request associated with this codespace, if any.
 * @param recentFolders Recently opened folders inside the codespace.
 * @param runtimeConstraints Runtime constraints of the codespace.
 * @param pendingOperation Whether or not a codespace has a pending async operation.
 * @param pendingOperationDisabledReason Text to show user when codespace is disabled by a pending operation.
 * @param idleTimeoutNotice Text to show user when codespace idle timeout minutes has been overridden.
 * @param retentionPeriodMinutes Duration in minutes after codespace has gone idle in which it will be deleted.
 * @param retentionExpiresAt When a codespace will be auto-deleted.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/codespace-with-full-repository")
@Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
public record GitHubCodespaceWithFullRepository(
        long id,
        String name,
        @Nullable String displayName,
        @Nullable String environmentId,
        GitHubSimpleUser owner,
        GitHubSimpleUser billableOwner,
        GitHubFullRepository repository,
        @Nullable GitHubCodespaceMachine machine,
        @Nullable String devcontainerPath,
        @Nullable Boolean prebuild,
        Instant createdAt,
        Instant updatedAt,
        Instant lastUsedAt,
        GitHubCodespaceState state,
        String url,
        GitHubCodespaceGitStatus gitStatus,
        GitHubCodespaceLocation location,
        @Nullable Integer idleTimeoutMinutes,
        String webUrl,
        String machinesUrl,
        String startUrl,
        String stopUrl,
        @Nullable String publishUrl,
        @Nullable String pullsUrl,
        List<String> recentFolders,
        @Nullable GitHubCodespaceRuntimeConstraints runtimeConstraints,
        @Nullable Boolean pendingOperation,
        @Nullable String pendingOperationDisabledReason,
        @Nullable String idleTimeoutNotice,
        @Nullable Integer retentionPeriodMinutes,
        @Nullable Instant retentionExpiresAt) {

    public GitHubCodespaceWithFullRepository {
        requireNonNull(name, "name");
        requireNonNull(owner, "owner");
        requireNonNull(billableOwner, "billableOwner");
        requireNonNull(repository, "repository");
        requireNonNull(createdAt, "createdAt");
        requireNonNull(updatedAt, "updatedAt");
        requireNonNull(lastUsedAt, "lastUsedAt");
        requireNonNull(state, "state");
        requireNonNull(url, "url");
        requireNonNull(gitStatus, "gitStatus");
        requireNonNull(location, "location");
        requireNonNull(webUrl, "webUrl");
        requireNonNull(machinesUrl, "machinesUrl");
        requireNonNull(startUrl, "startUrl");
        requireNonNull(stopUrl, "stopUrl");
        requireNonNull(recentFolders, "recentFolders");
    }
}
