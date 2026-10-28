package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsPersonalAccessTokensFilterTest {

    private static final Instant LAST_USED_BEFORE = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant LAST_USED_AFTER = Instant.parse("2023-01-01T00:00:00Z");

    @Test
    void empty_shouldReturnFilterWithNoQueryParameters_whenCalled() {
        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                GitHubOrganizationsPersonalAccessTokensFilter.empty();

        assertThat(filter.toQueryValues()).isEmpty();
    }

    @Test
    void toQueryValues_shouldIncludeAllFilters_whenSet() {
        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                GitHubOrganizationsPersonalAccessTokensFilterBuilder.builder()
                        .sort(GitHubOrganizationPersonalAccessTokenSort.CREATED_AT)
                        .direction(GitHubDirection.DESC)
                        .owner(List.of("octocat", "hubot"))
                        .repository("Hello-World")
                        .permission("metadata")
                        .lastUsedBefore(LAST_USED_BEFORE)
                        .lastUsedAfter(LAST_USED_AFTER)
                        .tokenId(List.of("1", "2"))
                        .build();
        final Map<String, List<String>> query = filter.toQueryValues();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("sort", List.of("created_at"));
            softly.assertThat(query).containsEntry("direction", List.of("desc"));
            softly.assertThat(query).containsEntry("owner[]", List.of("octocat", "hubot"));
            softly.assertThat(query).containsEntry("repository", List.of("Hello-World"));
            softly.assertThat(query).containsEntry("permission", List.of("metadata"));
            softly.assertThat(query).containsEntry("last_used_before", List.of(LAST_USED_BEFORE.toString()));
            softly.assertThat(query).containsEntry("last_used_after", List.of(LAST_USED_AFTER.toString()));
            softly.assertThat(query).containsEntry("token_id[]", List.of("1", "2"));
        });
    }

    @Test
    void toQueryValues_shouldRepeatOwnerAndTokenId_whenMultipleValues() {
        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                GitHubOrganizationsPersonalAccessTokensFilterBuilder.builder()
                        .owner(List.of("octocat", "hubot"))
                        .tokenId(List.of("1", "2"))
                        .build();

        final Map<String, List<String>> query = filter.toQueryValues();

        assertSoftly(softly -> {
            softly.assertThat(query.get("owner[]")).containsExactly("octocat", "hubot");
            softly.assertThat(query.get("token_id[]")).containsExactly("1", "2");
        });
    }

    @Test
    void constructor_shouldCopyOwner_whenPresent() {
        final List<String> owner = new ArrayList<>(List.of("octocat"));

        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                new GitHubOrganizationsPersonalAccessTokensFilter(null, null, owner, null, null, null, null, null);
        owner.add("hubot");

        assertThat(filter.owner()).containsExactly("octocat");
    }

    @Test
    void constructor_shouldThrow_whenOwnerExceedsMax() {
        final List<String> owner =
                IntStream.range(0, 11).mapToObj(i -> "user-" + i).toList();

        final Throwable thrown = catchThrowable(() ->
                new GitHubOrganizationsPersonalAccessTokensFilter(null, null, owner, null, null, null, null, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
    }

    @Test
    void constructor_shouldTreatEmptyOwnerAndTokenIdAsUnset_whenEmptyListsProvided() {
        final GitHubOrganizationsPersonalAccessTokensFilter filter = new GitHubOrganizationsPersonalAccessTokensFilter(
                null, null, List.of(), null, null, null, null, List.of());

        assertSoftly(softly -> {
            softly.assertThat(filter.owner()).isNull();
            softly.assertThat(filter.tokenId()).isNull();
            softly.assertThat(filter.toQueryValues()).isEmpty();
        });
    }

    @Test
    void toQueryValues_shouldSendUtcSeconds_whenLastUsedHasNanos() {
        final Instant withNanos = Instant.parse("2024-01-01T00:00:00.123456789Z");

        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                GitHubOrganizationsPersonalAccessTokensFilterBuilder.builder()
                        .lastUsedBefore(withNanos)
                        .build();

        assertThat(filter.toQueryValues()).containsEntry("last_used_before", List.of("2024-01-01T00:00:00Z"));
    }

    @Test
    void constructor_shouldThrow_whenTokenIdExceedsMax() {
        final List<String> tokenId =
                IntStream.range(0, 51).mapToObj(Integer::toString).toList();

        final Throwable thrown = catchThrowable(() ->
                new GitHubOrganizationsPersonalAccessTokensFilter(null, null, null, null, null, null, null, tokenId));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("token_id");
    }
}
