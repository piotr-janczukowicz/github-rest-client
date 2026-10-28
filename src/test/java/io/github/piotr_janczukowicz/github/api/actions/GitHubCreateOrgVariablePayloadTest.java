package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgVariablePayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final String value = "value-sample";
        final GitHubOrgVariableVisibility visibility = GitHubOrgVariableVisibility.ALL;
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown = catchThrowable(
                () -> new GitHubCreateOrgVariablePayload(null, value, visibility, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenValueNull() {
        final String name = "name-sample";
        final GitHubOrgVariableVisibility visibility = GitHubOrgVariableVisibility.ALL;
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrgVariablePayload(name, null, visibility, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenVisibilityNull() {
        final String name = "name-sample";
        final String value = "value-sample";
        final List<Long> selectedRepositoryIds = List.of(1L);

        final Throwable thrown =
                catchThrowable(() -> new GitHubCreateOrgVariablePayload(name, value, null, selectedRepositoryIds));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubCreateOrgVariablePayload payload = GitHubCreateOrgVariablePayloadBuilder.builder(
                        ActionsCatalogFixtures.createOrgVariablePayload())
                .selectedRepositoryIds(null)
                .build();

        assertThat(payload.selectedRepositoryIds()).isNull();
    }
}
