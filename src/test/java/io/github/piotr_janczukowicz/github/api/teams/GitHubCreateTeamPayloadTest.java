package io.github.piotr_janczukowicz.github.api.teams;

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
class GitHubCreateTeamPayloadTest {

    @Test
    void constructor_shouldCopyLists_whenMaintainersAndRepoNamesPresent() {
        final List<String> maintainers = new ArrayList<>(List.of("octocat"));
        final List<String> repoNames = new ArrayList<>(List.of("octocat/Hello-World"));

        final GitHubCreateTeamPayload body = new GitHubCreateTeamPayload(
                "Justice League", null, maintainers, repoNames, null, null, null, null, null);
        maintainers.add("other");
        repoNames.add("octocat/Spoon-Knife");

        assertSoftly(softly -> {
            softly.assertThat(body)
                    .hasNoNullFieldsOrPropertiesExcept(
                            "description",
                            "privacy",
                            "notificationSetting",
                            "parentTeamId",
                            "parentTeamSlug",
                            "ldapDn");
            softly.assertThat(body.maintainers()).containsExactly("octocat");
            softly.assertThat(body.repoNames()).containsExactly("octocat/Hello-World");
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
        return Stream.of(Arguments.of(
                (ConstructorCall)
                        () -> new GitHubCreateTeamPayload(null, null, null, null, null, null, null, null, null),
                NullPointerException.class,
                "name"));
    }

    @FunctionalInterface
    interface ConstructorCall {
        void run();
    }
}
