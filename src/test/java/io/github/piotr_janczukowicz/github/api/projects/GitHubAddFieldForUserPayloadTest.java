package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubAddFieldForUserPayloadTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void decode_shouldReturnBasic_whenTextField() {
        final byte[] json = "{\"name\":\"Notes\",\"data_type\":\"text\"}".getBytes(StandardCharsets.UTF_8);

        final GitHubAddFieldForUserPayload decoded = CODEC.decode(json, GitHubAddFieldForUserPayload.class);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isInstanceOf(GitHubAddProjectFieldPayload.Basic.class);
            softly.assertThat(((GitHubAddProjectFieldPayload.Basic) decoded).name())
                    .isEqualTo("Notes");
        });
    }

    @Test
    void decode_shouldReturnSingleSelect_whenOptionsPresent() {
        final byte[] json =
                "{\"name\":\"Status\",\"data_type\":\"single_select\",\"single_select_options\":[{\"name\":\"Todo\"}]}"
                        .getBytes(StandardCharsets.UTF_8);

        final GitHubAddFieldForUserPayload decoded = CODEC.decode(json, GitHubAddFieldForUserPayload.class);

        assertThat(decoded).isInstanceOf(GitHubAddProjectFieldPayload.SingleSelect.class);
    }

    @Test
    void decode_shouldReturnIteration_whenConfigurationPresent() {
        final byte[] json = """
                {"name":"Sprint","data_type":"iteration","iteration_configuration":{"start_date":"2022-04-28","duration":14}}
                """.getBytes(StandardCharsets.UTF_8);

        final GitHubAddFieldForUserPayload decoded = CODEC.decode(json, GitHubAddFieldForUserPayload.class);

        assertThat(decoded).isInstanceOf(GitHubAddProjectFieldPayload.Iteration.class);
    }

    @Test
    void decode_shouldThrow_whenIssueFieldIdPresent() {
        final byte[] json = "{\"issue_field_id\":99}".getBytes(StandardCharsets.UTF_8);

        final Throwable thrown = catchThrowable(() -> CODEC.decode(json, GitHubAddFieldForUserPayload.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .cause()
                .isInstanceOf(DatabindException.class)
                .hasMessageContaining("issue_field_id");
    }

    @Test
    void basic_shouldCreatePayload_whenCalled() {
        final GitHubAddProjectFieldPayload.Basic payload =
                GitHubAddFieldForUserPayload.basic("Notes", GitHubProjectFieldDataType.TEXT);

        assertThat(payload.name()).isEqualTo("Notes");
    }

    @Test
    void singleSelect_shouldCreatePayload_whenCalled() {
        final GitHubAddProjectFieldPayload.SingleSelect payload =
                GitHubAddFieldForUserPayload.singleSelect("Status", List.of());

        assertThat(payload.singleSelectOptions()).isEmpty();
    }

    @Test
    void iteration_shouldCreatePayload_whenCalled() {
        final GitHubProjectFieldIterationConfiguration configuration =
                new GitHubProjectFieldIterationConfiguration("2022-04-28", 14, null);

        final GitHubAddProjectFieldPayload.Iteration payload =
                GitHubAddFieldForUserPayload.iteration("Sprint", configuration);

        assertThat(payload.iterationConfiguration()).isEqualTo(configuration);
    }
}
