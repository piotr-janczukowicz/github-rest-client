package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubExternalGroupMemberTest {

    @Test
    void externalGroupMember_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubExternalGroupMember value = TeamCatalogFixtures.externalGroupMember();

        assertThat(value.memberId()).isEqualTo(1L);
        assertThat(value.memberLogin()).isEqualTo("mona-lisa_eocsaxrs");
        assertThat(value.memberName()).isEqualTo("Mona Lisa");
        assertThat(value.memberEmail()).isEqualTo("mona_lisa@github.com");
    }
}
