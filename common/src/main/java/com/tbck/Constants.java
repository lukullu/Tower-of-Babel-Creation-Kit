package com.tbck;

import com.tbck.data.entity.SegmentData;

import java.awt.*;
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
	
	// -. editor rendering .- \\
	Color TRANSPARENT = new Color(255, 255, 255, 0);
	Color SHAPE_HOVER = new Color(76, 76, 134, 200);
	Color SHAPE_SELECTED = new Color(55, 107, 73, 200);
	String NO_SEGMENT_TITLE = "no segment selected";
	
}
