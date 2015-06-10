# EncodingHelper
A command line tool for Unicode encoding and decoding.

Authors: Tore Banta, Derek Shang

## Usage
* Given only a string as its input, EncodingHelper prints out a summary of its Unicode contents.  
~~~
> java EncodingHelper être
String: être
Code points: U+00EA U+0074 U+0072 U+0065
UTF-8: \xC3\xAA\x74\x72\x65
~~~
If the string contains only a single character, then it includes the name of the character in the summary.
~~~
> java EncodingHelper é
Character: é
Code point: U+00E9
Name: LATIN SMALL LETTER E WITH ACUTE
UTF-8: \xC3\xA9
~~~
