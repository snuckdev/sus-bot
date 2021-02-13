package org.sus.object;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LogChannel {

    private String guildId;
    private String channelId;

}
