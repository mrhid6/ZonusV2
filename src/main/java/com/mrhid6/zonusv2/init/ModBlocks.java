package com.mrhid6.zonusv2.init;

import com.mrhid6.zonusv2.block.BlockMachine;
import com.mrhid6.zonusv2.block.BlockZonus;
import com.mrhid6.zonusv2.block.BlockZoroFurnace;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;


@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	
	public static final BlockZonus machineBlock = new BlockMachine();
	public static final BlockZonus zoroFurnace = new BlockZoroFurnace();
	
	public static void init(){
		
		GameRegistry.registerBlock(machineBlock, "machine");
		GameRegistry.registerBlock(zoroFurnace, "zorofurnace");
	}
	
}
