package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.projects.GitHubAddProjectFieldPayload;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonDecodeException;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.DatabindException;

@UnitTest
class GitHubAddProjectFieldPayloadDeserializerTest {

    @Test
    void deserialize_shouldReturnIssueField_whenIssueFieldIdPresent() {
        final byte[] json = "{\"issue_field_id\":12}".getBytes(StandardCharsets.UTF_8);

        final GitHubAddProjectFieldPayload payload =
                GitHubJsonCodecs.defaults().decode(json, GitHubAddProjectFieldPayload.class);

        assertThat(payload).isInstanceOf(GitHubAddProjectFieldPayload.IssueField.class);
    }

    @Test
    void deserialize_shouldReturnSingleSelect_whenOptionsPresent() {
        final byte[] json = "{\"name\":\"Status\",\"single_select_options\":[{\"name\":\"Todo\"}]}"
                .getBytes(StandardCharsets.UTF_8);

        final GitHubAddProjectFieldPayload payload =
                GitHubJsonCodecs.defaults().decode(json, GitHubAddProjectFieldPayload.class);

        assertThat(payload).isInstanceOf(GitHubAddProjectFieldPayload.SingleSelect.class);
    }

    @Test
    void deserialize_shouldReturnIteration_whenConfigurationPresent() {
        final byte[] json =
                "{\"name\":\"Sprint\",\"iteration_configuration\":{\"start_date\":\"2022-04-28\",\"duration\":14}}"
                        .getBytes(StandardCharsets.UTF_8);

        final GitHubAddProjectFieldPayload payload =
                GitHubJsonCodecs.defaults().decode(json, GitHubAddProjectFieldPayload.class);

        assertThat(payload).isInstanceOf(GitHubAddProjectFieldPayload.Iteration.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenDataTypeUnknown() {
        final byte[] json = "{\"name\":\"Team notes\",\"data_type\":\"hologram\"}".getBytes(StandardCharsets.UTF_8);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubAddProjectFieldPayload.class));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(DatabindException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenDataTypeIsNotBasic() {
        final byte[] json = "{\"name\":\"Status\",\"data_type\":\"single_select\"}".getBytes(StandardCharsets.UTF_8);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown = catchThrowable(() -> codec.decode(json, GitHubAddProjectFieldPayload.class));

        assertThat(thrown)
                .isInstanceOf(JsonDecodeException.class)
                .hasMessageContaining("IllegalArgumentException")
                .cause()
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deserialize_shouldThrowDecodeException_whenOptionIsNull() {
        final byte[] json = "{\"name\":\"Status\",\"single_select_options\":[null]}".getBytes(StandardCharsets.UTF_8);

        final JsonCodec codec = GitHubJsonCodecs.defaults();

        final Throwable thrown =
                catchThrowable(() -> codec.decode(json, JsonTypes.of(GitHubAddProjectFieldPayload.class)));

        assertThat(thrown).isInstanceOf(JsonDecodeException.class).cause().isInstanceOf(NullPointerException.class);
    }

    @Test
    void deserialize_shouldReturnBasic_whenNameAndDataType() {
        final byte[] json = "{\"name\":\"Team notes\",\"data_type\":\"text\"}".getBytes(StandardCharsets.UTF_8);

        final GitHubAddProjectFieldPayload payload =
                GitHubJsonCodecs.defaults().decode(json, GitHubAddProjectFieldPayload.class);

        assertThat(payload).isInstanceOf(GitHubAddProjectFieldPayload.Basic.class);
    }
}
