package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageDownloadUpgradeNodesTest {

    @Test
    void constructor_shouldThrow_whenNodesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubManageDownloadUpgradeNodes(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodes");
    }
}
