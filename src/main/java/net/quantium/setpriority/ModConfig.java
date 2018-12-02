package net.quantium.setpriority;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public final class ModConfig {
	
	public static final String CONFIG_VERSION = "1.0";
	
	public static final String PRIORITY_NAME = "priority";
	public static final String PRIORITY_CATEGORY = Configuration.CATEGORY_GENERAL;
	public static final String PRIORITY_DEFAULT = EnumPriority.LOW.toString();
	public static final String PRIORITY_COMMENT = "Desired priority of minecraft process. Must be one of these: " + EnumPriority.getNames();
	
	private final Configuration config;
	
	public ModConfig(File file) {
		this.config = new Configuration(file, CONFIG_VERSION);
		load();
	}
	
	private EnumPriority priority;
	
	private final void load() {
		this.priority = EnumPriority.parse(this.config.getString(PRIORITY_NAME, PRIORITY_CATEGORY, PRIORITY_DEFAULT, PRIORITY_COMMENT));
		this.config.save();
	}
	
	public final EnumPriority getPriority() {
		return this.priority;
	}
}
