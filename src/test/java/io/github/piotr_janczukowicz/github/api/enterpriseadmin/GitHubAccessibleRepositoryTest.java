package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAccessibleRepositoryTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubAccessibleRepository value = EnterpriseAdminCatalogFixtures.accessibleRepository();

        assertThat(GitHubAccessibleRepositoryBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubAccessibleRepositoryBuilder.builder(EnterpriseAdminCatalogFixtures.accessibleRepository())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
