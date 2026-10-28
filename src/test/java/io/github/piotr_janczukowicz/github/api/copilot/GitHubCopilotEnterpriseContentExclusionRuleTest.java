package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotEnterpriseContentExclusionRuleTest {

    @Test
    void ofPath_shouldSetPathOnly_whenCalled() {
        final GitHubCopilotEnterpriseContentExclusionRule rule =
                GitHubCopilotEnterpriseContentExclusionRule.ofPath("/src/kernel.rs");

        assertThat(rule.path()).isEqualTo("/src/kernel.rs");
        assertThat(rule.ifAnyMatch()).isNull();
        assertThat(rule.ifNoneMatch()).isNull();
    }

    @Test
    void ofIfAnyMatch_shouldSetDetailsOnly_whenCalled() {
        final GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails details =
                new GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails(List.of("*.rs"));

        final GitHubCopilotEnterpriseContentExclusionRule rule =
                GitHubCopilotEnterpriseContentExclusionRule.ofIfAnyMatch(details);

        assertThat(rule.ifAnyMatch()).isEqualTo(details);
        assertThat(rule.path()).isNull();
        assertThat(rule.ifNoneMatch()).isNull();
    }

    @Test
    void ofIfNoneMatch_shouldSetDetailsOnly_whenCalled() {
        final GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails details =
                new GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails(List.of("*.md"));

        final GitHubCopilotEnterpriseContentExclusionRule rule =
                GitHubCopilotEnterpriseContentExclusionRule.ofIfNoneMatch(details);

        assertThat(rule.ifNoneMatch()).isEqualTo(details);
        assertThat(rule.path()).isNull();
        assertThat(rule.ifAnyMatch()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCopilotEnterpriseContentExclusionRule(null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrow_whenTwoBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotEnterpriseContentExclusionRule(
                "/src/kernel.rs",
                new GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails(List.of("*.rs")),
                null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void ofPath_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotEnterpriseContentExclusionRule.ofPath(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void ofIfAnyMatch_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotEnterpriseContentExclusionRule.ofIfAnyMatch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void ofIfNoneMatch_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotEnterpriseContentExclusionRule.ofIfNoneMatch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void ifAnyMatchDetailsConstructor_shouldThrow_whenIfAnyMatchNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCopilotEnterpriseContentExclusionRule.IfAnyMatchDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ifAnyMatch");
    }

    @Test
    void ifNoneMatchDetailsConstructor_shouldThrow_whenIfNoneMatchNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCopilotEnterpriseContentExclusionRule.IfNoneMatchDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ifNoneMatch");
    }
}
