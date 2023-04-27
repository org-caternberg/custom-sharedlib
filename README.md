




# Current State and Objectives

This repo shows approaches we have discussed for implementing a new Pipeline design
The objectives for a new SharedLibrary and Pipeline design are:

* Reducing the complexity of Shared Library design and implementation
* Reuseability and testability
* Avoid [Script Approvals](https://www.jenkins.io/doc/book/managing/script-approval/#in-process-script-approval)
* Better adoption
* Faster Pipeline/SharedLib development
* SharedLib redesign: Common (Global) Utility Lib and Team specific Lib 

# Steps

sh steps and shell scripts/steps
* When to use shell: https://google.github.io/styleguide/shellguide.html#s1.2-when-to-use-shell
* Bash settings: http://redsymbol.net/articles/unofficial-bash-strict-mode/
* Shell exit codes https://www.cyberciti.biz/faq/bash-get-exit-code-of-command/

We have implemented an discussed several approaches on how to use Pipeline shell steps in a proper way:
See file  in `vars` directory.

We have NOT reviewed all step details right now. 

# Shared Libraries

To avoid "script approvals" we must define Shared Libraries on Jenkins "Global" level.
Libraries on Folder Level might require approvals. Even when a approval is required, we should look for a better alternative
(F.e The dependency to `JSonSlurper` can be removed by using `jq`, see sample in `vars` dir)

# Next Steps

* review of additional certain steps: what can be externalized or optimized?
* Review and redesign/refactor more SharedLib step code. Identify step for the common library and identify what team specific.
* What should be in /src? What should be in /vars? When to use groovy and when to use shell?
* POC: implement/migrate an existing Pipeline Job with the "new Design"
* Can we avoid script approvals when a even when shared lib on folder level? How?
* Redesign of stages: Better naming and grouping (like: init -> build -> test -> qa -> deploy). The current stage areas are confusing
