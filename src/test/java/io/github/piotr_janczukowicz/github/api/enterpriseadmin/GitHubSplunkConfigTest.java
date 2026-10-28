package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSplunkConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubSplunkConfig value = EnterpriseAdminCatalogFixtures.splunkConfig();

        assertThat(GitHubSplunkConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDomainNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubSplunkConfigBuilder.builder(EnterpriseAdminCatalogFixtures.splunkConfig())
                        .domain(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubSplunkConfig value = EnterpriseAdminCatalogFixtures.splunkConfig();

        assertThat(value.toString())
                .contains("encryptedToken=<redacted>", "domain=splunk.example.com")
                .doesNotContain(value.encryptedToken());
    }
}
