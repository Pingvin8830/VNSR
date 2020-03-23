#!/bin/bash
tmux new  -s vnsr_d -d -n srv -A
tmux neww -t vnsr_d -d -n shl
tmux neww -t vnsr_d -d -n db
tmux neww -t vnsr_d -d -n mdl
tmux neww -t vnsr_d -d -n adm
tmux neww -t vnsr_d -d -n url
tmux neww -t vnsr_d -d -n vws
tmux neww -t vnsr_d -d -n frm
tmux neww -t vnsr_d -d -n htm
tmux neww -t vnsr_d -d -n css

tmux attach -t vnsr_d
