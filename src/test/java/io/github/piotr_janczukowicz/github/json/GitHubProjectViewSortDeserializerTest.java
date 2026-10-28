package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectViewSort;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectViewSortDirection;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubProjectViewSortDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldMatchTuple_whenTwoElements() {
        final GitHubProjectViewSort decoded = decode("[12,\"asc\"]");

        assertThat(decoded).isEqualTo(new GitHubProjectViewSort(12, GitHubProjectViewSortDirection.ASC));
    }

    @ParameterizedTest
    @ValueSource(
            strings = {
                "[12]",
                "[]",
                "[12,\"asc\",true]",
                "{}",
                "[\"12\",\"asc\"]",
                "[12,1]",
                "[12,null]",
                "[null,\"asc\"]",
                "[1.5,\"asc\"]"
            })
    void deserialize_shouldThrow_whenTupleIsNotAnIntegerAndADirection(final String json) {
        final byte[] bytes = json.getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubProjectViewSort.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("two-element");
    }

    @Test
    void deserialize_shouldReturnUnknownDirection_whenDirectionIsNotKnown() {
        final GitHubProjectViewSort decoded = decode("[12,\"sideways\"]");

        assertThat(decoded).isEqualTo(new GitHubProjectViewSort(12, GitHubProjectViewSortDirection.UNKNOWN));
    }

    private static GitHubProjectViewSort decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubProjectViewSort.class);
    }
}
