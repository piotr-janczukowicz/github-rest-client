package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotRepositoryAccessDetailsTest {

    @Test
    void constructor_shouldLeaveAccessibleRepositoriesNull_whenNull() {
        final GitHubDependabotRepositoryAccessDetailsBuilder builder =
                GitHubDependabotRepositoryAccessDetailsBuilder.builder(
                                DependabotCatalogFixtures.dependabotRepositoryAccessDetails())
                        .accessibleRepositories(null);

        final GitHubDependabotRepositoryAccessDetails sut = builder.build();

        assertThat(sut.accessibleRepositories()).isNull();
    }
}
