package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeownersErrorsFilterTest {

    @Test
    void empty_shouldReturnNullRef_whenCalled() {
        final GitHubCodeownersErrorsFilter filter = GitHubCodeownersErrorsFilter.empty();

        assertThat(filter.ref()).isNull();
    }

    @Test
    void toQueryParameters_shouldReturnEmptyMap_whenRefNull() {
        final Map<String, String> query = GitHubCodeownersErrorsFilter.empty().toQueryParameters();

        assertThat(query).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEmitRef_whenRefProvided() {
        final GitHubCodeownersErrorsFilter filter =
                GitHubCodeownersErrorsFilterBuilder.builder().ref("main").build();

        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsEntry("ref", "main");
    }
}
