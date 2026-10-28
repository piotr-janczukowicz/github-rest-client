package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentDirectory;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentFile;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentResult;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentSubmodule;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentSymlink;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubContentResultDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldThrow_whenTypeUnknown() {
        final byte[] json = "{\"type\":\"unknown\"}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubContentResult.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("Unknown GitHub content type");
    }

    @Test
    void decode_shouldReturnDirectory_whenJsonIsArray() {
        final byte[] json = FileUtils.readBytes(GitHubContentResultDeserializerTest.class, "content-directory.json");

        final GitHubContentResult decoded = CODEC.decode(json, GitHubContentResult.class);

        assertThat(decoded).isInstanceOf(GitHubContentDirectory.class);
    }

    @Test
    void decode_shouldReturnFile_whenTypeIsFile() {
        final GitHubContentResult decoded = decodeRepositoryFixture("content-file.json");

        assertThat(decoded).isInstanceOf(GitHubContentFile.class);
    }

    @Test
    void decode_shouldReturnSymlink_whenTypeIsSymlink() {
        final GitHubContentResult decoded = decodeRepositoryFixture("content-symlink.json");

        assertThat(decoded).isInstanceOf(GitHubContentSymlink.class);
    }

    @Test
    void decode_shouldReturnSubmodule_whenTypeIsSubmodule() {
        final GitHubContentResult decoded = decodeRepositoryFixture("content-submodule.json");

        assertThat(decoded).isInstanceOf(GitHubContentSubmodule.class);
    }

    private static GitHubContentResult decodeRepositoryFixture(final String name) {
        final byte[] json = FileUtils.readBytes(GitHubContentFile.class, name);
        return CODEC.decode(json, GitHubContentResult.class);
    }
}
