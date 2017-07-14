
# Find Out What Ports Are Listening / Open On My Linux & FreeBSD Server     
```bash
# netstat --listen
or
# netstat -l
```

# lsof Command Examples
```bash
# lsof -i
```

# create symlink link 
```bash
ln -s jdk1.8.0_121/ latest
```
# find port 80** listening to and kill the process
```bash
netstat -ntlp | grep :80
kill -9 pid
```
