package com.mrhid6.zonusv2;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;

import com.mrhid6.zonusv2.handler.ConfigHandler;
import com.mrhid6.zonusv2.handler.GuiHandler;
import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.init.ModItems;
import com.mrhid6.zonusv2.init.Recipes;
import com.mrhid6.zonusv2.init.TileEntities;
import com.mrhid6.zonusv2.network.PacketHandler;
import com.mrhid6.zonusv2.proxy.IProxy;
import com.mrhid6.zonusv2.reference.Reference;
import com.mrhid6.zonusv2.utility.LogHelper;
import com.mrhid6.zonusv2.world.WorldGenBase;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.MOD_VERSION, guiFactory = Reference.GUI_FACTOR_CLASS)
public class ZonusV2 {

	@Mod.Instance(Reference.MOD_ID)
	public static ZonusV2 instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static IProxy proxy;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		FMLCommonHandler.instance().bus().register(new ConfigHandler());
		
		proxy.init();

		PacketHandler.init();
		
		ModItems.init();
		ModBlocks.init();
		
		LogHelper.info("Pre Initialisation Complete!");
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event){
		
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
		
		TileEntities.init();
		
		proxy.initRenderingAndTextures();
		
		Recipes.init();
		
		GameRegistry.registerWorldGenerator(new WorldGenBase(), 1);
		LogHelper.info("Initialisation Complete!");
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event){
		LogHelper.info("Post Initialisation Complete!");
	}
	
}
