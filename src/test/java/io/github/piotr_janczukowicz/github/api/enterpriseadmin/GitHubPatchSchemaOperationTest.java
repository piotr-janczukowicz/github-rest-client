package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubPatchSchemaOperationTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubPatchSchemaOperation value = EnterpriseAdminCatalogFixtures.patchSchemaOperation();

        assertThat(GitHubPatchSchemaOperationBuilder.builder(value).build()).isEqualTo(value);
    }
}
