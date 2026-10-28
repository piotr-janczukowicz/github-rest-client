package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserEmailTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUserEmail value = EnterpriseAdminCatalogFixtures.userEmail();

        assertThat(GitHubUserEmailBuilder.builder(value).build()).isEqualTo(value);
    }
}
