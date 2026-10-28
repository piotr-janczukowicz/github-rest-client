package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationDetailsMessageTest {

    @Test
    void constructor_shouldThrow_whenMessageTypeNull() {
        final GitHubLiveMigrationDetailsMessageBuilder builder = GitHubLiveMigrationDetailsMessageBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMessage())
                .messageType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("messageType");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final GitHubLiveMigrationDetailsMessageBuilder builder = GitHubLiveMigrationDetailsMessageBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationDetailsMessage())
                .message(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }
}
