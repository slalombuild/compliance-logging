<p align="center"><img src="docs/images/slalom.png" align="center" width="300" hspace="10" vspace="10"></p>
<h6 align="center">presents</h6>
<h3 align="center"><b>Compliance Logging</b></h3>
<h4 align="center">A library that allows you to mask sensitive information when logging.</h4>

<p align="center">
<a href="LICENSE" alt="Project License"><img src="https://img.shields.io/apm/l/vim-mode?style=for-the-badge"></a>
</p>

### Description

This library has been built to help developers masking PII, PCI and PHI when logging with your favorite framework.<br />
For instance, if you want to log a JSON String that contains some sensitive information, you would obtain something like:

```java
12:44:09 [main] INFO  MyClass - {"login":"john","password":"***********"}
```

The library supports right now two types of objects that can be masked: JSON and Lombok.<br />
To be able to achieve that and keep your logging framework still performant, the developer needs to indicate the framework with a mask. <br />
For example:
```java
LOGGER.info("{}", "{\"login\":\"john\",\"password":\"mypassword\"});
```
Will not mask anything, while with our mask:
```java
LOGGER.info(MaskType.JSON, "{}", "{\"login\":\"john\",\"password":\"mypassword\"});
```
It will mask the password (or any other fields listed as sensitive).

We provide different [examples](/tree/master/examples) that you can checkout in our repository

#### Usage with Log4j2

#### Usage with Logback

#### Questions?
[Create an issue](https://github.com/carlphilipp/compliance-logging/issues/new)

## Development

[Developer notes](DEV.md)

#### License
```text
MIT License

Copyright (c) 2020 Slalom LLC

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```