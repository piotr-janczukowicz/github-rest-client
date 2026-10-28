package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.Assertions.entry;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCheckPermissionsForDevcontainerFilterTest {

    @Test
    void toQueryParameters_shouldIncludeRefAndDevcontainerPath_whenCalled() {
        final GitHubCheckPermissionsForDevcontainerFilter filter =
                CodespacesCatalogFixtures.checkPermissionsForDevcontainerFilter();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query)
                .containsExactly(entry("ref", "main"), entry("devcontainer_path", ".devcontainer/devcontainer.json"));
    }

    @Test
    void of_shouldThrow_whenRefNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCheckPermissionsForDevcontainerFilter.of(null, "path"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ref");
    }
}
