#Set Alias

```bash
git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status
```

#Squash commits from last N commits

```bash
git reset --soft HEAD^N
git commit -m "Bla"
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