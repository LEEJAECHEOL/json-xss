## Json 으로 받을 때 XSS 방지 처리

- gradle 일 경우 : implementation 'org.apache.commons:commons-text:1.8'
- 참고 : https://jojoldu.tistory.com/470
- 루시 적용 같이 할 경우
  - https://medium.com/@dltkdals2202/spring-boot-%EC%97%90%EC%84%9C-json-%ED%83%80%EC%9E%85-xss-prevention-e9ce7b02c05b
- 이모지 필터 예외
  - https://hiphopddori.tistory.com/61
- HtmlCharacterEscapes
```java
package com.example.xss.escapes;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;

public class HtmlCharacterEscapes extends CharacterEscapes {

  private final int[] asciiEscapes;

  public HtmlCharacterEscapes() {
    // 1. XSS 방지 처리할 특수 문자 지정
    asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();
    asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['('] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes[')'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['#'] = CharacterEscapes.ESCAPE_CUSTOM;
    asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;

  }

  @Override
  public int[] getEscapeCodesForAscii() {
    return asciiEscapes;
  }

  @Override
  public SerializableString getEscapeSequence(int i) {
//    return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) i)));
    SerializedString serializedString = null;
    char charAt = (char) i; //emoji jackson parse 오류에 따른 예외 처리
    if (Character.isHighSurrogate(charAt) || Character.isLowSurrogate(charAt)) {
      StringBuilder sb = new StringBuilder();
      sb.append("\\u"); sb.append(String.format("%04x", i));
      serializedString = new SerializedString(sb.toString());
    } else {
      serializedString = new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString(charAt)));
    }
    return serializedString;
  }
}

```
