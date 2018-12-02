package net.quantium.setpriority;

import java.util.ArrayList;

public enum EnumPriority {
	DEFAULT		(-1, 	"default"), //up to OS to decide what priority will be used (mod just doesn't set priority)
	LOW			(64, 	"low"),
	BELOW_NORMAL(16384, "below_normal"),
	NORMAL		(32, 	"normal"),
	ABOVE_NORMAL(32768, "above_normal"),
	HIGH		(128, 	"high"),
	REAL_TIME	(256, 	"real_time");
	
	private final int id;
	private final String name;
	
	private EnumPriority(int id, String name) {
		this.id = id;
		this.name = normalizeString(name);
	}
	
	public final int getId() {
		return this.id;
	}
	
	public final String getName() {
		return this.name;
	}
	
	public final String toString() {
		return getName();
	}
	
	private static final String normalizeString(String input) {
		if(input == null) return "";
		return input.toLowerCase().trim();
	}
	
	public static final EnumPriority parse(String name) {
		name = normalizeString(name);
		
		for(EnumPriority prio : values()) {
			if(name.equals(prio.getName())) {
				return prio;
			}
		}
		
		throw new java.lang.IllegalArgumentException("Cannot parse priority: " + name);
	}

	public static final String getNames() {
		ArrayList<String> strings = new ArrayList<String>();
		for(EnumPriority prio : values()) {
			strings.add(prio.getName());
		}
		
		return String.join(", ", strings);
	}
}
