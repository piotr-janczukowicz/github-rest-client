package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGlobalAdvisoryCreditTest {

    @Test
    void constructor_shouldThrow_whenUserNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubGlobalAdvisoryCredit(null, GitHubSecurityAdvisoryCreditType.FINDER));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("user");
    }

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubSimpleUser user = UsersCatalogFixtures.octocat();

        final Throwable thrown = catchThrowable(() -> new GitHubGlobalAdvisoryCredit(user, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
