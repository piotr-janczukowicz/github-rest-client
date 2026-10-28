package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceResourcesPageTest {

    @Test
    void constructor_shouldThrow_whenResourcesNull() {
        final GitHubCopilotSpaceResourcesPageBuilder builder = GitHubCopilotSpaceResourcesPageBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceResourcesPage())
                .resources(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("resources");
    }
}
