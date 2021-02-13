package org.sus.command;

import java.util.Collections;
import java.util.List;

public interface ICommand {

    void handle(CommandContext ctx);
    String getName();

    default List<String> getAliases() {
        return Collections.emptyList();
    };
}
