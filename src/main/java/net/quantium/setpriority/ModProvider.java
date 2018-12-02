package net.quantium.setpriority;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ModProvider.MODID, name = ModProvider.NAME, version = ModProvider.VERSION, acceptableRemoteVersions = "*")
public final class ModProvider
{
    public static final String MODID = "setpriority";
    public static final String NAME = "SetPriority";
    public static final String VERSION = "1.0";

    private static Logger logger;
    private static ModConfig config;

    public final static Logger logger() {
    	return logger;
    }
    
    public final static ModConfig config() {
    	return config;
    }
    
    @EventHandler
    public final void preInit(FMLPreInitializationEvent event)
    {
    	logger = event.getModLog();
        config = new ModConfig(event.getSuggestedConfigurationFile());
        
        trySetPriority(config().getPriority());
    }
    
    private static final void trySetPriority(EnumPriority prio) {
    	if(prio == EnumPriority.DEFAULT) {
    		logger().info("Setting priority is defaulted: skipping");
    		return;
    	}
    	
    	try {
    		if(!SysHelper.IS_WINDOWS) {
    			logger().error("Failed to set process priority: current OS is not Windows");
    			return;
    		}
    		
    		try {
    			long pid = SysHelper.getProcessID();
    			logger().info("Current PID is {}", pid);
    			
    			try {
    				logger().info("Trying to set priority to {}", prio.toString());
    				Process proc = SysHelper.setPriority(pid, prio.getId());
        			logger().info("WMIC process started successfully!");
        			int code = proc.waitFor();
        			logger().info("WMIC process completed with code {} ({})", code, SysHelper.getWMICCodeDescription(code));        	
        			
        		} catch (Throwable throwable) {
            		logger().error("Failed to set process priority: cannot set priority", throwable);
            	}
    			
    		} catch (Throwable throwable) {
        		logger().error("Failed to set process priority: cannot acquire PID", throwable);
        	}
    		
    	} catch (Throwable throwable) {
    		logger().error("Failed to set process priority: generic", throwable);
    	}
    }
}
