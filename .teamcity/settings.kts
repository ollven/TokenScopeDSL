import jetbrains.buildServer.configs.kotlin.*
import jetbrains.buildServer.configs.kotlin.buildFeatures.commitStatusPublisher
import jetbrains.buildServer.configs.kotlin.buildFeatures.perfmon
import jetbrains.buildServer.configs.kotlin.buildSteps.maven
import jetbrains.buildServer.configs.kotlin.projectFeatures.githubAppConnection
import jetbrains.buildServer.configs.kotlin.triggers.vcs
import jetbrains.buildServer.configs.kotlin.vcs.GitVcsRoot

/*
The settings script is an entry point for defining a TeamCity
project hierarchy. The script should contain a single call to the
project() function with a Project instance or an init function as
an argument.

VcsRoots, BuildTypes, Templates, and subprojects can be
registered inside the project using the vcsRoot(), buildType(),
template(), and subProject() methods respectively.

To debug settings scripts in command-line, run the

    mvnDebug org.jetbrains.teamcity:teamcity-configs-maven-plugin:generate

command and attach your debugger to the port 8000.

To debug in IntelliJ Idea, open the 'Maven Projects' tool window (View
-> Tool Windows -> Maven Projects), find the generate task node
(Plugins -> teamcity-configs -> teamcity-configs:generate), the
'Debug' option is available in the context menu for the task.
*/

version = "2024.03"

project {

    vcsRoot(HttpsGithubComOllvenBabySteps)

    buildType(Empty)

    subProject(SubprojectB)
    subProject(S)
}

object Empty : BuildType({
    name = "Empty Build"

    vcs {
        root(HttpsGithubComOllvenBabySteps)
    }
})

object HttpsGithubComOllvenBabySteps : GitVcsRoot({
    name = "https://github.com/ollven/BabySteps"
    url = "https://github.com/ollven/BabySteps"
    branch = "refs/heads/main"
    authMethod = password {
        userName = "ollven"
        password = "credentialsJSON:9fd10c2d-c48f-40aa-9156-19acfe23cab2"
    }
})


object S : Project({
    name = "Subproject 1"

    vcsRoot(S_HttpsGithubComOllvenBabyStepsRefsHeadsMain)

    buildType(S_Build)

    features {
        githubAppConnection {
            id = "PROJECT_EXT_13"
            displayName = "TeamCityTokenScope"
            appId = "873151"
            clientId = "Iv1.192b2a31c4b50ffe"
            clientSecret = "credentialsJSON:5bf90ce6-2894-43ef-a8bb-7fe05db7e7bc"
            privateKey = "credentialsJSON:6dc3c95c-0af0-4ab5-92a1-6821fc8bd5ab"
            webhookSecret = "credentialsJSON:01bda570-70f6-4cc9-bf74-96b18e962bad"
            ownerUrl = "https://github.com/ollven"
        }
    }

    subProject(S_BranchFilters)
    subProject(S_SubprojectAChild)
})

object S_Build : BuildType({
    name = "Build"

    vcs {
        root(S_HttpsGithubComOllvenBabyStepsRefsHeadsMain)
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            vcsRootExtId = "${S_HttpsGithubComOllvenBabyStepsRefsHeadsMain.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = storedToken {
                    tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:e1016005-8696-4445-a98c-6445ae12bd4c"
                }
            }
        }
    }
})

object S_HttpsGithubComOllvenBabyStepsRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/ollven/BabySteps#refs/heads/main"
    url = "https://github.com/ollven/BabySteps"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:d2e7c73f-f55d-4283-88aa-a63bd8c2e0ca"
    }
})


object S_BranchFilters : Project({
    name = "BranchFilters"

    vcsRoot(S_BranchFilters_HttpsGithubComOllvenBranchFiltersRefsHeadsMain)

    buildType(S_BranchFilters_Build)
})

