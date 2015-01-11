package com.mrhid6.zonusv2.configuration;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	public static void init(File configFile){
		
		// Create the configuration object from the given configuration file.
		Configuration config = new Configuration(configFile);
		
		boolean configValue = false;
		
		try{
			
			// Load the configuration file.
			config.load();
			
			// Read in properties from configuration file.
			configValue = config.get(Configuration.CATEGORY_GENERAL, "configValue", true, "Example Config").getBoolean(true);
			
		}catch(Exception e){
			
			// Log Exception.
		}finally{
			
			// Save the configuration file.
			config.save();
		}
		
		System.out.println(configValue);
	}
	
}
