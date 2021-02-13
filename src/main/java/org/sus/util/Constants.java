package org.sus.util;

import org.sus.Main;

public class Constants {

    public static final String PREFIX = Main.getDotenv().get("PREFIX");
    public static final String TOKEN = Main.getDotenv().get("TOKEN");
    public static final String OWNER_ID = Main.getDotenv().get("OWNER_ID");

}
