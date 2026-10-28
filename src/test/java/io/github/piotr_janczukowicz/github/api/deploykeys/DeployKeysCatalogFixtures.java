package io.github.piotr_janczukowicz.github.api.deploykeys;

import java.time.Instant;

final class DeployKeysCatalogFixtures {

    private DeployKeysCatalogFixtures() {}

    static GitHubDeployKey deployKey() {
        return GitHubDeployKeyBuilder.builder()
                .id(1)
                .key("ssh-rsa AAA...")
                .url("https://api.github.com/repos/octocat/Hello-World/keys/1")
                .title("octocat@octomac")
                .verified(true)
                .createdAt("2014-12-10T15:53:42Z")
                .readOnly(true)
                .addedBy("octocat")
                .lastUsed(Instant.parse("2022-01-10T15:53:42Z"))
                .enabled(true)
                .build();
    }

    static GitHubCreateDeployKeyPayload createDeployKeyPayload() {
        return GitHubCreateDeployKeyPayloadBuilder.builder()
                .title("octocat@octomac")
                .key("ssh-rsa AAA...")
                .readOnly(true)
                .build();
    }
}
