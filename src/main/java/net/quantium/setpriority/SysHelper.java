package net.quantium.setpriority;

import java.io.IOException;

import org.apache.commons.lang3.SystemUtils;

/**
 * Just set of hacks
 */
public final class SysHelper {
	
	public static final boolean IS_WINDOWS = SystemUtils.IS_OS_WINDOWS;
	
	public static final long getProcessID() {
	    String processName =
	      java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
	    return Long.parseLong(processName.split("@")[0]);
	}
	
	public static final Process setPriority(long pid, int priority) throws IOException, InterruptedException {
		return new ProcessBuilder("wmic", "process", "where", "ProcessId=" + pid, "CALL", "setpriority", Integer.toString(priority)).start();	
	}

	public static final String getWMICCodeDescription(int code) {
		switch(code) {
			case 0: return "success";
			case 2: return "access denied";
			case 3: return "insufficient privilege";
			case 8: return "unknown failure";
			case 9: return "path not found";
			case 21: return "invalid parameter";
			case -2147217385: return "invalid request";
			default: return "undefined";
		}
	}
}
