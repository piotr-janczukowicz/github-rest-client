package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFieldTest {

    private static final Instant CREATED_AT = Instant.parse("2022-04-28T12:00:00Z");

    private static final String PROJECT_URL = "https://api.github.com/orgs/octocat/projectsV2/1";

    @Test
    void constructor_shouldCopyOptions_whenPresent() {
        final Instant createdAt = Instant.parse("2022-04-28T12:00:00Z");
        final GitHubProjectSingleSelectOption option = ProjectsCatalogFixtures.singleSelectOption();

        final GitHubProjectField field = new GitHubProjectField(
                123,
                null,
                null,
                "https://api.github.com/orgs/octocat/projectsV2/1",
                "Status",
                GitHubProjectFieldDataType.SINGLE_SELECT,
                List.of(option),
                null,
                createdAt,
                createdAt);

        assertThat(field.options()).containsExactly(option);
    }

    @Test
    void constructor_shouldKeepOptionsNull_whenAbsent() {
        final GitHubProjectField field = new GitHubProjectField(
                123,
                null,
                null,
                PROJECT_URL,
                "Team notes",
                GitHubProjectFieldDataType.TEXT,
                null,
                null,
                CREATED_AT,
                CREATED_AT);

        assertThat(field.options()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenProjectUrlNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectField(
                123,
                null,
                null,
                null,
                "Team notes",
                GitHubProjectFieldDataType.TEXT,
                null,
                null,
                CREATED_AT,
                CREATED_AT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("projectUrl");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectField(
                123,
                null,
                null,
                PROJECT_URL,
                null,
                GitHubProjectFieldDataType.TEXT,
                null,
                null,
                CREATED_AT,
                CREATED_AT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDataTypeNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectField(
                123, null, null, PROJECT_URL, "Team notes", null, null, null, CREATED_AT, CREATED_AT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dataType");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectField(
                123,
                null,
                null,
                PROJECT_URL,
                "Team notes",
                GitHubProjectFieldDataType.TEXT,
                null,
                null,
                null,
                CREATED_AT));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubProjectField(
                123,
                null,
                null,
                PROJECT_URL,
                "Team notes",
                GitHubProjectFieldDataType.TEXT,
                null,
                null,
                CREATED_AT,
                null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }
}
