package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomDeploymentRuleAppTest {

    @Test
    void constructor_shouldThrow_whenSlugNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCustomDeploymentRuleApp(1, null, "u", "n"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("slug");
    }
}
