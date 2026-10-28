package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAttestationsFilterTest {

    @Test
    void toQueryParameters_shouldIncludePredicateType_whenProvided() {
        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                .predicateType("sbom")
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("predicate_type", "sbom");
    }

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilter.empty();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).isEmpty();
    }
}
