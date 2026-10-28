package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload value =
                OrganizationsCatalogFixtures.customPropertiesForOrgsCreateOrUpdateValuesPayload();
        final GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload expected =
                OrganizationsCatalogFixtures.customPropertiesForOrgsCreateOrUpdateValuesPayload();

        assertThat(value.properties()).isEqualTo(expected.properties());
    }

    @Test
    void constructor_shouldThrow_whenPropertiesNull() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubCustomPropertiesForOrgsCreateOrUpdateValuesPayload(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("properties");
    }
}
