package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGistSimpleTest {
    @Test
    void constructor_shouldLeaveFilesNull_whenNull() {
        final GitHubGistSimpleBuilder builder = GitHubGistSimpleBuilder.builder(GistsCatalogFixtures.gistSimple())
                .files(null);

        final GitHubGistSimple result = builder.build();

        assertThat(result.files()).isNull();
    }

    @Test
    void constructor_shouldKeepNullFile_whenFileNull() {
        final Map<String, GitHubGistSimpleFile> files = new HashMap<>();
        files.put("README.md", null);
        final GitHubGistSimpleBuilder builder = GitHubGistSimpleBuilder.builder(GistsCatalogFixtures.gistSimple())
                .files(files);

        final GitHubGistSimple result = builder.build();

        assertThat(result.files()).containsOnlyKeys("README.md").containsEntry("README.md", null);
    }
}
