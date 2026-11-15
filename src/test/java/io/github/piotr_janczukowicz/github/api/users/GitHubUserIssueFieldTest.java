package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUserIssueFieldTest {

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubUserIssueFieldBuilder builder = GitHubUserIssueFieldBuilder.builder(
                        UsersCatalogFixtures.userIssueField())
                .nodeId(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubUserIssueFieldBuilder builder = GitHubUserIssueFieldBuilder.builder(
                        UsersCatalogFixtures.userIssueField())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDataTypeNull() {
        final GitHubUserIssueFieldBuilder builder = GitHubUserIssueFieldBuilder.builder(
                        UsersCatalogFixtures.userIssueField())
                .dataType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dataType");
    }
}
