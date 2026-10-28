package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBranchProtectionPayloadTest {

    @Test
    void constructor_shouldThrow_whenRequiredStatusChecksNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateBranchProtectionPayload(
                null,
                OptionalNull.of(true),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("requiredStatusChecks");
    }

    @Test
    void constructor_shouldAcceptJsonNull_whenRequiredFieldsUseOfNull() {
        final GitHubUpdateBranchProtectionPayload payload = new GitHubUpdateBranchProtectionPayload(
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                null,
                null,
                null,
                null,
                null,
                null,
                null);

        assertThat(payload.requiredStatusChecks().value()).isNull();
        assertThat(payload.enforceAdmins().value()).isNull();
    }

    @Test
    void encode_shouldWriteExplicitNull_whenAllowForcePushesIsOfNull() {
        final GitHubUpdateBranchProtectionPayload payload = new GitHubUpdateBranchProtectionPayload(
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                OptionalNull.ofNull(),
                null,
                OptionalNull.ofNull(),
                null,
                null,
                null,
                null,
                null);

        final String json = new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8);

        assertThat(json).contains("\"allow_force_pushes\":null");
    }
}
