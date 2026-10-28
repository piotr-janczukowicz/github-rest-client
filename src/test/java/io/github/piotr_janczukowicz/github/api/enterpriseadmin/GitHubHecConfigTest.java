package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubHecConfigTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubHecConfig value = EnterpriseAdminCatalogFixtures.hecConfig();

        assertThat(GitHubHecConfigBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenDomainNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubHecConfigBuilder.builder(EnterpriseAdminCatalogFixtures.hecConfig())
                        .domain(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubHecConfig value = EnterpriseAdminCatalogFixtures.hecConfig();

        assertThat(value.toString())
                .contains("encryptedToken=<redacted>", "path=/services/collector")
                .doesNotContain(value.encryptedToken());
    }
}
