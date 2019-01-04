# MinecraftLangBuilder
A Java Program that helps you to build MC lang files

To download, see the release folder. It has the executable jar, an example script and the output from that script
<br>
# Script Commands
<b>Command</b> #SCRIPT <br>
Mark beginning of the script, also defines languages<br>
<b>Usage:</b><br>
#SCRIPT<br>
[lang1]<br>
[lang2]<br>
...<br>
<i>{EMPTY_LINE}</i><br>

<b>Command</b> #END <br>
Mark end of the script<br>
<b>Usage:</b><br>
#END

<b>Command</b> #NAME <br>
Add a localization entry with the current group and suffix<br>
The entry will be exported as [group].[name][suffix][enum]=[localized]<br>
<b>Usage:</b><br>
#NAME<br>
[name]<br>
[localized string for lang1]<br>
[localized string for lang2]<br>
...<br>
<i>{EMPTY_LINE}</i><br>

<b>Command</b> #NAMEENUM<br>
Add a enumerated localization entry<br>
Group and suffix also apply<br>
<b>Usage:</b><br>
#NAMEENUM<br>
[listname]<br>
[lang1line0]<br>
[lang1line1]<br>
[lang1line...]<br>
<i>{EMPTY_LINE}</i><br>
[lang2line0]<br>
[...]<br>
<i>{EMPTY_LINE_IN_THE_END}</i><br>

<b>Command</b> #GROUP<br>
Push a group. If the old group is foo and we push bar, the group becomes foo.bar<br>
The default group name is an empty string<br>
<b>Usage:</b><br>
#GROUP
[group name]

<b>Command</b> #SUFFIX<br>
Change suffix.<br>
Use an empty line in the place of suffix name to reset the suffix to empty<br>
<b>Usage:</b><br>
#SUFFIX<br>
[suffix name]<br>

<b>Command</b> #RETURN<br>
Pop(return) the group<br>
If the group is foo.bar and we pop, the group becomes foo<br>
This is a one line command, but you can put comment on the same line or on the next line<br>
It is recommended to put the group name after return, but you don't have to<br>
<b>Usage:</b><br>
#RETURN<br>

<b>Command</b> #RETURNALL<br>
Pop all groups. Reset group name to empty<br>
This is a shortcut for many returns, but I don't recommended this<br>
<b>Usage:</b><br>
#RETURNALL

