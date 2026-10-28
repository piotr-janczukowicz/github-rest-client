package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPolicyOrgConditionsTest {

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNoRepositoryTargetSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyOrgConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyOrgConditions())
                .repositoryName(null)
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one of");
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenMultipleRepositoryTargetsSet() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsPolicyOrgConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyOrgConditions())
                .repositoryId(ActionsCatalogFixtures.actionsPolicyRepositoryId())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("exactly one of");
    }

    @Test
    void constructor_shouldAcceptRepositoryProperty_whenOnlyPropertyTargetSet() {
        final GitHubActionsPolicyOrgConditions sut = GitHubActionsPolicyOrgConditionsBuilder.builder(
                        ActionsCatalogFixtures.actionsPolicyOrgConditions())
                .repositoryName(null)
                .repositoryProperty(ActionsCatalogFixtures.actionsPolicyRepositoryProperty())
                .build();

        assertThat(sut).hasAllNullFieldsOrPropertiesExcept("repositoryProperty", "workflowPath");
    }
}