object S_BranchFilters_Build : BuildType({
    name = "Build"

    vcs {
        root(S_BranchFilters_HttpsGithubComOllvenBranchFiltersRefsHeadsMain)
        root(S_HttpsGithubComOllvenBabyStepsRefsHeadsMain, "+:. => .subdirectory")
    }

    steps {
        maven {
            id = "Maven2"
            goals = "clean test"
            runnerArgs = "-Dmaven.test.failure.ignore=true"
        }
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
    }
})

object S_BranchFilters_HttpsGithubComOllvenBranchFiltersRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/ollven/BranchFilters#refs/heads/main"
    url = "https://github.com/ollven/BranchFilters"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:74547a8a-7283-426a-a115-f5ec7769b026"
    }
})


object S_SubprojectAChild : Project({
    name = "Subproject A child"

    vcsRoot(S_SubprojectAChild_HttpsGithubComOllvenNetSimpleProjectRefsHeadsMain)

    buildType(S_SubprojectAChild_Build)
})

object S_SubprojectAChild_Build : BuildType({
    name = "Build"

    vcs {
        root(S_SubprojectAChild_HttpsGithubComOllvenNetSimpleProjectRefsHeadsMain)
    }

    triggers {
        vcs {
        }
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            vcsRootExtId = "${S_SubprojectAChild_HttpsGithubComOllvenNetSimpleProjectRefsHeadsMain.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = storedToken {
                    tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:fac2541c-5da7-4dcc-b73f-25283e2c0741"
                }
            }
        }
    }
})

object S_SubprojectAChild_HttpsGithubComOllvenNetSimpleProjectRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/ollven/.NetSimpleProject#refs/heads/main"
    url = "https://github.com/ollven/.NetSimpleProject"
    branch = "refs/heads/main"
    branchSpec = "refs/heads/*"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:2016400b-b891-4ce2-9d1a-4a28e57aab65"
    }
})


object SubprojectB : Project({
    name = "Subproject B"

    vcsRoot(SubprojectB_HttpsGithubComOllvenOneTestClassRefsHeadsMain)

    buildType(SubprojectB_Xs)

    features {
        githubAppConnection {
            id = "PROJECT_EXT_64"
            displayName = "TeamCityCheckTokenScope2"
            appId = "873321"
            clientId = "Iv1.631ad6ac96fa2576"
            clientSecret = "credentialsJSON:504f8a01-2b66-4129-b13d-e22a2af646a0"
            privateKey = "credentialsJSON:1e5fafd1-c523-4bb3-88f9-1c0da9c120f4"
            webhookSecret = "credentialsJSON:1e488119-ad8a-4ddd-b9fb-4b4186534473"
            ownerUrl = "https://github.com/ollven"
        }
    }
})

object SubprojectB_Xs : BuildType({
    name = "xs"

    vcs {
        root(SubprojectB_HttpsGithubComOllvenOneTestClassRefsHeadsMain)
    }

    features {
        perfmon {
        }
        commitStatusPublisher {
            vcsRootExtId = "${S_HttpsGithubComOllvenBabyStepsRefsHeadsMain.id}"
            publisher = github {
                githubUrl = "https://api.github.com"
                authType = storedToken {
                    tokenId = "tc_token_id:CID_c707dfbb36afa4bd6b7ff56d7df26aa3:-1:e1016005-8696-4445-a98c-6445ae12bd4c"
                }
            }
        }
    }
})

object SubprojectB_HttpsGithubComOllvenOneTestClassRefsHeadsMain : GitVcsRoot({
    name = "https://github.com/ollven/oneTestClass#refs/heads/main"
    url = "https://github.com/ollven/oneTestClass"
    branch = "refs/heads/main"
    authMethod = token {
        userName = "oauth2"
        tokenId = "tc_token_id:CID_c4f4afae51277ab7b131458657afb9f6:-1:2dd7b2a6-e498-4c7e-8b6c-fa4a4b7a67df"
    }
})
