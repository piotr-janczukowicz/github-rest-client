package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePayloadTest {
    @Test
    void constructor_shouldLeaveFilesNull_whenNull() {
        final GitHubUpdatePayloadBuilder builder = GitHubUpdatePayloadBuilder.builder(
                        GistsCatalogFixtures.updatePayload())
                .files(null);

        final GitHubUpdatePayload result = builder.build();

        assertThat(result.files()).isNull();
    }

    @Test
    void constructor_shouldKeepNullFile_whenFileDeleted() {
        final Map<String, GitHubUpdateGistFile> files = new HashMap<>();
        files.put("hello.py", null);
        final GitHubUpdatePayloadBuilder builder = GitHubUpdatePayloadBuilder.builder(
                        GistsCatalogFixtures.updatePayload())
                .files(files);

        final GitHubUpdatePayload result = builder.build();

        assertThat(result.files()).containsOnlyKeys("hello.py").containsEntry("hello.py", null);
    }
}
