package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserEmailResponseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUserEmailResponse value = EnterpriseAdminCatalogFixtures.userEmailResponse();

        assertThat(GitHubUserEmailResponseBuilder.builder(value).build()).isEqualTo(value);
    }
}
