package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenBaseRoleNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .baseRole(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("baseRole");
    }

    @Test
    void constructor_shouldThrow_whenOwnerNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .owner(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldThrow_whenCreatorNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .creator(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("creator");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .createdAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .updatedAt(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    @Test
    void constructor_shouldThrow_whenHtmlUrlNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .htmlUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("htmlUrl");
    }

    @Test
    void constructor_shouldThrow_whenApiUrlNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .apiUrl(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("apiUrl");
    }

    @Test
    void constructor_shouldLeaveResourcesAttributesNull_whenNull() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .resourcesAttributes(null);

        final GitHubCopilotSpace sut = builder.build();

        assertThat(sut.resourcesAttributes()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenGeneralInstructionsTooLong() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .generalInstructions("x".repeat(4001));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("generalInstructions");
    }

    @Test
    void constructor_shouldAcceptGeneralInstructions_whenAtLimit() {
        final GitHubCopilotSpaceBuilder builder = GitHubCopilotSpaceBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpace())
                .generalInstructions("x".repeat(4000));

        final GitHubCopilotSpace sut = builder.build();

        assertThat(sut.generalInstructions()).hasSize(4000);
    }
}
