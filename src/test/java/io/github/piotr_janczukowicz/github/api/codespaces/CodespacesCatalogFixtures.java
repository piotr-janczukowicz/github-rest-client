package io.github.piotr_janczukowicz.github.api.codespaces;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class CodespacesCatalogFixtures {

    private static final String ENCRYPTED_VALUE = "c2VjcmV0";
    private static final String KEY_ID = "012345678912345678";
    private static final Instant CREATED_AT = Instant.parse("2021-01-01T19:01:12Z");
    private static final Instant UPDATED_AT = Instant.parse("2021-01-02T10:15:30Z");
    private static final Instant LAST_USED_AT = Instant.parse("2021-01-03T08:00:00Z");
    private static final String API_URL =
            "https://api.github.com/user/codespaces/monalisa-octocat-hello-world-g4wpq6h95q";

    private CodespacesCatalogFixtures() {}

    static GitHubCodespaceState codespaceState() {
        return GitHubCodespaceState.AVAILABLE;
    }

    static GitHubCodespaceLocation codespaceLocation() {
        return GitHubCodespaceLocation.WEST_US2;
    }

    static GitHubCodespaceGeo codespaceGeo() {
        return GitHubCodespaceGeo.US_WEST;
    }

    static GitHubCodespaceMachinePrebuildAvailability codespaceMachinePrebuildAvailability() {
        return GitHubCodespaceMachinePrebuildAvailability.READY;
    }

    static GitHubCodespacesSecretVisibility codespacesSecretVisibility() {
        return GitHubCodespacesSecretVisibility.SELECTED;
    }

    static GitHubCodespacesAccessVisibility codespacesAccessVisibility() {
        return GitHubCodespacesAccessVisibility.SELECTED_MEMBERS;
    }

    static GitHubCodespaceMachine codespaceMachine() {
        return GitHubCodespaceMachineBuilder.builder()
                .name("standardLinux32gb")
                .displayName("4 cores, 16 GB RAM, 64 GB storage")
                .operatingSystem("linux")
                .storageInBytes(68_719_476_736L)
                .memoryInBytes(17_179_869_184L)
                .cpus(4)
                .prebuildAvailability(codespaceMachinePrebuildAvailability())
                .build();
    }

    static GitHubCodespaceGitStatus codespaceGitStatus() {
        return GitHubCodespaceGitStatusBuilder.builder()
                .ahead(0)
                .behind(0)
                .hasUnpushedChanges(false)
                .hasUncommittedChanges(true)
                .ref("main")
                .build();
    }

    static GitHubCodespaceRuntimeConstraints codespaceRuntimeConstraints() {
        return new GitHubCodespaceRuntimeConstraints(List.of("private", "public"));
    }

    static GitHubCodespace codespace() {
        return GitHubCodespaceBuilder.builder()
                .id(1L)
                .name("monalisa-octocat-hello-world-g4wpq6h95q")
                .displayName("bookish space pancake")
                .environmentId("26a7c758-7299-4a73-b978-5a92a7ae98a0")
                .owner(UsersCatalogFixtures.octocat())
                .billableOwner(UsersCatalogFixtures.monalisa())
                .repository(RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .machine(codespaceMachine())
                .devcontainerPath(".devcontainer/devcontainer.json")
                .prebuild(false)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .lastUsedAt(LAST_USED_AT)
                .state(codespaceState())
                .url(API_URL)
                .gitStatus(codespaceGitStatus())
                .location(codespaceLocation())
                .idleTimeoutMinutes(30)
                .webUrl("https://monalisa-octocat-hello-world-g4wpq6h95q.github.dev")
                .machinesUrl(API_URL + "/machines")
                .startUrl(API_URL + "/start")
                .stopUrl(API_URL + "/stop")
                .publishUrl(API_URL + "/publish")
                .pullsUrl("https://api.github.com/repos/octocat/Hello-World/pulls/1")
                .recentFolders(List.of("/workspaces/hello-world"))
                .runtimeConstraints(codespaceRuntimeConstraints())
                .pendingOperation(false)
                .pendingOperationDisabledReason("Codespace is being updated")
                .idleTimeoutNotice("Idle timeout overridden by organization policy")
                .retentionPeriodMinutes(43_200)
                .retentionExpiresAt(Instant.parse("2021-02-01T19:01:12Z"))
                .lastKnownStopNotice("Stopped by organization policy")
                .build();
    }

    static GitHubCodespaceWithFullRepository codespaceWithFullRepository() {
        return GitHubCodespaceWithFullRepositoryBuilder.builder()
                .id(1L)
                .name("monalisa-octocat-hello-world-g4wpq6h95q")
                .displayName("bookish space pancake")
                .environmentId("26a7c758-7299-4a73-b978-5a92a7ae98a0")
                .owner(UsersCatalogFixtures.octocat())
                .billableOwner(UsersCatalogFixtures.monalisa())
                .repository(RepositoriesCatalogFixtures.fullRepository())
                .machine(codespaceMachine())
                .devcontainerPath(".devcontainer/devcontainer.json")
                .prebuild(false)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .lastUsedAt(LAST_USED_AT)
                .state(codespaceState())
                .url(API_URL)
                .gitStatus(codespaceGitStatus())
                .location(codespaceLocation())
                .idleTimeoutMinutes(30)
                .webUrl("https://monalisa-octocat-hello-world-g4wpq6h95q.github.dev")
                .machinesUrl(API_URL + "/machines")
                .startUrl(API_URL + "/start")
                .stopUrl(API_URL + "/stop")
                .publishUrl(API_URL + "/publish")
                .pullsUrl("https://api.github.com/repos/octocat/Hello-World/pulls/1")
                .recentFolders(List.of("/workspaces/hello-world"))
                .runtimeConstraints(codespaceRuntimeConstraints())
                .pendingOperation(false)
                .pendingOperationDisabledReason("Codespace is being updated")
                .idleTimeoutNotice("Idle timeout overridden by organization policy")
                .retentionPeriodMinutes(43_200)
                .retentionExpiresAt(Instant.parse("2021-02-01T19:01:12Z"))
                .build();
    }

    static GitHubCodespaceExportDetails codespaceExportDetails() {
        return GitHubCodespaceExportDetailsBuilder.builder()
                .state("succeeded")
                .completedAt(Instant.parse("2021-01-01T19:01:12Z"))
                .branch("codespace-monalisa-octocat-hello-world-g4wpq6h95q")
                .sha("fd95a81ca01e48ede9f39c799ecbcef817b8a3b2")
                .id("latest")
                .exportUrl(API_URL + "/exports/latest")
                .htmlUrl("https://github.com/octocat/hello-world/tree/codespace-monalisa")
                .build();
    }

    static GitHubCodespacesOrgSecret codespacesOrgSecret() {
        return GitHubCodespacesOrgSecretBuilder.builder()
                .name("MY_SECRET")
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .visibility(codespacesSecretVisibility())
                .selectedRepositoriesUrl(
                        "https://api.github.com/orgs/octocat/codespaces/secrets/MY_SECRET/repositories")
                .build();
    }

    static GitHubCodespacesSecret codespacesSecret() {
        return new GitHubCodespacesSecret(
                "MY_SECRET",
                CREATED_AT,
                UPDATED_AT,
                codespacesSecretVisibility(),
                "https://api.github.com/user/codespaces/secrets/MY_SECRET/repositories");
    }

    static GitHubRepoCodespacesSecret repoCodespacesSecret() {
        return new GitHubRepoCodespacesSecret("MY_SECRET", CREATED_AT, UPDATED_AT);
    }

    static GitHubCodespacesPublicKey codespacesPublicKey() {
        return GitHubCodespacesPublicKeyBuilder.builder()
                .keyId(KEY_ID)
                .key("2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234")
                .id(1L)
                .url("https://api.github.com/user/codespaces/secrets/public-key")
                .title("ssh-ed25519 AAAAC3NzaC1lZDI1NTE5AAAAIOMqqnkVzrm0SdG6UOoqKLsabgH5C9okWi0dh2l9GKJl")
                .createdAt("2021-01-01T19:01:12Z")
                .build();
    }

    static GitHubCodespacesUserPublicKey codespacesUserPublicKey() {
        return new GitHubCodespacesUserPublicKey(KEY_ID, "2Sg8iYjAxxmI2LvUXpJjkYrMxURPc8r+dB7TJyvv1234");
    }

    static GitHubCodespacesPermissionsCheckForDevcontainer codespacesPermissionsCheckForDevcontainer() {
        return new GitHubCodespacesPermissionsCheckForDevcontainer(true);
    }

    static GitHubCodespacesPage codespacesPage() {
        return new GitHubCodespacesPage(1, List.of(codespace()));
    }

    static GitHubOrgCodespacesSecretsPage orgCodespacesSecretsPage() {
        return new GitHubOrgCodespacesSecretsPage(1, List.of(codespacesOrgSecret()));
    }

    static GitHubRepoCodespacesSecretsPage repoCodespacesSecretsPage() {
        return new GitHubRepoCodespacesSecretsPage(1, List.of(repoCodespacesSecret()));
    }

    static GitHubCodespacesSecretsPage codespacesSecretsPage() {
        return new GitHubCodespacesSecretsPage(1, List.of(codespacesSecret()));
    }

    static GitHubCodespacesSelectedRepositoriesPage codespacesSelectedRepositoriesPage() {
        return new GitHubCodespacesSelectedRepositoriesPage(
                1, List.of(RepositoriesCatalogFixtures.helloWorldMinimalRepository()));
    }

    static GitHubCodespaceMachinesPage codespaceMachinesPage() {
        return new GitHubCodespaceMachinesPage(1, List.of(codespaceMachine()));
    }

    static GitHubDevcontainer devcontainer() {
        return new GitHubDevcontainer(".devcontainer/devcontainer.json", "Node.js", "Node.js 20");
    }

    static GitHubDevcontainersPage devcontainersPage() {
        return new GitHubDevcontainersPage(1, List.of(devcontainer()));
    }

    static GitHubCodespaceDefaults codespaceDefaults() {
        return new GitHubCodespaceDefaults("WestUs2", ".devcontainer/devcontainer.json");
    }

    static GitHubPreFlightWithRepoForAuthenticatedUserResponse preFlightWithRepoForAuthenticatedUserResponse() {
        return new GitHubPreFlightWithRepoForAuthenticatedUserResponse(
                UsersCatalogFixtures.monalisa(), codespaceDefaults());
    }

    static GitHubRepoMachinesForAuthenticatedUserFilter repoMachinesForAuthenticatedUserFilter() {
        return GitHubRepoMachinesForAuthenticatedUserFilterBuilder.builder()
                .location("WestUs2")
                .clientIp("1.2.3.4")
                .ref("main")
                .build();
    }

    static GitHubPreFlightWithRepoForAuthenticatedUserFilter preFlightWithRepoForAuthenticatedUserFilter() {
        return GitHubPreFlightWithRepoForAuthenticatedUserFilterBuilder.builder()
                .ref("main")
                .clientIp("1.2.3.4")
                .build();
    }

    static GitHubCheckPermissionsForDevcontainerFilter checkPermissionsForDevcontainerFilter() {
        return GitHubCheckPermissionsForDevcontainerFilter.of("main", ".devcontainer/devcontainer.json");
    }

    static GitHubListForAuthenticatedUserFilter listForAuthenticatedUserFilter() {
        return GitHubListForAuthenticatedUserFilterBuilder.builder()
                .repositoryId(1_296_269L)
                .build();
    }

    static GitHubSetCodespacesAccessPayload setCodespacesAccessPayload() {
        return new GitHubSetCodespacesAccessPayload(codespacesAccessVisibility(), List.of("johnDoe", "atomIO"));
    }

    static GitHubSetCodespacesAccessUsersPayload setCodespacesAccessUsersPayload() {
        return new GitHubSetCodespacesAccessUsersPayload(List.of("johnDoe", "atomIO"));
    }

    static GitHubDeleteCodespacesAccessUsersPayload deleteCodespacesAccessUsersPayload() {
        return new GitHubDeleteCodespacesAccessUsersPayload(List.of("johnDoe", "atomIO"));
    }

    static GitHubCreateOrUpdateOrgSecretPayload createOrUpdateOrgSecretPayload() {
        return new GitHubCreateOrUpdateOrgSecretPayload(
                ENCRYPTED_VALUE, KEY_ID, codespacesSecretVisibility(), List.of(1_296_269L, 1_296_270L));
    }

    static GitHubSetSelectedReposForOrgSecretPayload setSelectedReposForOrgSecretPayload() {
        return new GitHubSetSelectedReposForOrgSecretPayload(List.of(1_296_269L, 1_296_270L));
    }

    static GitHubCreateOrUpdateRepoSecretPayload createOrUpdateRepoSecretPayload() {
        return new GitHubCreateOrUpdateRepoSecretPayload(ENCRYPTED_VALUE, KEY_ID);
    }

    static GitHubCreateWithRepoForAuthenticatedUserPayload createWithRepoForAuthenticatedUserPayload() {
        return GitHubCreateWithRepoForAuthenticatedUserPayloadBuilder.builder()
                .ref("main")
                .location("WestUs2")
                .geo(codespaceGeo())
                .clientIp("1.2.3.4")
                .machine("standardLinux32gb")
                .devcontainerPath(".devcontainer/devcontainer.json")
                .multiRepoPermissionsOptOut(false)
                .workingDirectory("/workspaces/hello-world")
                .idleTimeoutMinutes(30)
                .displayName("bookish space pancake")
                .retentionPeriodMinutes(43_200)
                .build();
    }

    static GitHubCreateWithPrForAuthenticatedUserPayload createWithPrForAuthenticatedUserPayload() {
        return GitHubCreateWithPrForAuthenticatedUserPayloadBuilder.builder()
                .location("WestUs2")
                .geo(codespaceGeo())
                .clientIp("1.2.3.4")
                .machine("standardLinux32gb")
                .devcontainerPath(".devcontainer/devcontainer.json")
                .multiRepoPermissionsOptOut(false)
                .workingDirectory("/workspaces/hello-world")
                .idleTimeoutMinutes(30)
                .displayName("bookish space pancake")
                .retentionPeriodMinutes(43_200)
                .build();
    }

    static GitHubCodespacePullRequest codespacePullRequest() {
        return new GitHubCodespacePullRequest(42, 1_296_269L);
    }

    static GitHubCreateForAuthenticatedUserPayload createForAuthenticatedUserPayloadWithPullRequest() {
        return GitHubCreateForAuthenticatedUserPayloadBuilder.builder()
                .pullRequest(codespacePullRequest())
                .location("WestUs2")
                .geo(codespaceGeo())
                .machine("standardLinux32gb")
                .devcontainerPath(".devcontainer/devcontainer.json")
                .workingDirectory("/workspaces/hello-world")
                .idleTimeoutMinutes(30)
                .build();
    }

    static GitHubCreateForAuthenticatedUserPayload createForAuthenticatedUserPayload() {
        return GitHubCreateForAuthenticatedUserPayloadBuilder.builder()
                .repositoryId(1_296_269L)
                .ref("main")
                .location("WestUs2")
                .geo(codespaceGeo())
                .clientIp("1.2.3.4")
                .machine("standardLinux32gb")
                .devcontainerPath(".devcontainer/devcontainer.json")
                .multiRepoPermissionsOptOut(false)
                .workingDirectory("/workspaces/hello-world")
                .idleTimeoutMinutes(30)
                .displayName("bookish space pancake")
                .retentionPeriodMinutes(43_200)
                .build();
    }

    static GitHubCreateOrUpdateSecretForAuthenticatedUserPayload createOrUpdateSecretForAuthenticatedUserPayload() {
        return new GitHubCreateOrUpdateSecretForAuthenticatedUserPayload(
                ENCRYPTED_VALUE, KEY_ID, List.of("1296269", "1296270"));
    }

    static GitHubSetRepositoriesForSecretForAuthenticatedUserPayload
            setRepositoriesForSecretForAuthenticatedUserPayload() {
        return new GitHubSetRepositoriesForSecretForAuthenticatedUserPayload(List.of(1_296_269L, 1_296_270L));
    }

    static GitHubUpdateForAuthenticatedUserPayload updateForAuthenticatedUserPayload() {
        return new GitHubUpdateForAuthenticatedUserPayload(
                "standardLinux32gb", "bookish space pancake", List.of("/workspaces/hello-world"));
    }

    static GitHubPublishForAuthenticatedUserPayload publishForAuthenticatedUserPayload() {
        return new GitHubPublishForAuthenticatedUserPayload("hello-world-copy", true);
    }
}
