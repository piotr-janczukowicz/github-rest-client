package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubHookDeliveryResponseMessageTest {

    @Test
    void constructor_shouldLeaveHeadersNull_whenHeadersNull() {
        final GitHubHookDeliveryResponseMessage message =
                new GitHubHookDeliveryResponseMessage(null, OptionalNull.of("ok"));

        assertThat(message.headers()).isNull();
    }

    @Test
    void constructor_shouldLeaveHeadersValueNull_whenHeadersOfNull() {
        final GitHubHookDeliveryResponseMessage message =
                new GitHubHookDeliveryResponseMessage(OptionalNull.ofNull(), OptionalNull.of("ok"));

        assertThat(message.headers().value()).isNull();
    }

    @Test
    void constructor_shouldKeepNullValue_whenHeadersContainNull() {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("X-Null", null);

        final GitHubHookDeliveryResponseMessage message =
                new GitHubHookDeliveryResponseMessage(OptionalNull.of(headers), OptionalNull.of("ok"));

        assertThat(message.headers().value()).containsEntry("X-Null", null);
    }
}
