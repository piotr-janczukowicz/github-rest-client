package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLiveMigrationListTest {

    @Test
    void constructor_shouldThrow_whenMigrationsNull() {
        final GitHubLiveMigrationListBuilder builder = GitHubLiveMigrationListBuilder.builder(
                        EnterpriseAdminCatalogFixtures.liveMigrationList())
                .migrations(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("migrations");
    }
}
