package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubWorkflowAccessLevelTest {

    @Test
    void fromWire_shouldReturnEnterprise_whenEnterpriseValue() {
        final GitHubWorkflowAccessLevel decoded = GitHubWorkflowAccessLevel.fromWire("enterprise");

        assertThat(decoded).isEqualTo(GitHubWorkflowAccessLevel.ENTERPRISE);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubWorkflowAccessLevel.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubWorkflowAccessLevel.fromWire("unknown-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
