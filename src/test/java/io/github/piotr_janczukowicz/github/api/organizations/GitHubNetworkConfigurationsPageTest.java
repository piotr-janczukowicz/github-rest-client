package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubNetworkConfigurationsPageTest {

    @Test
    void constructor_shouldThrow_whenNetworkConfigurationsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubNetworkConfigurationsPage(0, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
