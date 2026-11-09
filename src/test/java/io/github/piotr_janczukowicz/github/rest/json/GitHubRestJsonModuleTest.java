package io.github.piotr_janczukowicz.github.rest.json;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.json.GitHubJsonModule;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ServiceLoader;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRestJsonModuleTest {

    @Test
    void module_shouldBeFoundByTheServiceLoader_whenTheRestModelsArePresent() {
        final List<Class<? extends GitHubJsonModule>> providers = ServiceLoader.load(GitHubJsonModule.class).stream()
                .map(ServiceLoader.Provider::type)
                .toList();

        assertThat(providers).contains(GitHubRestJsonModule.class);
    }

    @Test
    void module_shouldReturnTheRestModule_whenCalled() {
        final GitHubJsonModule provider = new GitHubRestJsonModule();

        final String name = provider.module().getModuleName();

        assertThat(name).isEqualTo("GitHubApi");
    }

    @Test
    void servicesFile_shouldListTheRestModule_whenTheJarIsOnTheClasspath() throws IOException {
        final String resource = "/META-INF/services/" + GitHubJsonModule.class.getName();

        final String listed;
        try (InputStream stream = GitHubRestJsonModule.class.getResourceAsStream(resource)) {
            assertThat(stream).isNotNull();
            listed = new String(stream.readAllBytes(), StandardCharsets.UTF_8).trim();
        }

        assertSoftly(softly -> softly.assertThat(listed).isEqualTo(GitHubRestJsonModule.class.getName()));
    }
}
