package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRulesetSourceTypeTest {

    @Test
    void fromWire_shouldReturnConstant_whenKnownValue() {
        final GitHubRulesetSourceType type = GitHubRulesetSourceType.fromWire("Repository");

        assertThat(type).isEqualTo(GitHubRulesetSourceType.REPOSITORY);
    }

    @Test
    void fromWire_shouldReturnEnterprise_whenKnownValue() {
        final GitHubRulesetSourceType type = GitHubRulesetSourceType.fromWire("Enterprise");

        assertThat(type).isEqualTo(GitHubRulesetSourceType.ENTERPRISE);
    }

    @Test
    void wireValue_shouldReturnSchemaString_whenCalled() {
        final String repository = GitHubRulesetSourceType.REPOSITORY.wireValue();
        final String organization = GitHubRulesetSourceType.ORGANIZATION.wireValue();
        final String enterprise = GitHubRulesetSourceType.ENTERPRISE.wireValue();

        assertSoftly(softly -> {
            softly.assertThat(repository).isEqualTo("Repository");
            softly.assertThat(organization).isEqualTo("Organization");
            softly.assertThat(enterprise).isEqualTo("Enterprise");
        });
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubRulesetSourceType decoded = GitHubRulesetSourceType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubRulesetSourceType.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
