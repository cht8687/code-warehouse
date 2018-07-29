
#  Install R on Linux
```bash
step1:

sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys E084DAB9

if timeout, then use following

gpg --keyserver hkp://keyserver.ubuntu.com:80 --recv-keys E084DAB9
gpg -a --export E084DAB9 | sudo apt-key add -pt install r-base

step2:
sudo apt update

step3:
sudo apt install r-base
```
