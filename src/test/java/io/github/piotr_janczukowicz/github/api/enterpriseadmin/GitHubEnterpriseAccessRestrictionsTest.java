package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseAccessRestrictionsTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseAccessRestrictions value = EnterpriseAdminCatalogFixtures.enterpriseAccessRestrictions();

        assertThat(value.message()).isEqualTo("sample-message");
        assertThat(value.headerName()).isEqualTo("sample-header_name");
        assertThat(value.headerValue()).isEqualTo("sample-header_value");
    }

    @Test
    void toString_shouldHideSecrets() {
        final GitHubEnterpriseAccessRestrictions value = EnterpriseAdminCatalogFixtures.enterpriseAccessRestrictions();

        assertThat(value.toString())
                .contains("headerValue=<redacted>", "headerName=" + value.headerName())
                .doesNotContain(value.headerValue());
    }
}
