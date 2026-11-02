package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGroupResponseMemberTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubGroupResponseMember value = EnterpriseAdminCatalogFixtures.groupResponseMember();

        assertThat(GitHubGroupResponseMemberBuilder.builder(value).build()).isEqualTo(value);
    }

    @Test
    void constructor_shouldAcceptNull_whenValueAndRefAbsent() {
        final GitHubGroupResponseMemberBuilder builder = GitHubGroupResponseMemberBuilder.builder(
                        EnterpriseAdminCatalogFixtures.groupResponseMember())
                .value(null)
                .ref(null);

        final GitHubGroupResponseMember member = builder.build();

        assertSoftly(softly -> {
            softly.assertThat(member.value()).isNull();
            softly.assertThat(member.ref()).isNull();
        });
    }
}
