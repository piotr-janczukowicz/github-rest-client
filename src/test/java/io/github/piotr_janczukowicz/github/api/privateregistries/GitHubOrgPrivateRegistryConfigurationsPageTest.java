package io.github.piotr_janczukowicz.github.api.privateregistries;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrgPrivateRegistryConfigurationsPageTest {

    @Test
    void constructor_shouldThrow_whenConfigurationsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrgPrivateRegistryConfigurationsPage(0, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("configurations");
    }
}
