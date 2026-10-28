package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDraftItemPayloadTest {

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateDraftItemPayload(null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldAcceptOptionalBody_whenTitlePresent() {
        final GitHubCreateDraftItemPayload payload = new GitHubCreateDraftItemPayload("Draft", "Body");

        assertThat(payload.title()).isEqualTo("Draft");
        assertThat(payload.body()).isEqualTo("Body");
    }
}
