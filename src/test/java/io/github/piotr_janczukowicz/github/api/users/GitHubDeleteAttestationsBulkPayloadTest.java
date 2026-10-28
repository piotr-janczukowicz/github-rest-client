package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubDeleteAttestationsBulkPayloadTest {

    @Test
    void bySubjectDigests_shouldEncodeOnlySubjectDigests_whenCalled() {
        final GitHubDeleteAttestationsBulkPayload body =
                GitHubDeleteAttestationsBulkPayload.bySubjectDigests(List.of("sha256:abc123", "sha512:def456"));

        final byte[] json = GitHubJsonCodecs.defaults().encode(body);

        assertThat(new String(json, StandardCharsets.UTF_8))
                .isEqualTo("{\"subject_digests\":[\"sha256:abc123\",\"sha512:def456\"]}");
    }

    @Test
    void byAttestationIds_shouldEncodeOnlyAttestationIds_whenCalled() {
        final GitHubDeleteAttestationsBulkPayload body =
                GitHubDeleteAttestationsBulkPayload.byAttestationIds(List.of(111L, 222L));

        final byte[] json = GitHubJsonCodecs.defaults().encode(body);

        assertThat(new String(json, StandardCharsets.UTF_8)).isEqualTo("{\"attestation_ids\":[111,222]}");
    }

    @Test
    void constructor_shouldCopyLists_whenAttestationIdsPresent() {
        final List<Long> attestationIds = new ArrayList<>(List.of(111L));

        final GitHubDeleteAttestationsBulkPayload payload =
                GitHubDeleteAttestationsBulkPayload.byAttestationIds(attestationIds);
        attestationIds.add(222L);

        assertThat(payload.attestationIds()).containsExactly(111L);
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall)
                                () -> new GitHubDeleteAttestationsBulkPayload(List.of("sha256:abc"), List.of(1L)),
                        IllegalArgumentException.class,
                        "exactly one"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubDeleteAttestationsBulkPayload(null, null),
                        IllegalArgumentException.class,
                        "exactly one"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubDeleteAttestationsBulkPayload.bySubjectDigests(List.of()),
                        IllegalArgumentException.class,
                        "subjectDigests"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubDeleteAttestationsBulkPayload.byAttestationIds(List.of()),
                        IllegalArgumentException.class,
                        "attestationIds"),
                Arguments.of(
                        (ConstructorCall) () -> GitHubDeleteAttestationsBulkPayload.bySubjectDigests(
                                Collections.nCopies(1025, "sha256:abc")),
                        IllegalArgumentException.class,
                        "subjectDigests"),
                Arguments.of(
                        (ConstructorCall) () ->
                                GitHubDeleteAttestationsBulkPayload.byAttestationIds(Collections.nCopies(1025, 1L)),
                        IllegalArgumentException.class,
                        "attestationIds"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
