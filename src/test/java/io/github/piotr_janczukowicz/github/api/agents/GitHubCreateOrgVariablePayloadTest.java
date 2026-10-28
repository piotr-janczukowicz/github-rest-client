package io.github.piotr_janczukowicz.github.api.agents;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgVariablePayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateOrgVariablePayloadBuilder builder = GitHubCreateOrgVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createOrgVariablePayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenValueNull() {
        final GitHubCreateOrgVariablePayloadBuilder builder = GitHubCreateOrgVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createOrgVariablePayload())
                .value(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void constructor_shouldThrow_whenVisibilityNull() {
        final GitHubCreateOrgVariablePayloadBuilder builder = GitHubCreateOrgVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createOrgVariablePayload())
                .visibility(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("visibility");
    }

    @Test
    void constructor_shouldCopySelectedRepositoryIds_whenMutableList() {
        final GitHubCreateOrgVariablePayload source = AgentsCatalogFixtures.createOrgVariablePayload();
        final int size = source.selectedRepositoryIds().size();
        final List<Long> mutable = new ArrayList<>(source.selectedRepositoryIds());
        final GitHubCreateOrgVariablePayload sut = GitHubCreateOrgVariablePayloadBuilder.builder(source)
                .selectedRepositoryIds(mutable)
                .build();
        mutable.clear();

        assertThat(sut.selectedRepositoryIds()).hasSize(size);
    }

    @Test
    void constructor_shouldLeaveSelectedRepositoryIdsNull_whenNull() {
        final GitHubCreateOrgVariablePayloadBuilder builder = GitHubCreateOrgVariablePayloadBuilder.builder(
                        AgentsCatalogFixtures.createOrgVariablePayload())
                .selectedRepositoryIds(null);

        final GitHubCreateOrgVariablePayload sut = builder.build();

        assertThat(sut.selectedRepositoryIds()).isNull();
    }
}
