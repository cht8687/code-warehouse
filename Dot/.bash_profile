# List all
alias listall='cd; cat .bash_profile'
alias reload='source .bash_profile'
# Bash
alias ls='ls -laghFG'
alias ll='ls -laghFG'
alias l='ls -laghFG'
alias cd..='cd ..'
alias g='git'

# Git
git config --global color.ui true
git config --global format.pretty oneline
git config --global core.autocrl input
git config --global core.fileMode true

git config --global alias.co checkout
git config --global alias.br branch
git config --global alias.ci commit
git config --global alias.st status

alias dsf='git diff --color | diff-so-fancy'

# Software
alias reload='source ~/.bash_profile'

# Services

# nginx
alias nginxstart='sudo service nginx start'
alias nginxstop='sudo service nginx stop'
alias confignginx='sudo vim /etc/nginx/sites-enabled/default'
alias lint='./node_modules/eslint/bin/eslint.js --fix src';
# tmux

alias tm='tmux'


alias fixzsh='mv .zsh_history .zsh_history_bad;strings .zsh_history_bad > .zsh_history;fc -R .zsh_history';