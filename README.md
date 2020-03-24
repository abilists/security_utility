# security_utility library for Abilists \[ [www.abilists.com](http://www.abilists.com)\]
--------------------------------------------------
[![Build Status](https://travis-ci.org/abilists/security_utility.svg?branch=master)](https://travis-ci.org/abilists/security_utility)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/abilists/security_utility)

**security_utility** is to protect your information in Server Side. 

#### About
security_utility has a few special features:

* It supports encryption decryption on compression.
* Generate Hash code.
* Generate Token.
#### Runtime Requirements

- *P1:* Java8 or newer
- *P2:* Junit test

#### How to Install
Build as blow
```
$ gradle install
```

#### Get started
Add the following code into the Model class.
```
String key = "test12345abcdefg"; // key 16 digit

CipherUtility cipherUtility = new CipherUtility(key);
String encrypted = cipherUtility.encrypt("TestTestTEsta");
```

#### License

security_utility is distributed under the MIT License.

