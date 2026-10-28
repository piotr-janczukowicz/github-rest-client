package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateAutolinkPayloadTest {

    @Test
    void constructor_shouldThrow_whenKeyPrefixNull() {
        final GitHubCreateAutolinkPayloadBuilder builder = GitHubCreateAutolinkPayloadBuilder.builder()
                .keyPrefix(null)
                .urlTemplate("https://example.com/TICKET?query=<num>");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("keyPrefix");
    }

    @Test
    void constructor_shouldThrow_whenUrlTemplateNull() {
        final GitHubCreateAutolinkPayloadBuilder builder = GitHubCreateAutolinkPayloadBuilder.builder()
                .keyPrefix("TICKET-")
                .urlTemplate(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("urlTemplate");
    }
}
