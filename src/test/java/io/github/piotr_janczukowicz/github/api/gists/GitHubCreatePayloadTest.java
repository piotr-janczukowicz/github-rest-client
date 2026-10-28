package io.github.piotr_janczukowicz.github.api.gists;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePayloadTest {
    @Test
    void constructor_shouldThrow_whenFilesNull() {
        final GitHubCreatePayloadBuilder builder = GitHubCreatePayloadBuilder.builder(
                        GistsCatalogFixtures.createPayload())
                .files(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("files");
    }
}
