# MinecraftLangBuilder
A Java Program that helps you to build MC lang files

To download, see the release folder. It has the executable jar, an example script and the output from that script

Script Commands:
<b>Command</b> #SCRIPT 
<i>Mark beginning of the script<i>
<b>Usage:</b>
#SCRIPT
lang1
lang2
...
<i>{EMPTY_LINE}</i>



END end


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
