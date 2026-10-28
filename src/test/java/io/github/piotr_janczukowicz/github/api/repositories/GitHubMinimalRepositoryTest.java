package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMinimalRepositoryTest {

    @Test
    void constructor_shouldKeepNullValue_whenCustomPropertyValueNull() {
        final Map<String, Object> customProperties = new HashMap<>();
        customProperties.put("environment", null);

        final GitHubMinimalRepository repository = GitHubMinimalRepositoryBuilder.builder(
                        RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .customProperties(customProperties)
                .build();

        assertThat(repository.customProperties()).containsEntry("environment", null);
    }
}
