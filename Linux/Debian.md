## Vmare Tools or open-vm-tools

```bash
sudo apt-get install open-vm-tools open-vm-tools-desktop
```

# MongoDB

## Install mongodb on debian
https://docs.mongodb.com/manual/tutorial/install-mongodb-on-debian/

## Start MongoDB
```bash
sudo service mongod start
```
## Stop MongoDB
```bash
sudo service mongod stop
```
## Restart MongoDB
```bash
sudo service mongod restart
```
## Log
```
/var/log/mongodb/mongod.log
```

# i3 
```bash
su
dpkg-reconfigure x11-common
```

# VS Code

https://code.visualstudio.com/docs/setup/linux

# Install .Deb files
```bash
sudo dpkg -i packagename.deb
```

# Uninstall 
```bash
sudo dpkg -r packagename
```

# unzip tar zxvf .tar.gz      
```bash
tar zxvf WebStorm-2016.3.tar.gz
```

# Postgres
Install Postgres
```bash
apt-get install postgresql-9.4
```

Change default password
```bash
$ sudo -u postgres psql postgres
postgres=# \password postgres
```
