package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.branches.GitHubTeamAccessRestrictionsPayload;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubTeamAccessRestrictionsPayloadDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void deserialize_shouldReturnTeamSlugs_whenJsonIsArray() {
        final GitHubTeamAccessRestrictionsPayload decoded = decode("[\"justice-league\"]");

        assertThat(decoded.teamSlugs()).containsExactly("justice-league");
    }

    @Test
    void deserialize_shouldReturnTeams_whenJsonIsObject() {
        final GitHubTeamAccessRestrictionsPayload decoded = decode("{\"teams\":[\"justice-league\"]}");

        assertThat(decoded.teams()).containsExactly("justice-league");
    }

    @Test
    void encode_shouldWriteArray_whenTeamSlugsPresent() {
        final byte[] json = CODEC.encode(GitHubTeamAccessRestrictionsPayload.withTeamSlugs(List.of("justice-league")));

        assertThat(new String(json, StandardCharsets.UTF_8)).isEqualTo("[\"justice-league\"]");
    }

    @Test
    void encode_shouldWriteObject_whenTeamsPresent() {
        final byte[] json = CODEC.encode(GitHubTeamAccessRestrictionsPayload.withTeams(List.of("justice-league")));

        assertThat(new String(json, StandardCharsets.UTF_8)).isEqualTo("{\"teams\":[\"justice-league\"]}");
    }

    @Test
    void deserialize_shouldThrow_whenObjectOmitsTeams() {
        final byte[] bytes = "{}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(bytes, GitHubTeamAccessRestrictionsPayload.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("teams");
    }

    private static GitHubTeamAccessRestrictionsPayload decode(final String json) {
        return CODEC.decode(json.getBytes(StandardCharsets.UTF_8), GitHubTeamAccessRestrictionsPayload.class);
    }
}
