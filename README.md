# What is vimaal?

It stands for "[**Vim**](https://www.vim.org/) **A**s **A** **L**ibrary". It's meant to be a reusable implementation of vim's backend. You can integrate it with your editor to provide access to vim's commands.

# Feature coverage

The order of sections doesn't matter. The order of features: I picked it subjectively, to reflect which features I use the most. I use the order within a given section to figure out which features to implement next.

 *"(repeatable)"* means that one can enter a number (N) before executing a given command to execute it N times.

The shortcuts are implemented in
[VimShortcutTranslator.kt](src/main/kotlin/it/krzeminski/vimaal/VimShortcutTranslator.kt).

## Navigation/cursor movement

| Command | Description | Is implemented? |
|---------|-------------|:---------------:|
| `gg` | Move to first line | ✓ |
| `G` | Move to last line | ✓ |
| `5G`/`5gg` | Move to line 5 | ✓ |
| `h`/`j`/`k`/`l` (repeatable) | Move cursor left/down/up/right | ✓ |
| `b`/`w` (repeatable) | Previous/next start of word | |
| `B`/`W` (repeatable) | Previous/next start of word, including punctuation | |
| `e` (repeatable) | Next end of word | |
| `E` (repeatable) | Next end of word, including punctuation | |
| `%` | Next matching braces | |
| `H`/`M`/`L` | Move to top/middle/bottom of screen | |
| `0` | Move to start of line | ✓ |
| `^` | Move to first non-blank character of line | |
| `g_` | Move to first non-blank character of line | |
| `$` | Move to end of line | |
| `fx` (repeatable) | Move to next occurrence of character `x` | |
| `tx` (repeatable) | Move to before next occurrence of character `x` | |
| `Fx` (repeatable) | Move to previous occurrence of character `x` | |
| `Tx` (repeatable) | Move to after previous occurrence of character `x` | |
| `;` (repeatable) | Repeat previous `f`, `t`, `F` or `T` movement | |
| `,` (repeatable) | Repeat previous `f`, `t`, `F` or `T` movement, backwards | |
| `}` | Jump to next paragraph, or function/block when editing code | |
| `{` | Jump to previous paragraph, or function/block when editing code | |
| `zz` | Center cursor on screen | |
| `Ctrl+E` (repeatable) | Move screen down one line | |
| `Ctrl+Y` (repeatable) | Move screen up one line | |
| `Ctrl+F` (repeatable) | Move forward one full screen | |
| `Ctrl+B` (repeatable) | Move back one full screen | |
| `Ctrl+D` (repeatable) | Move forward 1/2 a screen | |
| `Ctrl+U` (repeatable) | Move back 1/2 a screen | |

## Clipboard

| Command | Description | Is implemented? |
|---------|-------------|:---------------:|
| `yy` (repeatable) | Copy one line | |
| `dd` (repeatable) | Cut one line | ✓ |
| `p` | Paste from the clipboard after cursor | |
| `P` | Paste from the clipboard before cursor | |
| `yw` (repeatable) | Copy from cursor to the end of 1st/Nth word | |
| `yaw` (repeatable) | Copy 1 or N word(s), starting from the cursor | |
| `dw` (repeatable) | Cut from cursor to the end of 1st/Nth word | |
| `daw` (repeatable) | Cut the whole 1 or N word(s) | |

## Inserting

| Command | Description | Is implemented? |
|---------|-------------|:---------------:|
| `i` | insert before the cursor | |
| `<Esc>` | Exit insert mode | |
| `I` | insert at the beginning of the line | |
| `a` | insert after the cursor | |
| `A` | insert at the end of the line | |

## The rest

TODO
