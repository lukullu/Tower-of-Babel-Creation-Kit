package com.tbck;

import java.nio.file.Path;

public interface Constants {

	String GAME_NAME = "Tower of Babel Creation Kit";
	String WINDOW_TITLE = "ToBCK";
	
	// -. user data .-\\
	Path USER_DATA = Path.of("userData");
	Path ENTITY_TEMPLATE_DIRECTORY = USER_DATA.resolve("entityTemplates/");
	
	// -. Entity Editor .- \\
	String ENTITY_TEMPLATE_FILE_EXTENSION = ".psff"; // .etf maybe? or just a "universal" file-extension for all things we store? .towerdata?
	String ENTITY_TEMPLATE_FILE_TYPE_DESCRIPTION = "Entity-Template File";
	
}
