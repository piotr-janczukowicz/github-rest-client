package io.github.piotr_janczukowicz.github.api.gitdatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateTreePayloadTest {

    @Test
    void constructor_shouldThrow_whenTreeNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCreateTreePayloadBuilder.builder(GitDatabaseCatalogFixtures.createTreePayload())
                        .tree(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("tree");
    }
}
