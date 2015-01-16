package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityZoroCable;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init(){
		
		GameRegistry.registerTileEntity(TileEntityZoroFurnace.class, "tile.zorofurnace");
		GameRegistry.registerTileEntity(TileEntityCableBase.class, "tile.cablebase");
		GameRegistry.registerTileEntity(TileEntityZoroCable.class, "tile.zorocable");
	}
}
