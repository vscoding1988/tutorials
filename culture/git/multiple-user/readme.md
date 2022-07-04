# How to have multiple user in GIT

The user config for git is stored in the file `.gitconfig` in your user folder. If you want to use
different user based on the project folder there is a possibility to swap config based on the
folder.

Let's assume we have a basic user and a special user for all projects in the folder `c:/Dev/GitHub/`
.

```text
# .gitconfig

[include]
    path = base.gitconfig
[includeIf "gitdir/i:c:/Dev/GitHub/**"]
    path = github.gitconfig	
```

This config will include `github.gitconfig` for all projects in `c:/Dev/GitHub/`
and `base.gitconfig` for all other projects.
