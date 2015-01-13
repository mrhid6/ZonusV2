package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.tileentity.TileEntityZonus;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init(){
		
		GameRegistry.registerTileEntity(TileEntityZoroFurnace.class, "tile.zorofurnace");
	}
}
