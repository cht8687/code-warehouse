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