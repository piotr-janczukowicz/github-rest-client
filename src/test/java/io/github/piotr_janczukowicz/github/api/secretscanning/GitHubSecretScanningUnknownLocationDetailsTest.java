package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecretScanningUnknownLocationDetailsTest {

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningUnknownLocationDetails(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }

    @Test
    void properties_shouldBeUnmodifiable_whenRead() {
        final GitHubSecretScanningUnknownLocationDetails details =
                new GitHubSecretScanningUnknownLocationDetails(Map.of("gist_id", "abc"));

        final Map<String, Object> properties = details.properties();

        final Throwable thrown = catchThrowable(() -> properties.put("other", "value"));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
    }
}
