package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserRoleAssignmentTest {

    @Test
    void constructor_shouldThrow_whenLoginNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUserRoleAssignment(
                null,
                null,
                null,
                null,
                null,
                1L,
                "MDQ6VXNlcjE=",
                "https://github.com/images/error/octocat_happy.gif",
                null,
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/events{/privacy}",
                "https://api.github.com/users/octocat/received_events",
                GitHubUserType.USER,
                false,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("login");
    }

    @Test
    void constructor_shouldAcceptNullInheritedFrom_whenNotProvided() {
        final GitHubUserRoleAssignment assignment = new GitHubUserRoleAssignment(
                GitHubOrganizationRoleAssignment.DIRECT,
                null,
                null,
                null,
                "octocat",
                1L,
                "MDQ6VXNlcjE=",
                "https://github.com/images/error/octocat_happy.gif",
                null,
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/events{/privacy}",
                "https://api.github.com/users/octocat/received_events",
                GitHubUserType.USER,
                false,
                null,
                null);

        assertThat(assignment.inheritedFrom()).isNull();
    }
}
