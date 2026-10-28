package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateBlobPayloadTest {

    @Test
    void constructor_shouldThrow_whenContentNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateBlobPayloadBuilder.builder(GitDatabaseCatalogFixtures.createBlobPayload())
                        .content(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("content");
    }
}
