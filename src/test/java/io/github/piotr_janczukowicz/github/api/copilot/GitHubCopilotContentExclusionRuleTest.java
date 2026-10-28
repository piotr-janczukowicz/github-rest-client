package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotContentExclusionRuleTest {

    @Test
    void ofPath_shouldSetPathOnly_whenCalled() {
        final GitHubCopilotContentExclusionRule rule = GitHubCopilotContentExclusionRule.ofPath("/src/kernel.rs");

        assertThat(rule.path()).isEqualTo("/src/kernel.rs");
        assertThat(rule.ifAnyMatch()).isNull();
        assertThat(rule.ifNoneMatch()).isNull();
    }

    @Test
    void ofIfAnyMatch_shouldSetDetailsOnly_whenCalled() {
        final GitHubCopilotContentExclusionRule.IfAnyMatchDetails details =
                new GitHubCopilotContentExclusionRule.IfAnyMatchDetails(List.of("*.rs"));

        final GitHubCopilotContentExclusionRule rule = GitHubCopilotContentExclusionRule.ofIfAnyMatch(details);

        assertThat(rule.ifAnyMatch()).isEqualTo(details);
        assertThat(rule.path()).isNull();
        assertThat(rule.ifNoneMatch()).isNull();
    }

    @Test
    void ofIfNoneMatch_shouldSetDetailsOnly_whenCalled() {
        final GitHubCopilotContentExclusionRule.IfNoneMatchDetails details =
                new GitHubCopilotContentExclusionRule.IfNoneMatchDetails(List.of("*.md"));

        final GitHubCopilotContentExclusionRule rule = GitHubCopilotContentExclusionRule.ofIfNoneMatch(details);

        assertThat(rule.ifNoneMatch()).isEqualTo(details);
        assertThat(rule.path()).isNull();
        assertThat(rule.ifAnyMatch()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotContentExclusionRule(null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void constructor_shouldThrow_whenTwoBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotContentExclusionRule(
                "/src/kernel.rs", new GitHubCopilotContentExclusionRule.IfAnyMatchDetails(List.of("*.rs")), null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one");
    }

    @Test
    void ofPath_shouldThrow_whenPathNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotContentExclusionRule.ofPath(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("path");
    }

    @Test
    void ofIfAnyMatch_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotContentExclusionRule.ofIfAnyMatch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void ofIfNoneMatch_shouldThrow_whenDetailsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotContentExclusionRule.ofIfNoneMatch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("details");
    }

    @Test
    void ifAnyMatchDetailsConstructor_shouldThrow_whenIfAnyMatchNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotContentExclusionRule.IfAnyMatchDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ifAnyMatch");
    }

    @Test
    void ifNoneMatchDetailsConstructor_shouldThrow_whenIfNoneMatchNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotContentExclusionRule.IfNoneMatchDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ifNoneMatch");
    }
}
