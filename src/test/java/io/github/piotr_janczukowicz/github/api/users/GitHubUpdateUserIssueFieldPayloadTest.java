package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateUserIssueFieldPayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenOptionsNull() {
        final GitHubUpdateUserIssueFieldPayload value = GitHubUpdateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.updateUserIssueFieldPayload())
                .options(null)
                .build();

        assertThat(value.options()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenOptionsGiven() {
        final List<GitHubUpdateUserIssueFieldOption> source =
                new ArrayList<>(List.of(UsersCatalogFixtures.updateUserIssueFieldOption()));
        final GitHubUpdateUserIssueFieldPayload value = GitHubUpdateUserIssueFieldPayloadBuilder.builder(
                        UsersCatalogFixtures.updateUserIssueFieldPayload())
                .options(source)
                .build();

        source.add(UsersCatalogFixtures.updateUserIssueFieldOption());

        assertThat(value.options()).hasSize(source.size() - 1);
    }
}
