# Homebrew Permissions Denied Issues Solution
sudo chown -R $(whoami) $(brew --prefix)/*
