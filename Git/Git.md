#Set Alias

```bash
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
git config --global alias.lg-ascii "log --graph --pretty=format:'%h -%d %s (%cr) <%an>' --abbrev-commit"
```

#Squash commits from last N commits

```bash
git reset --soft HEAD^N
git commit -m "Bla"
```

#Squash commits from last N commits and push to remote

```bash
git reset --soft HEAD^N
git commit -m "Bla"
git push --force origin my_branch
```

#Reset head to certain commit

```bash
git reset --hard <sha1-commit-id>
git push origin HEAD --force
```

#Enable creditial-cache on windows

```bash

git config --global credential.helper wincred

```
#Sync a fork

```bash
git remote add upstream https://github.com/whoever/whatever.git
git fetch upstream
git checkout master
git rebase upstream/master
```

#Stash

```bash
git stash save "my_stash"
git stash list
git stash apply stash^{/my_stash}
```

#Remove untracked files

```bash
git clean -fdx

-f - force
-d - directories too
-x - remove ignored files too ( don't use this if you don't want to remove ignored files)

```

#Get commit based on message

```bash
git log --grep=".. any text you need to find ..."

```

#Git remove ignored files
```bash
git rm -r --cached .
git add .
git commit -am "Remove ignored files"
```

#Get satus of changes
```bash
 git diff --shortstat develop
 ```

#Make git aware filename letter case change
```bash
git mv -f OldFileNameCase newfilenamecase

or 

git config core.ignorecase false
```

#Make git remember credential
git config --global credential.helper store

