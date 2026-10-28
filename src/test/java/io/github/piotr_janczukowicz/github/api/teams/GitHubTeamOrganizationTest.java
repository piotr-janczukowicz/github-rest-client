package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamOrganizationTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final GitHubTeamOrganization base = TeamOrganizationCatalogFixtures.sampleOrganization();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamOrganization(
                null,
                base.id(),
                base.nodeId(),
                base.url(),
                base.reposUrl(),
                base.eventsUrl(),
                base.hooksUrl(),
                base.issuesUrl(),
                base.membersUrl(),
                base.publicMembersUrl(),
                base.avatarUrl(),
                base.description(),
                base.name(),
                base.company(),
                base.blog(),
                base.location(),
                base.email(),
                base.twitterUsername(),
                base.isVerified(),
                base.hasOrganizationProjects(),
                base.hasRepositoryProjects(),
                base.publicRepos(),
                base.publicGists(),
                base.followers(),
                base.following(),
                base.htmlUrl(),
                base.createdAt(),
                base.type(),
                base.totalPrivateRepos(),
                base.ownedPrivateRepos(),
                base.privateGists(),
                base.diskUsage(),
                base.collaborators(),
                base.billingEmail(),
                base.plan(),
                base.defaultRepositoryPermission(),
                base.membersCanCreateRepositories(),
                base.twoFactorRequirementEnabled(),
                base.membersAllowedRepositoryCreationType(),
                base.membersCanCreatePublicRepositories(),
                base.membersCanCreatePrivateRepositories(),
                base.membersCanCreateInternalRepositories(),
                base.membersCanCreatePages(),
                base.membersCanCreatePublicPages(),
                base.membersCanCreatePrivatePages(),
                base.membersCanForkPrivateRepositories(),
                base.webCommitSignoffRequired(),
                base.updatedAt(),
                base.archivedAt()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }
}
