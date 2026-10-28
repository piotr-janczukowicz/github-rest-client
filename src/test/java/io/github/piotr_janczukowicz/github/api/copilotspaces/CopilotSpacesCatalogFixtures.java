package io.github.piotr_janczukowicz.github.api.copilotspaces;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class CopilotSpacesCatalogFixtures {

    private static final Instant CREATED_AT = Instant.parse("2023-01-01T00:00:00Z");
    private static final Instant UPDATED_AT = Instant.parse("2023-01-01T12:00:00Z");
    private static final String GENERAL_INSTRUCTIONS = "Help with React development patterns and best practices";

    private CopilotSpacesCatalogFixtures() {}

    public static GitHubCopilotSpaceOwner copilotSpaceOwnerUser() {
        return GitHubCopilotSpaceOwner.ofUser(UsersCatalogFixtures.octocat());
    }

    public static GitHubCopilotSpaceOwner copilotSpaceOwnerOrganization() {
        return GitHubCopilotSpaceOwner.ofOrganization(OrganizationsCatalogFixtures.organizationSimple());
    }

    public static GitHubCopilotSpace copilotSpace() {
        return GitHubCopilotSpaceBuilder.builder()
                .id(42L)
                .number(1L)
                .name("My Development Space")
                .description("A space for discussing React development patterns")
                .generalInstructions(GENERAL_INSTRUCTIONS)
                .baseRole(GitHubCopilotSpaceBaseRole.NO_ACCESS)
                .owner(copilotSpaceOwnerUser())
                .creator(UsersCatalogFixtures.octocat())
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .htmlUrl("https://github.com/copilot/spaces/octo-org/5")
                .apiUrl("https://api.github.com/organizations/1/copilot-spaces/5")
                .resourcesAttributes(List.of(copilotSpaceResourceAttribute()))
                .build();
    }

    static GitHubCopilotSpaceResourceAttribute copilotSpaceResourceAttribute() {
        return GitHubCopilotSpaceResourceAttributeBuilder.builder()
                .id(101L)
                .resourceType(GitHubCopilotSpaceResourceType.GITHUB_FILE)
                .copilotChatAttachmentId(12L)
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .metadata(copilotSpaceResourceAttributeMetadata())
                .build();
    }

    static GitHubCopilotSpaceResourceAttributeMetadata copilotSpaceResourceAttributeMetadata() {
        return GitHubCopilotSpaceResourceAttributeMetadataBuilder.builder()
                .repositoryId(1_296_269L)
                .filePath("README.md")
                .text("Notes about the project")
                .name("README")
                .number(42)
                .copilotChatAttachmentId(12L)
                .mediaType("image/png")
                .url("https://example.com/image.png")
                .height(480)
                .width(640)
                .build();
    }

    static GitHubCopilotSpaceResource copilotSpaceResource() {
        return GitHubCopilotSpaceResourceBuilder.builder()
                .id(7L)
                .resourceType(GitHubCopilotSpaceResourceType.GITHUB_FILE)
                .copilotChatAttachmentId(12L)
                .metadata(Map.of("repository_id", 1_296_269, "file_path", "README.md"))
                .createdAt(CREATED_AT)
                .updatedAt(UPDATED_AT)
                .build();
    }

    static GitHubCopilotSpacesPage copilotSpacesPage() {
        return new GitHubCopilotSpacesPage(List.of(copilotSpace()));
    }

    static GitHubCopilotSpaceResourcesPage copilotSpaceResourcesPage() {
        return new GitHubCopilotSpaceResourcesPage(List.of(copilotSpaceResource()));
    }

    static GitHubCopilotSpaceResourceAttributeInput copilotSpaceResourceAttributeInput() {
        return new GitHubCopilotSpaceResourceAttributeInput(
                GitHubCopilotSpaceResourceType.REPOSITORY, copilotSpaceResourceAttributeInputMetadata());
    }

    static GitHubCopilotSpaceResourceAttributeInputMetadata copilotSpaceResourceAttributeInputMetadata() {
        return GitHubCopilotSpaceResourceAttributeInputMetadataBuilder.builder()
                .repositoryId(1_296_269L)
                .filePath("README.md")
                .text("Notes about the project")
                .name("README")
                .number(42)
                .build();
    }

    static GitHubCreateForOrgPayload createForOrgPayload() {
        return GitHubCreateForOrgPayloadBuilder.builder()
                .name("Team Planning Space")
                .description("Organization space for team planning and coordination")
                .generalInstructions("Help the team with planning and coordination tasks")
                .baseRole(GitHubCopilotSpaceBaseRole.NO_ACCESS)
                .resourcesAttributes(List.of(copilotSpaceResourceAttributeInput()))
                .build();
    }

    static GitHubUpdateForOrgPayload updateForOrgPayload() {
        return GitHubUpdateForOrgPayloadBuilder.builder()
                .name("Updated Team Planning Space")
                .description("Updated organization space for team planning and coordination")
                .generalInstructions("Updated instructions to help the team with planning and coordination tasks")
                .baseRole(GitHubCopilotSpaceBaseRole.READER)
                .resourcesAttributes(List.of(copilotSpaceResourceAttributeInput()))
                .build();
    }

    static GitHubCreateForUserPayload createForUserPayload() {
        return GitHubCreateForUserPayloadBuilder.builder()
                .name("My Development Space")
                .description("Personal space for development assistance")
                .generalInstructions(GENERAL_INSTRUCTIONS)
                .baseRole(GitHubCopilotSpaceUserBaseRole.NO_ACCESS)
                .resourcesAttributes(List.of(copilotSpaceResourceAttributeInput()))
                .build();
    }

    static GitHubUpdateForUserPayload updateForUserPayload() {
        return GitHubUpdateForUserPayloadBuilder.builder()
                .name("Updated Development Space")
                .description("Updated personal space for development assistance")
                .generalInstructions("Updated instructions to help me with React development patterns")
                .baseRole(GitHubCopilotSpaceUserBaseRole.READER)
                .resourcesAttributes(List.of(copilotSpaceResourceAttributeInput()))
                .build();
    }

    static GitHubAddCollaboratorForOrgPayload addCollaboratorForOrgPayload() {
        return new GitHubAddCollaboratorForOrgPayload(
                GitHubCopilotSpaceActorType.TEAM, "justice-league", GitHubCopilotSpaceCollaboratorRole.WRITER);
    }

    static GitHubAddCollaboratorForUserPayload addCollaboratorForUserPayload() {
        return new GitHubAddCollaboratorForUserPayload(
                GitHubCopilotSpaceActorType.USER, "octocat", GitHubCopilotSpaceCollaboratorRole.READER);
    }

    static GitHubUpdateCollaboratorPayload updateCollaboratorPayload() {
        return new GitHubUpdateCollaboratorPayload(GitHubUpdateCollaboratorRole.ADMIN);
    }

    static GitHubCreateResourcePayload createResourcePayload() {
        return new GitHubCreateResourcePayload(
                GitHubCreateResourceType.GITHUB_FILE, Map.of("repository_id", 1_296_269, "file_path", "README.md"));
    }

    static GitHubUpdateResourcePayload updateResourcePayload() {
        return new GitHubUpdateResourcePayload(Map.of("file_path", "docs/README.md"));
    }

    public static GitHubCopilotSpaceUserCollaborator copilotSpaceUserCollaborator() {
        return GitHubCopilotSpaceUserCollaboratorBuilder.builder()
                .name("monalisa octocat")
                .email("octocat@github.com")
                .login("octocat")
                .id(1L)
                .nodeId("MDQ6VXNlcjE=")
                .avatarUrl("https://github.com/images/error/octocat_happy.gif")
                .gravatarId("")
                .url("https://api.github.com/users/octocat")
                .htmlUrl("https://github.com/octocat")
                .followersUrl("https://api.github.com/users/octocat/followers")
                .followingUrl("https://api.github.com/users/octocat/following{/other_user}")
                .gistsUrl("https://api.github.com/users/octocat/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/octocat/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/octocat/subscriptions")
                .organizationsUrl("https://api.github.com/users/octocat/orgs")
                .reposUrl("https://api.github.com/users/octocat/repos")
                .eventsUrl("https://api.github.com/users/octocat/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/octocat/received_events")
                .type("User")
                .siteAdmin(false)
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .actorType(GitHubCopilotSpaceUserCollaboratorActorType.USER)
                .role(GitHubCopilotSpaceCollaboratorRole.WRITER)
                .build();
    }

    public static GitHubCopilotSpaceTeamCollaborator copilotSpaceTeamCollaborator() {
        return GitHubCopilotSpaceTeamCollaboratorBuilder.builder()
                .actorType(GitHubCopilotSpaceTeamCollaboratorActorType.TEAM)
                .role(GitHubCopilotSpaceCollaboratorRole.READER)
                .id(1L)
                .nodeId("MDQ6VGVhbTE=")
                .name("Justice League")
                .slug("justice-league")
                .type(GitHubCopilotSpaceTeamCollaboratorType.TEAM)
                .description("A great team.")
                .privacy("closed")
                .notificationSetting("notifications_enabled")
                .url("https://api.github.com/teams/1")
                .htmlUrl("https://github.com/orgs/github/teams/justice-league")
                .membersUrl("https://api.github.com/teams/1/members{/member}")
                .repositoriesUrl("https://api.github.com/teams/1/repos")
                .organizationId(1L)
                .build();
    }

    static GitHubCopilotSpaceCollaboratorsPage copilotSpaceCollaboratorsPage() {
        return new GitHubCopilotSpaceCollaboratorsPage(
                List.of(copilotSpaceUserCollaborator(), copilotSpaceTeamCollaborator()));
    }
}
