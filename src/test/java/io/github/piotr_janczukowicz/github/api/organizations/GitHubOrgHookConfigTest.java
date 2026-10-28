package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgHookConfigTest {

    @Test
    void toString_shouldHideSecrets() {
        final GitHubOrgHookConfig value = OrganizationsCatalogFixtures.orgHookConfig();

        final String text = value.toString();

        assertSoftly(softly -> {
            softly.assertThat(text).contains("secret=<redacted>");
            softly.assertThat(text).doesNotContain(value.secret());
        });
    }
}
