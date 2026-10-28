package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTransferPayloadTest {

    @Test
    void constructor_shouldThrow_whenNewOwnerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubTransferPayload(null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("newOwner");
    }

    @Test
    void constructor_shouldCopyTeamIds_whenTeamIdsProvided() {
        final GitHubTransferPayload payload = new GitHubTransferPayload("github", "renamed", List.of(1L, 2L));

        assertThat(payload.teamIds()).containsExactly(1L, 2L);
    }

    @Test
    void constructor_shouldLeaveTeamIdsNull_whenNull() {
        final GitHubTransferPayload payload = GitHubTransferPayloadBuilder.builder(
                        RepositoriesCatalogFixtures.transferRepositoryPayload())
                .teamIds(null)
                .build();

        assertThat(payload.teamIds()).isNull();
    }
}
