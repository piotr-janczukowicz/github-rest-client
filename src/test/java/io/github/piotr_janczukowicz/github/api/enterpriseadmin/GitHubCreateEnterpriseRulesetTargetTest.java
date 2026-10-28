package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseRulesetTargetTest {

    @Test
    void fromWire_shouldReturnBranch_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseRulesetTarget.fromWire("branch"))
                .isEqualTo(GitHubCreateEnterpriseRulesetTarget.BRANCH);
    }

    @Test
    void fromWire_shouldReturnTag_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseRulesetTarget.fromWire("tag"))
                .isEqualTo(GitHubCreateEnterpriseRulesetTarget.TAG);
    }

    @Test
    void fromWire_shouldReturnPush_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseRulesetTarget.fromWire("push"))
                .isEqualTo(GitHubCreateEnterpriseRulesetTarget.PUSH);
    }

    @Test
    void fromWire_shouldReturnRepository_whenKnownValue() {
        assertThat(GitHubCreateEnterpriseRulesetTarget.fromWire("repository"))
                .isEqualTo(GitHubCreateEnterpriseRulesetTarget.REPOSITORY);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateEnterpriseRulesetTarget.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
