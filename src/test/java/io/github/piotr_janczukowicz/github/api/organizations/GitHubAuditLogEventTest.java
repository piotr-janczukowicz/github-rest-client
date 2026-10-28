package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuditLogEventTest {

    @Test
    void constructor_shouldLeaveDataNull_whenNull() {
        final GitHubAuditLogEvent sut = GitHubAuditLogEventBuilder.builder(OrganizationsCatalogFixtures.auditLogEvent())
                .data(null)
                .build();

        assertThat(sut.data()).isNull();
    }

    @Test
    void constructor_shouldLeaveConfigNull_whenNull() {
        final GitHubAuditLogEvent sut = GitHubAuditLogEventBuilder.builder(OrganizationsCatalogFixtures.auditLogEvent())
                .config(null)
                .build();

        assertThat(sut.config()).isNull();
    }

    @Test
    void constructor_shouldLeaveConfigWasNull_whenNull() {
        final GitHubAuditLogEvent sut = GitHubAuditLogEventBuilder.builder(OrganizationsCatalogFixtures.auditLogEvent())
                .configWas(null)
                .build();

        assertThat(sut.configWas()).isNull();
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenNull() {
        final GitHubAuditLogEvent sut = GitHubAuditLogEventBuilder.builder(OrganizationsCatalogFixtures.auditLogEvent())
                .events(null)
                .build();

        assertThat(sut.events()).isNull();
    }

    @Test
    void constructor_shouldLeaveEventsWereNull_whenNull() {
        final GitHubAuditLogEvent sut = GitHubAuditLogEventBuilder.builder(OrganizationsCatalogFixtures.auditLogEvent())
                .eventsWere(null)
                .build();

        assertThat(sut.eventsWere()).isNull();
    }
}
