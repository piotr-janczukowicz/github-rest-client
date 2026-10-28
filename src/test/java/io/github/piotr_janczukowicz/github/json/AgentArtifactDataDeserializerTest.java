package io.github.piotr_janczukowicz.github.json;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAutomationTasksTaskArtifactData;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAutomationTasksTaskArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAutomationTasksTaskArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agentautomations.GitHubListAutomationTasksTaskArtifactDataUnknown;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubCreateTaskInRepoResponseArtifactData;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubCreateTaskInRepoResponseArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubCreateTaskInRepoResponseArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubCreateTaskInRepoResponseArtifactDataUnknown;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByIdResponseArtifactData;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByIdResponseArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByIdResponseArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByIdResponseArtifactDataUnknown;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByRepoAndIdResponseArtifactData;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByRepoAndIdResponseArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByRepoAndIdResponseArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubGetTaskByRepoAndIdResponseArtifactDataUnknown;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksForRepoTaskArtifactData;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksForRepoTaskArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksForRepoTaskArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksForRepoTaskArtifactDataUnknown;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksTaskArtifactData;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksTaskArtifactDataById;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksTaskArtifactDataByRef;
import io.github.piotr_janczukowicz.github.api.agenttasks.GitHubListTasksTaskArtifactDataUnknown;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class AgentArtifactDataDeserializerTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();
    private static final String BY_ID = "{\"id\":7,\"global_id\":\"g\"}";
    private static final String BY_REF = "{\"head_ref\":\"h\",\"base_ref\":\"b\"}";
    private static final String UNKNOWN = "{\"new_field\":\"x\"}";

    private static Stream<Arguments> unions() {
        return Stream.of(
                Arguments.of(
                        GitHubGetTaskByIdResponseArtifactData.class,
                        GitHubGetTaskByIdResponseArtifactDataById.class,
                        GitHubGetTaskByIdResponseArtifactDataByRef.class,
                        GitHubGetTaskByIdResponseArtifactDataUnknown.class),
                Arguments.of(
                        GitHubCreateTaskInRepoResponseArtifactData.class,
                        GitHubCreateTaskInRepoResponseArtifactDataById.class,
                        GitHubCreateTaskInRepoResponseArtifactDataByRef.class,
                        GitHubCreateTaskInRepoResponseArtifactDataUnknown.class),
                Arguments.of(
                        GitHubGetTaskByRepoAndIdResponseArtifactData.class,
                        GitHubGetTaskByRepoAndIdResponseArtifactDataById.class,
                        GitHubGetTaskByRepoAndIdResponseArtifactDataByRef.class,
                        GitHubGetTaskByRepoAndIdResponseArtifactDataUnknown.class),
                Arguments.of(
                        GitHubListTasksForRepoTaskArtifactData.class,
                        GitHubListTasksForRepoTaskArtifactDataById.class,
                        GitHubListTasksForRepoTaskArtifactDataByRef.class,
                        GitHubListTasksForRepoTaskArtifactDataUnknown.class),
                Arguments.of(
                        GitHubListTasksTaskArtifactData.class,
                        GitHubListTasksTaskArtifactDataById.class,
                        GitHubListTasksTaskArtifactDataByRef.class,
                        GitHubListTasksTaskArtifactDataUnknown.class),
                Arguments.of(
                        GitHubListAutomationTasksTaskArtifactData.class,
                        GitHubListAutomationTasksTaskArtifactDataById.class,
                        GitHubListAutomationTasksTaskArtifactDataByRef.class,
                        GitHubListAutomationTasksTaskArtifactDataUnknown.class));
    }

    @ParameterizedTest
    @MethodSource("unions")
    void decode_shouldReturnById_whenDataHasIds(
            final Class<?> union, final Class<?> byId, final Class<?> byRef, final Class<?> unknown) {
        final byte[] json = BY_ID.getBytes(StandardCharsets.UTF_8);

        final Object decoded = CODEC.decode(json, union);

        assertThat(decoded).isInstanceOf(byId);
    }

    @ParameterizedTest
    @MethodSource("unions")
    void decode_shouldReturnByRef_whenDataHasRefs(
            final Class<?> union, final Class<?> byId, final Class<?> byRef, final Class<?> unknown) {
        final byte[] json = BY_REF.getBytes(StandardCharsets.UTF_8);

        final Object decoded = CODEC.decode(json, union);

        assertThat(decoded).isInstanceOf(byRef);
    }

    @ParameterizedTest
    @MethodSource("unions")
    @SuppressWarnings("unchecked")
    void decode_shouldReturnUnknown_whenDataMatchesNoVariant(
            final Class<?> union, final Class<?> byId, final Class<?> byRef, final Class<?> unknown) {
        final byte[] json = UNKNOWN.getBytes(StandardCharsets.UTF_8);

        final Object decoded = CODEC.decode(json, union);

        assertThat(decoded).isInstanceOf(unknown);
        assertThat(CODEC.decode(CODEC.encode(decoded), Map.class)).isEqualTo(Map.of("new_field", "x"));
    }

    @Test
    void decode_shouldKeepTheKnownItems_whenOneListItemIsUnknown() {
        final byte[] json = ("[" + BY_ID + "," + UNKNOWN + "," + BY_REF + "]").getBytes(StandardCharsets.UTF_8);

        final List<GitHubListTasksTaskArtifactData> decoded =
                CODEC.decode(json, JsonTypes.listOf(GitHubListTasksTaskArtifactData.class));

        assertThat(decoded)
                .hasSize(3)
                .hasOnlyElementsOfTypes(
                        GitHubListTasksTaskArtifactDataById.class,
                        GitHubListTasksTaskArtifactDataUnknown.class,
                        GitHubListTasksTaskArtifactDataByRef.class);
    }
}
