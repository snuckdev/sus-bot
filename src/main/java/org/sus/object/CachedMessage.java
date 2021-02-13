package org.sus.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CachedMessage {

    private String messageId;
    private String rawContent;
    private String author;


}
