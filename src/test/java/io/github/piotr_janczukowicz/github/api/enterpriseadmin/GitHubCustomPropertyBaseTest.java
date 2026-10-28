package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertyBaseTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCustomPropertyBase value = EnterpriseAdminCatalogFixtures.customPropertyBase();

        assertThat(GitHubCustomPropertyBaseBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldLeaveAllowedValuesNull_whenNull() {
        final GitHubCustomPropertyBase value = GitHubCustomPropertyBaseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.customPropertyBase())
                .allowedValues(null)
                .build();

        assertThat(value.allowedValues()).isNull();
    }

    @Test
    void constructor_shouldLeaveDefaultValueNull_whenNull() {
        final GitHubCustomPropertyBase value = GitHubCustomPropertyBaseBuilder.builder(
                        EnterpriseAdminCatalogFixtures.customPropertyBase())
                .defaultValue(null)
                .build();

        assertThat(value.defaultValue()).isNull();
    }
}
