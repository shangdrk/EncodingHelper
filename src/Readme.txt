**EncodingHelper**
----------------------
Authors: Tore Banta, Derek Shang.

The additional feature we implemented is support for the "lang" output type.
Unicode group information is printed as part of the single character summary,
but was excluded from multi-input summaries to prevent excessive print
statements. The method getGroupName() in EncodingHelperChar.java reads range
values for each Unicode group contained in the "lang.txt" document, and
returns the group name once the appropriate range is found for a given
codepoint.
-----------------------
Quick example 1:
>java EncodingHelper Щ

Character: Щ
Code point: U+0429
Name: CYRILLIC CAPITAL LETTER SHCHA
UTF-8: \xD0\xA9
Group: Cyrillic
-----------------------
Quick example 2:
>java EncodingHelper -o lang Щ

Char 1: Щ, Group: Cyrillic