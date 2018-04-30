export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion

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

alias dsf='git diff --color | diff-so-fancy'

alias reload='source ~/.bash_profile'

# tmux
alias t='tmux'

# Init jenv
if which jenv > /dev/null; then eval "$(jenv init -)"; fi

# alias commands
alias cdw='cd ~/Documents/work';
alias cdp='cd ~/Documents/cht'
alias cnginx='code /usr/local/etc/nginx/nginx.conf'
alias cdn='cd /usr/local/etc/nginx; code nginx.conf;'
alias rnginx='sudo nginx -s stop;sudo nginx;'
alias gitcht8687='git config user.name "cht8687"; git config user.email "cht8687@gmail.com";'
alias gs='gulp serve-dev'
alias dud='bash update_from_git.sh'
alias dc='docker-compose'
alias dcps='docker-compose ps'

export PATH=/Users/rob.chang/Documents/cht/code-warehouse/Android/flutter/bin:$PATH

[[ -f ~/.bashrc ]] && . ~/.bashrc
