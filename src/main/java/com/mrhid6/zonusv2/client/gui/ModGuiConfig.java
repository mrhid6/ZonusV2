package com.mrhid6.zonusv2.client.gui;

import com.mrhid6.zonusv2.handler.ConfigHandler;
import com.mrhid6.zonusv2.reference.Reference;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import cpw.mods.fml.client.config.GuiConfig;

public class ModGuiConfig extends GuiConfig{
	
	public ModGuiConfig(GuiScreen guiscreen){
		super(guiscreen,new ConfigElement(ConfigHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
				Reference.MOD_ID,
				false,
				false,
				GuiConfig.getAbridgedConfigPath(ConfigHandler.config.toString()));
	}
	
}
