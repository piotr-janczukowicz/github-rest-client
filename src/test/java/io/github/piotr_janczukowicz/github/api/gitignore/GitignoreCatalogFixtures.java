package io.github.piotr_janczukowicz.github.api.gitignore;

final class GitignoreCatalogFixtures {

    private GitignoreCatalogFixtures() {}

    static GitHubGitignoreTemplate gitignoreTemplate() {
        return new GitHubGitignoreTemplate("C", """
                # Object files
                *.o

                # Libraries
                *.lib
                *.a

                # Shared objects (inc. Windows DLLs)
                *.dll
                *.so
                *.so.*
                *.dylib

                # Executables
                *.exe
                *.out
                *.app
                """);
    }
}
