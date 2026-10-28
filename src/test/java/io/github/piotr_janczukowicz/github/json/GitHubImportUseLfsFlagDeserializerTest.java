package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.migrations.GitHubImport;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubImportUseLfsFlagDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnTrue_whenJsonTrue() {
        final GitHubImport decoded = decode("import-use-lfs-true.json");

        assertThat(decoded.useLfs()).isTrue();
    }

    @Test
    void decode_shouldReturnFalse_whenJsonFalse() {
        final GitHubImport decoded = decode("import-use-lfs-false.json");

        assertThat(decoded.useLfs()).isFalse();
    }

    @Test
    void decode_shouldReturnTrue_whenOptIn() {
        final GitHubImport decoded = decode("import-use-lfs-opt-in.json");

        assertThat(decoded.useLfs()).isTrue();
    }

    @Test
    void decode_shouldReturnFalse_whenOptOut() {
        final GitHubImport decoded = decode("import-use-lfs-opt-out.json");

        assertThat(decoded.useLfs()).isFalse();
    }

    @Test
    void decode_shouldReturnNull_whenUndecided() {
        final GitHubImport decoded = decode("import-use-lfs-undecided.json");

        assertThat(decoded.useLfs()).isNull();
    }

    @Test
    void decode_shouldThrow_whenUnknownString() {
        final byte[] json =
                FileUtils.readBytes(GitHubImportUseLfsFlagDeserializerTest.class, "import-use-lfs-unknown-string.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubImport.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("use_lfs");
    }

    @Test
    void decode_shouldThrow_whenJsonNumber() {
        final byte[] json =
                FileUtils.readBytes(GitHubImportUseLfsFlagDeserializerTest.class, "import-use-lfs-number.json");

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubImport.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("boolean");
    }

    private static GitHubImport decode(final String fileName) {
        return CODEC.decode(
                FileUtils.readBytes(GitHubImportUseLfsFlagDeserializerTest.class, fileName), GitHubImport.class);
    }
}
