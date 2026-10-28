package io.github.piotr_janczukowicz.github.api.collaborators;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;

final class CollaboratorsCatalogFixtures {

    private CollaboratorsCatalogFixtures() {}

    static GitHubListCollaboratorsFilter listCollaboratorsFilter() {
        return GitHubListCollaboratorsFilterBuilder.builder()
                .affiliation(GitHubCollaboratorAffiliation.ALL)
                .permission(GitHubCollaboratorPermission.ADMIN)
                .build();
    }

    static GitHubCollaboratorPermissions collaboratorPermissions() {
        return new GitHubCollaboratorPermissions(true, true, true, true, true);
    }

    static GitHubCollaborator collaborator() {
        return GitHubCollaboratorBuilder.builder()
                .login("octocat")
                .id(1L)
                .email("octocat@github.com")
                .name("monalisa octocat")
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
                .permissions(collaboratorPermissions())
                .roleName("admin")
                .userViewType("public")
                .build();
    }

    static GitHubRepositoryCollaboratorPermission repositoryCollaboratorPermission() {
        return new GitHubRepositoryCollaboratorPermission("admin", "admin", collaborator());
    }

    static GitHubAddCollaboratorPayload addCollaboratorPayload() {
        return new GitHubAddCollaboratorPayload("push");
    }

    static GitHubUpdateRepositoryInvitationPayload updateRepositoryInvitationPayload() {
        return new GitHubUpdateRepositoryInvitationPayload(GitHubUpdateRepositoryInvitationPermission.READ);
    }

    static GitHubRepositoryInvitation repositoryInvitation() {
        return new GitHubRepositoryInvitation(
                42L,
                RepositoriesCatalogFixtures.helloWorldMinimalRepository(),
                UsersCatalogFixtures.monalisa(),
                UsersCatalogFixtures.octocat(),
                GitHubRepositoryInvitationPermission.READ,
                Instant.parse("2016-06-13T19:52:50Z"),
                true,
                "https://api.github.com/user/repository_invitations/42",
                "https://github.com/octocat/Hello-World/invitations",
                "MDEyOk9yZ2FuaXphdGlvbkludml0YXRpb24x");
    }
}
