https://github.com/tony/tmux-config


## Session Management

```bash
tmux new -s session_name
```
creates a new tmux session named session_name

```bash
tmux attach -t session_name
```
attaches to an existing tmux session named session_name

```bash
tmux switch -t session_name
```
switches to an existing session named session_name

```bash
tmux list-sessions
```

lists existing tmux sessions

```bash
tmux detach (prefix + d)
```
detach the currently attached session



## Windows

```bash
tmux new-window (prefix + c)
```
create a new window

```bash
tmux select-window -t :0-9 (prefix + 0-9)
```
move to the window based on index

```bash
tmux rename-window (prefix + ,)
```

rename the current window


## Panes
```bash
tmux split-window (prefix + ")
```
splits the window into two vertical panes

```bash
tmux split-window -h (prefix + %)
```
splits the window into two horizontal panes

```bash
tmux swap-pane -[UDLR] (prefix + { or })
```
swaps pane with another in the specified direction

```bash
tmux select-pane -[UDLR]
```
selects the next pane in the specified direction

```bash
tmux select-pane -t :.+
```
selects the next pane in numerical order


## Helpful tmux commands

```bash
tmux list-keys
```

lists out every bound key and the tmux command it runs

```bash
tmux list-commands
```

lists out every tmux command and its arguments

```bash
tmux info
```

lists out every session, window, pane, its pid, etc.

```bash
tmux source-file ~/.tmux.conf
```
reloads the current tmux configuration (based on a default tmux config)
Must-haves


## These are some of my must-haves in tmux config:

remap prefix to Control + a
```bash
set -g prefix C-a
```
unbind C-b

```bash
bind C-a send-prefix
```

force a reload of the config file
unbind r
```bash
bind r source-file ~/.tmux.conf
```

quick pane cycling
unbind ^A
```bash
bind ^A select-pane -t :.+
```