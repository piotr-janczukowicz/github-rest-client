package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListSamlSsoAuthorizationsFilterTest {

    @Test
    void empty_shouldHaveNoQueryParameters() {
        assertThat(GitHubListSamlSsoAuthorizationsFilter.empty().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldIncludeLogin_whenSet() {
        assertThat(OrganizationsCatalogFixtures.listSamlSsoAuthorizationsFilter()
                        .toQueryParameters())
                .containsExactlyEntriesOf(Map.of("login", "monalisa"));
    }
}
