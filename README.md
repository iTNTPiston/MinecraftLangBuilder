# MinecraftLangBuilder
A Java Program that helps you to build MC lang files

To download, see the release folder. It has the executable jar, an example script and the output from that script
<br>
# Script Commands
<b>Command</b> #SCRIPT <br>
<i>Mark beginning of the script, also defines languages</i><br>
<b>Usage:</b><br>
#SCRIPT<br>
lang1<br>
lang2<br>
...<br>
<i>{EMPTY_LINE}</i><br>

<b>Command</b> #END <br>
<i>Mark end of the script</i><br>
<b>Usage:</b><br>
#END




NAME define => group.name[suffix][enum]=<name>
name
lang1
lang2
...

GROUP forward group
groupname

SUFFIX change suffix
suffixname

NAMEENUM start enum
name
lang1line0
lang1line1
lang1line...

lang2line0
...

RETURN return group
RETURNALL return all groups
