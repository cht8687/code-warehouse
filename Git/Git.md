#Set Alias

git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status


#Squash commits from last N commits

git reset --soft HEAD^N
git commit -m "Bla"


