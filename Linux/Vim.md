## When you edit a file without sudo

```bash
:w !sudo tee % 
```

## Travel back in time

```bash
:earlier 15m
:later 15m
```

## Execute any command
```bash
:!ls -l
```

## Several useful delete examples vim tricks
```bash
diw to delete the current word and ciw to cut the current word.
de is like diw, however it opens the opportunity to delete every next word just by pressing dot(.).
di( delete within the current parents.
di" to delete the text between the quotes.
dab delete around brackets.
daB delete around curly brackets.
```

## Several useful cut 

```bash
ciw to cut the current word.
ci" cut the word inside the quotes.
ci( cut the word in the parents.
C cut the rest of the line and enter INSERT MODE. This is very useful for cut and paste.
```

## Miscellaneous useful commands

```bash
zz Scroll down the screen to make the current line appear in the middle. Very useful to put some chunk of code in focus.
% finds and moves the cursor to the matching parentheses.
:%TOhtml Creates HTML version of the current document. (Try it, it is very useful).
vim http://rosehosting.com/ Vim can also open up URLs assuming they go directly to static HTML files.
```
## Search and replace

```bash
:%s/something/something_else/g Find the word something and replace it with something_else in the entire document.
:s/something/something_else/g Similarly like the before command. This one just replaces in the current line only.
:%s/something/something_else/gc Note the c. It replaces everything but asks for confirmation first.
:%s/\<something\>/something_else/gc Changes whole words exactly matching something with something_else but ask for confirmation first.
:%s/SomeThing/something_else/gic Here the i flag is used for case insensitive search. And the c flag for confirmation.
```

