#Download and install the Z shell:

For Debian based distros: 
```shell
$ sudo apt-get install zsh
```
For Red Hat based distros: 
```shell
$ sudo yum install zsh
```
For Suse based distros: 
```shell
$ sudo zypper install zsh
```
To simple use zsh, type zsh in your terminal:
```shell
$ zsh
```
3. How to set zsh as the default login shell:

This works on Fedora / Debian / OpenSuse: 
```shell
chsh -s /bin/zsh user

$ sudo chsh -s /bin/zsh yoda

$ finger yoda | grep zsh

Directory: /home/yoda Shell: /bin/zsh

If you want to customize the Z-shell, edit ~/.zshrc: vim ~/.zshrc
```


[Tutorial](https://www.codementor.io/linux/tutorial/configure-linux-toolset-zsh-tmux-vim)