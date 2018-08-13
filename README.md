# What is vimaal?

It stands for "[**Vim**](https://www.vim.org/) **A**s **A** **L**ibrary". It's meant to be a reusable implementation of vim's backend. You can integrate it with your editor to provide access to vim's commands.

# Feature coverage

I googled `vim cheat sheet` and picked top three results. From these pages, I extracted the commands, more or less preserving the order. This way, the order in which features should be implemented is defined.

## Navigation/cursor movement

| Command | Description | Is implemented? |
|---------|-------------|:---------------:|
| `h`/`j`/`k`/`l` | Move cursor left/down/up/right | |
| `H`/`M`/`L` | Move to top/middle/bottom of screen | |
| `b`/`w` | Previous/next start of word | |
| `B`/`W` | Previous/next start of word, including punctuation | |
| `e` | Next end of word | |
| `E` | Next end of word, including punctuation | |
| `%` | Next matching braces | |
| `0` | Move to start of line | ✓ |
| `^` | Move to first non-blank character of line | |
| `g_` | Move to first non-blank character of line | |
| `$` | Move to end of line | |
| `gg` | Move to first line | |
| `G` | Move to last line | |
| `5G` | Move to line 5 | |
| `fx` | Move to next occurrence of character `x` | |
| `tx` | Move to before next occurrence of character `x` | |
| `Fx` | Move to previous occurrence of character `x` | |
| `Tx` | Move to after previous occurrence of character `x` | |
| `;` | Repeat previous `f`, `t`, `F` or `T` movement | |
| `,` | Repeat previous `f`, `t`, `F` or `T` movement, backwards | |
| `}` | Jump to next paragraph, or function/block when editing code | |
| `{` | Jump to previous paragraph, or function/block when editing code | |
| `zz` | Center cursor on screen | |
| `Ctrl+E` | Move screen down one line | |
| `Ctrl+Y` | Move screen up one line | |
| `Ctrl+F` | Move forward one full screen | |
| `Ctrl+B` | Move back one full screen | |
| `Ctrl+D` | Move forward 1/2 a screen | |
| `Ctrl+U` | Move back 1/2 a screen | |

## Editing

TODO

## Clipboard

| Command | Description | Is implemented? |
|---------|-------------|:---------------:|
| `dd` | Delete one line | ✓ |
| `3dd` | Delete 3 lines | ✓ |


TODO

## Visual mode

TODO

## Macros

TODO

## Searching and replacing

TODO
