package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemOwnerTypeTest {

    @Test
    void fromWire_shouldReturnUser_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemOwnerType.fromWire("user"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemOwnerType.USER);
    }

    @Test
    void fromWire_shouldReturnOauthApplication_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemOwnerType.fromWire("oauth_application"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemOwnerType.OAUTH_APPLICATION);
    }

    @Test
    void fromWire_shouldReturnGithubApp_whenKnownValue() {
        assertThat(GitHubEnterpriseTokenInventoryItemOwnerType.fromWire("github_app"))
                .isEqualTo(GitHubEnterpriseTokenInventoryItemOwnerType.GITHUB_APP);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseTokenInventoryItemOwnerType decoded =
                GitHubEnterpriseTokenInventoryItemOwnerType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseTokenInventoryItemOwnerType.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
