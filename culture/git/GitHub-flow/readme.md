# GitHub - Flow

![flow-chart.png](flow-chart.png)

GitHub - Flow describe a lightweight branching system for git. It is based on a master/feature
branch system. Where the feature branch is checked out from master and after a review merged back.
The master can than be deployed to the environments.

It is way faster than the GitFlow, especially when you compare the time to deploy, but there are
some pitfalls you have to know especially in new teams.

## Pitfall 1 - release deadline

If your team is bound on releasing in a certain time window, you may run into problems. By itself
the GitHub - Flow expects the deployment from master. So when the release candidate is merged, you
will have to secure the master branch from merges. This will make the work, until the master is open
again unfinishable.

> This can be fixed by releasing git tags, so after the work is done, you create a git tag and
> release it in your deployment window, also this makes hotfixes/rollbacks easier

## Pitfall 2 - dirty master

You can deploy changes pretty fast, because there is no multibranch approach, no release candidate
branches, there is only one source and if it is corrupted and has bugs, or feature which should not
be released this time (f.e. this may happen, when deployment has to be postponed, and the
development of new feature begun) you will have to do cherry-picking or reverting the changes.
Especially in new teams you will end up with dirty master very often and this will make it hard to
keep the deployment windows.

> One way to handle this are feature flags, but this is a very costly way, especially if you are
> working in microservice environment and your services has only two-three features, but in big
> applications this may be a valid way. A better way would be to set up a sandbox for branch checks,
> but this is only a valid approach if you have a good e2e coverage.
