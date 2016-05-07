#Set up multiple Github accounts on machine

##Step 1:

account 1:

```bash
ssh-keygen -t rsa -C "your-email-address_1"

```
when prompted, save the file as id_rsa_account1, 'account1' can be your own string.
copy id_rsa_account1.pub to your first account Github->Personal settings->SSH and GPG keys->New SSH key.

account 2:

```bash
ssh-keygen -t rsa -C "your-email-address_2"

```

when prompted, save the file as id_rsa_account1, 'account2' can be your own string.
copy id_rsa_account1.pub to your second account Github->Personal settings->SSH and GPG keys->New SSH key.


##Step 2:

touch ~/.ssh/config

use your own editor, type in:

```bash
#Default GitHub
Host github-account1
HostName github.com
User git
IdentityFile ~/.ssh/id_rsa_account1

Host github-account2
HostName github.com
User git
IdentityFile ~/.ssh/id_rsa_account2

```

##Step 3:

Clear currently stored identities:

```bash
$ ssh-add -D

```

-- tips: If you are using git bash on Windows, type in:

```bash

eval `ssh-agent -s`
ssh-add

```

Add new keys:

```bash
$ ssh-add id_rsa_account1
$ ssh-add id_rsa_account2
```

Test to make sure new keys are stored:

```bash
$ ssh-add -l
```

Test to make sure Github recognizes the keys:

```bash
$ ssh -T github-account1
You've successfully authenticated, but GitHub does not provide shell access.

$ ssh -T github-account2
Hi You've successfully authenticated, but GitHub does not provide shell access.

```

