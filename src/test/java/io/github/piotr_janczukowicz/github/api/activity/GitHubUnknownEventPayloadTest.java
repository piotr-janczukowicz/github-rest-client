package io.github.piotr_janczukowicz.github.api.activity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUnknownEventPayloadTest {

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUnknownEventPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }

    @Test
    void properties_shouldBeUnmodifiable_whenRead() {
        final GitHubUnknownEventPayload payload = new GitHubUnknownEventPayload(Map.of("action", "created"));

        final Throwable thrown = catchThrowable(() -> payload.properties().put("other", "value"));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }
}
