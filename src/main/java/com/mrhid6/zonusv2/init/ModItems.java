package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.item.ItemZonus;
import com.mrhid6.zonusv2.item.ItemZoroIngot;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {
	
	public static final ItemZonus zoroIngot = new ItemZoroIngot();
	
	public static void init(){
		
		GameRegistry.registerItem(zoroIngot, "zonusIngot");
	}
	
}
