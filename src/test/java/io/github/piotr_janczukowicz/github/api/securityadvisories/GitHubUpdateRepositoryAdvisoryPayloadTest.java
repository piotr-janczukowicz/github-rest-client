package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubUpdateRepositoryAdvisoryPayloadTest {

    @Test
    void constructor_shouldCopyLists_whenPresent() {
        final List<GitHubRepositoryAdvisoryVulnerabilityPayload> vulnerabilities =
                new ArrayList<>(List.of(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryVulnerabilityPayload()));
        final List<String> cweIds = new ArrayList<>(List.of("CWE-79"));
        final List<GitHubRepositoryAdvisoryCreditPayload> credits = new ArrayList<>(List.of(
                new GitHubRepositoryAdvisoryCreditPayload("octocat", GitHubSecurityAdvisoryCreditType.REPORTER)));
        final List<String> collaboratingUsers = new ArrayList<>(List.of("octocat"));
        final List<String> collaboratingTeams = new ArrayList<>(List.of("security"));

        final GitHubUpdateRepositoryAdvisoryPayload payload = new GitHubUpdateRepositoryAdvisoryPayload(
                null,
                null,
                null,
                vulnerabilities,
                cweIds,
                credits,
                null,
                null,
                null,
                collaboratingUsers,
                collaboratingTeams);
        vulnerabilities.add(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryVulnerabilityPayload());
        cweIds.add("CWE-20");
        credits.add(new GitHubRepositoryAdvisoryCreditPayload("monalisa", GitHubSecurityAdvisoryCreditType.ANALYST));
        collaboratingUsers.add("monalisa");
        collaboratingTeams.add("developers");

        assertSoftly(softly -> {
            softly.assertThat(payload.vulnerabilities()).hasSize(1);
            softly.assertThat(payload.cweIds()).containsExactly("CWE-79");
            softly.assertThat(payload.credits()).hasSize(1);
            softly.assertThat(payload.collaboratingUsers()).containsExactly("octocat");
            softly.assertThat(payload.collaboratingTeams()).containsExactly("security");
        });
    }

    @Test
    void constructor_shouldAccept_whenSeverityAndCvssBothSet() {
        final GitHubUpdateRepositoryAdvisoryPayload payload = new GitHubUpdateRepositoryAdvisoryPayload(
                null,
                null,
                null,
                null,
                null,
                null,
                GitHubRepositoryAdvisorySeverity.HIGH,
                "CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H",
                null,
                null,
                null);

        assertSoftly(softly -> {
            softly.assertThat(payload.severity()).isEqualTo(GitHubRepositoryAdvisorySeverity.HIGH);
            softly.assertThat(payload.cvssVectorString()).isEqualTo("CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H");
        });
    }

    @ParameterizedTest
    @MethodSource("invalidConstructors")
    void constructor_shouldThrow_whenInvalid(
            final ConstructorCall call, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(call::run);

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidConstructors() {
        return Stream.of(
                Arguments.of(
                        (ConstructorCall) () -> new GitHubUpdateRepositoryAdvisoryPayload(
                                "a".repeat(1025), null, null, null, null, null, null, null, null, null, null),
                        IllegalArgumentException.class,
                        "summary"),
                Arguments.of(
                        (ConstructorCall) () -> new GitHubUpdateRepositoryAdvisoryPayload(
                                null, "a".repeat(65_536), null, null, null, null, null, null, null, null, null),
                        IllegalArgumentException.class,
                        "description"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
