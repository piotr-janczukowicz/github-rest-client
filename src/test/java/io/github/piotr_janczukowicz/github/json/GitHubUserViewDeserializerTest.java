package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubUserView;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubUserViewDeserializerTest {

    @ParameterizedTest
    @ValueSource(strings = {"[]", "5", "\"x\"", "true"})
    void deserialize_shouldThrowDecodeException_whenRootIsNotObject(final String root) {
        final byte[] json = root.getBytes(StandardCharsets.UTF_8);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubUserView.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class);
    }
}
