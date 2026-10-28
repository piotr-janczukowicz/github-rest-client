package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseRulesetTargetTest {

    @Test
    void fromWire_shouldReturnBranch_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseRulesetTarget.fromWire("branch"))
                .isEqualTo(GitHubUpdateEnterpriseRulesetTarget.BRANCH);
    }

    @Test
    void fromWire_shouldReturnTag_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseRulesetTarget.fromWire("tag"))
                .isEqualTo(GitHubUpdateEnterpriseRulesetTarget.TAG);
    }

    @Test
    void fromWire_shouldReturnPush_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseRulesetTarget.fromWire("push"))
                .isEqualTo(GitHubUpdateEnterpriseRulesetTarget.PUSH);
    }

    @Test
    void fromWire_shouldReturnRepository_whenKnownValue() {
        assertThat(GitHubUpdateEnterpriseRulesetTarget.fromWire("repository"))
                .isEqualTo(GitHubUpdateEnterpriseRulesetTarget.REPOSITORY);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseRulesetTarget.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
