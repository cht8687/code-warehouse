# List all
alias listall='cd; cat .bash_profile'
alias reload='source .bash_profile'
# Bash
alias ls='ls -laghFG'
alias ll='ls -laghFG'
alias l='ls -laghFG'
alias cd..='cd ..'

# Git
git config --global color.ui true
git config --global format.pretty oneline
git config --global core.autocrl input
git config --global core.fileMode true

git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status

# Git git diff-so-fancy
alias dsf = 'git diff --color | diff-so-fancy'

# Software
alias reload='source ~/.bash_profile'

# Services
alias mst='sudo service mongod start'
alias msp='sudo service mongod stop'
alias mrs='sudo service mongod restart'
alias mlog='cat /var/log/mongodb/mongod.log'