package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRuleFilePathRestrictionParametersTest {

    @Test
    void constructor_shouldCopyIgnoredFilePaths_whenGiven() {
        final List<String> source = new ArrayList<>(List.of("keep.md"));
        final GitHubRepositoryRuleFilePathRestrictionParameters parameters =
                new GitHubRepositoryRuleFilePathRestrictionParameters(List.of("docs/"), source);

        source.add("other.md");

        assertThat(parameters.ignoredFilePaths()).containsExactly("keep.md");
    }

    @Test
    void constructor_shouldLeaveIgnoredFilePathsNull_whenNull() {
        final GitHubRepositoryRuleFilePathRestrictionParameters parameters =
                GitHubRepositoryRuleFilePathRestrictionParametersBuilder.builder(
                                RepositoriesCatalogFixtures.filePathRestrictionParameters())
                        .ignoredFilePaths(null)
                        .build();

        assertThat(parameters.ignoredFilePaths()).isNull();
    }
}
