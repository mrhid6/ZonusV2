package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.item.ItemCableBase;
import com.mrhid6.zonusv2.item.ItemZonus;
import com.mrhid6.zonusv2.item.ItemZonusDebug;
import com.mrhid6.zonusv2.item.ItemZoroCable;
import com.mrhid6.zonusv2.item.ItemZoroIngot;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;

@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModItems {

	public static final ItemZonus zoroIngot = new ItemZoroIngot();
	public static final ItemZonus zonusDebug = new ItemZonusDebug();
	public static final ItemZonus cablebase = new ItemCableBase();
	public static final ItemZonus zorocable = new ItemZoroCable();

	public static void init() {

		GameRegistry.registerItem(zoroIngot, "zonusIngot");
		GameRegistry.registerItem(zonusDebug, "zonusdebug");
		GameRegistry.registerItem(cablebase, "cablebase");
		GameRegistry.registerItem(zorocable, "zorocable");
	}

}
