package com.mrhid6.zonusv2;

import com.mrhid6.zonusv2.configuration.ConfigHandler;
import com.mrhid6.zonusv2.proxy.IProxy;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION)
public class ZonusV2 {

	@Mod.Instance(Reference.MOD_ID)
	public static ZonusV2 instance;
	
	@SidedProxy(clientSide="com.mrhid6.zonusv2.proxy.ClientProxy", serverSide="com.mrhid6.zonusv2.proxy.ServerProxy")
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		
	}
	
}
