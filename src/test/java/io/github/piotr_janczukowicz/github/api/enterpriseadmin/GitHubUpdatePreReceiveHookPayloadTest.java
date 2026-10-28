package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdatePreReceiveHookPayloadTest {

    @Test
    void constructor_shouldCopyMaps_whenCallerChangesThemLater() {
        final Map<String, Object> repository = new HashMap<>(Map.of("full_name", "DevIns/hooks-repo"));
        final Map<String, Object> environment = new HashMap<>(Map.of("id", 2));

        final GitHubUpdatePreReceiveHookPayload payload = GitHubUpdatePreReceiveHookPayloadBuilder.builder()
                .scriptRepository(repository)
                .environment(environment)
                .build();
        repository.put("other", "x");
        environment.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.scriptRepository()).containsOnlyKeys("full_name");
            softly.assertThat(payload.environment()).containsOnlyKeys("id");
        });
    }

    @Test
    void constructor_shouldKeepMapsNull_whenNotGiven() {
        final GitHubUpdatePreReceiveHookPayload payload =
                GitHubUpdatePreReceiveHookPayloadBuilder.builder().build();

        assertSoftly(softly -> {
            softly.assertThat(payload.scriptRepository()).isNull();
            softly.assertThat(payload.environment()).isNull();
        });
    }
}
