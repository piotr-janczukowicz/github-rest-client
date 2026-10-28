package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryAdvisoryCreditTest {

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryAdvisoryCredit(
                null, GitHubSecurityAdvisoryCreditType.FINDER, GitHubRepositoryAdvisoryCreditState.ACCEPTED));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final Throwable thrown = catchThrowable(
                () -> new GitHubRepositoryAdvisoryCredit(user, null, GitHubRepositoryAdvisoryCreditState.ACCEPTED));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final Throwable thrown = catchThrowable(
                () -> new GitHubRepositoryAdvisoryCredit(user, GitHubSecurityAdvisoryCreditType.FINDER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }
}
