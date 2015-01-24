package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.tileentity.TileEntityTriniumGenerator;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityZoroCable;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileEntities {
	public static void init() {

		// Register Machines
		GameRegistry.registerTileEntity(TileEntityZoroFurnace.class,
				"tile.zorofurnace");
		GameRegistry.registerTileEntity(TileEntityTriniumGenerator.class,
				"tile.tringenerator");

		// Register Cables
		GameRegistry.registerTileEntity(TileEntityCableBase.class,
				"tile.cablebase");
		GameRegistry.registerTileEntity(TileEntityZoroCable.class,
				"tile.zorocable");
	}
}
