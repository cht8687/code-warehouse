## On Linux

execute:


```bash

#!/bin/bash

fonts_dir="~/.local/share/fonts"
if [ ! -d ${fonts_dir} ]; then
    echo "mkdir -p $fonts_dir"
    mkdir -p ${fonts_dir}
else
    echo "Found fonts dir $fonts_dir"
fi

for type in Bold Light Medium Regular Retina; do
    file_path="~/.local/share/fonts/FiraCode-${type}.ttf"
    file_url="https://github.com/tonsky/FiraCode/blob/master/distr/ttf/FiraCode-${type}.ttf?raw=true"
    if [ ! -e ${file_path} ]; then
        echo "wget -O $file_path $file_url"
        wget -O ${file_path} ${file_url}
    else
	echo "Found existing file $file_path"
    fi;
done

echo "fc-cache -f"
fc-cache -f

```


To open settings.json, from the File menu choose Preferences, Settings or use keyboard shortcut Ctrl+, (Cmd+, on Mac). Then paste the following lines and save the file.

```bash

"editor.fontFamily": "'Fira Code'",
"editor.fontLigatures": true

```

restart vscode


