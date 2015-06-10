# EncodingHelper
A command line tool for Unicode encoding and decoding.

The original repo is [HERE](https://bitbucket.org/bantat/encodinghelper) on bitbucket.

Authors: Tore Banta, Derek Shang

## Usage
* Given only a string as its input, EncodingHelper prints out a summary of its Unicode contents.  
~~~
> java EncodingHelper être
String: être
Code points: U+00EA U+0074 U+0072 U+0065
UTF-8: \xC3\xAA\x74\x72\x65
~~~
* If the string contains only a single character, then it includes the name of the character in the summary.
~~~
> java EncodingHelper é
Character: é
Code point: U+00E9
Name: LATIN SMALL LETTER E WITH ACUTE
UTF-8: \xC3\xA9
~~~
* Output type can be specified with the `--output` argument.
~~~
> java EncodingHelper --output utf8 être
\xC3\xAA\x74\x72\x65
~~~
Short argument name (`-o`) is also supported.
* Input type can be specified as well using the `--input` argument.
~~~
> java EncodingHelper --input codepoint U+00E9
Character: é
Code point: U+00E9
Name: LATIN SMALL LETTER E WITH ACUTE
UTF8: \xC3\xA9
~~~
Short argument name (`-i`) is also supported.
* The user can specify both input and output types, specifying them in an arbitrary order and with short or long names for either.
~~~
> java EncodingHelper --input utf8 --output codepoint '\xC3\xAA'
U+00EA
> java EncodingHelper -i utf8 -o codepoint '\xC3\xAA'
U+00EA
> java EncodingHelper --output codepoint -i utf8  '\xC3\xAA'
U+00EA
~~~
* Every input type supports multi-character input, including codepoints. Multiple codepoints can be passed in either as multiple arguments, or as a single space-separated argument.
~~~
> java EncodingHelper -i codepoint -o utf8 U+00EA U+0074 U+0072 U+0065
\xC3\xAA\x74\x72\x65
> java EncodingHelper -i codepoint -o utf8 "U+00EA U+0074 U+0072 U+0065"
\xC3\xAA\x74\x72\x65
~~~
* The 'lang' output argument returns the unicode group which a character belongs to.
~~~
> java EncodingHelper Щ
Character: Щ
Code point: U+0429
Name: CYRILLIC CAPITAL LETTER SHCHA
UTF-8: \xD0\xA9
Group: Cyrillic
> java EncodingHelper -o lang Щ
Char 1: Щ, Group: Cyrillic
~~~
* If no arguments are given, then EncodingHelper prints out a helpful usage message.
