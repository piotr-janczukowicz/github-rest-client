package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreatePreReceiveHookPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreatePreReceiveHookPayloadBuilder builder = base().name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenScriptNull() {
        final GitHubCreatePreReceiveHookPayloadBuilder builder = base().script(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("script");
    }

    @Test
    void constructor_shouldThrow_whenScriptRepositoryNull() {
        final GitHubCreatePreReceiveHookPayloadBuilder builder = base().scriptRepository(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("scriptRepository");
    }

    @Test
    void constructor_shouldThrow_whenEnvironmentNull() {
        final GitHubCreatePreReceiveHookPayloadBuilder builder = base().environment(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("environment");
    }

    @Test
    void constructor_shouldCopyMaps_whenCallerChangesThemLater() {
        final Map<String, Object> repository = new HashMap<>(Map.of("full_name", "DevIns/hooks-repo"));
        final Map<String, Object> environment = new HashMap<>(Map.of("id", 2));

        final GitHubCreatePreReceiveHookPayload payload =
                base().scriptRepository(repository).environment(environment).build();
        repository.put("other", "x");
        environment.clear();

        assertSoftly(softly -> {
            softly.assertThat(payload.scriptRepository()).containsOnlyKeys("full_name");
            softly.assertThat(payload.environment()).containsOnlyKeys("id");
        });
    }

    private static GitHubCreatePreReceiveHookPayloadBuilder base() {
        return GitHubCreatePreReceiveHookPayloadBuilder.builder(
                EnterpriseAdminCatalogFixtures.createPreReceiveHookPayload());
    }
}
