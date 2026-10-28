package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubListAlertsForEnterpriseFilterTest {

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubListAlertsForEnterpriseFilter filter = GitHubListAlertsForEnterpriseFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.state()).isNull();
            softly.assertThat(filter.secretType()).isNull();
            softly.assertThat(filter.toQueryParameters()).isEmpty();
        });
    }

    @ParameterizedTest
    @MethodSource("partialFilters")
    void toQueryParameters_shouldOmitUnsetFields_whenCalled(
            final GitHubListAlertsForEnterpriseFilter filter, final Map<String, String> expected) {
        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubListAlertsForEnterpriseFilter filter = GitHubListAlertsForEnterpriseFilterBuilder.builder()
                .state(GitHubSecretScanningAlertState.OPEN)
                .secretType("mailchimp_api_key")
                .excludeSecretTypes("adafruit_io_key")
                .excludeProviders("github_secret_scanning")
                .providers("clojars")
                .resolution("false_positive,wont_fix")
                .assignee("octocat")
                .sort(GitHubSecretScanningAlertSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .validity("active,unknown")
                .isPubliclyLeaked(true)
                .isMultiRepo(false)
                .hideSecret(true)
                .isBypassed(false)
                .includedMetadata("owner-email,secret-id")
                .ownerEmailHash("abc123")
                .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("state", "open");
            softly.assertThat(query).containsEntry("secret_type", "mailchimp_api_key");
            softly.assertThat(query).containsEntry("exclude_secret_types", "adafruit_io_key");
            softly.assertThat(query).containsEntry("exclude_providers", "github_secret_scanning");
            softly.assertThat(query).containsEntry("providers", "clojars");
            softly.assertThat(query).containsEntry("resolution", "false_positive,wont_fix");
            softly.assertThat(query).containsEntry("assignee", "octocat");
            softly.assertThat(query).containsEntry("sort", "updated");
            softly.assertThat(query).containsEntry("direction", "desc");
            softly.assertThat(query).containsEntry("validity", "active,unknown");
            softly.assertThat(query).containsEntry("is_publicly_leaked", "true");
            softly.assertThat(query).containsEntry("is_multi_repo", "false");
            softly.assertThat(query).containsEntry("hide_secret", "true");
            softly.assertThat(query).containsEntry("is_bypassed", "false");
            softly.assertThat(query).containsEntry("included_metadata", "owner-email,secret-id");
            softly.assertThat(query).containsEntry("owner_email_hash", "abc123");
        });
    }

    private static Stream<Arguments> partialFilters() {
        return Stream.of(
                Arguments.of(
                        GitHubListAlertsForEnterpriseFilterBuilder.builder()
                                .state(GitHubSecretScanningAlertState.RESOLVED)
                                .build(),
                        Map.of("state", "resolved")),
                Arguments.of(
                        GitHubListAlertsForEnterpriseFilterBuilder.builder()
                                .assignee("*")
                                .build(),
                        Map.of("assignee", "*")),
                Arguments.of(
                        GitHubListAlertsForEnterpriseFilterBuilder.builder()
                                .sort(GitHubSecretScanningAlertSort.CREATED)
                                .direction(GitHubDirection.ASC)
                                .build(),
                        Map.of("sort", "created", "direction", "asc")));
    }
}
