package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateEnvironmentPayloadTest {

    @Test
    void constructor_shouldCopyReviewers_whenPresent() {
        final GitHubCreateOrUpdateEnvironmentReviewerPayload reviewer =
                DeploymentsCatalogFixtures.createOrUpdateEnvironmentReviewerPayload();
        final GitHubCreateOrUpdateEnvironmentPayload payload = GitHubCreateOrUpdateEnvironmentPayloadBuilder.builder()
                .reviewers(List.of(reviewer))
                .build();

        assertThat(payload.reviewers()).containsExactly(reviewer);
    }

    @Test
    void constructor_shouldLeaveReviewersNull_whenNull() {
        final GitHubCreateOrUpdateEnvironmentPayload payload = GitHubCreateOrUpdateEnvironmentPayloadBuilder.builder(
                        DeploymentsCatalogFixtures.createOrUpdateEnvironmentPayload())
                .reviewers(null)
                .build();

        assertThat(payload.reviewers()).isNull();
    }
}
