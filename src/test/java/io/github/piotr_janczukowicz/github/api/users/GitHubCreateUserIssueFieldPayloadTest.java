package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateUserIssueFieldPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateUserIssueFieldPayloadBuilder builder = GitHubCreateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.createUserIssueFieldPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDataTypeNull() {
        final GitHubCreateUserIssueFieldPayloadBuilder builder = GitHubCreateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.createUserIssueFieldPayload())
                .dataType(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("dataType");
    }

    @Test
    void constructor_shouldAcceptNull_whenOptionsNull() {
        final GitHubCreateUserIssueFieldPayload value = GitHubCreateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.createUserIssueFieldPayload())
                .options(null)
                .build();

        assertThat(value.options()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenOptionsGiven() {
        final List<GitHubCreateUserIssueFieldOption> source =
                new ArrayList<>(List.of(UsersCatalogFixtures.createUserIssueFieldOption()));
        final GitHubCreateUserIssueFieldPayload value = GitHubCreateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.createUserIssueFieldPayload())
                .options(source)
                .build();

        source.add(UsersCatalogFixtures.createUserIssueFieldOption());

        assertThat(value.options()).hasSize(source.size() - 1);
    }
}
