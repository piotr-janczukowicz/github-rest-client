package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnvironmentsPageTest {

    @Test
    void constructor_shouldLeaveEnvironmentsNull_whenNull() {
        final GitHubEnvironmentsPage page = new GitHubEnvironmentsPage(0, null);

        assertThat(page.environments()).isNull();
    }
}
