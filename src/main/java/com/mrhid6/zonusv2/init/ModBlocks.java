package com.mrhid6.zonusv2.init;

import net.minecraft.item.ItemStack;

import com.mrhid6.zonusv2.block.BlockMachine;
import com.mrhid6.zonusv2.block.BlockZonus;
import com.mrhid6.zonusv2.block.BlockZoroFurnace;
import com.mrhid6.zonusv2.block.cable.BlockCableBase;
import com.mrhid6.zonusv2.block.cable.BlockZoroCable;
import com.mrhid6.zonusv2.block.ore.BlockZonusOre;
import com.mrhid6.zonusv2.itemblocks.ItemBlockZonusOre;
import com.mrhid6.zonusv2.reference.Materials;
import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.registry.GameRegistry;


@GameRegistry.ObjectHolder(Reference.MOD_ID)
public class ModBlocks {
	
	// Ores
	public static BlockZonus zonusOres = new BlockZonusOre();
	
	// Machines
	public static final BlockZonus machineBlock = new BlockMachine();
	public static final BlockZonus zoroFurnace = new BlockZoroFurnace();
	
	// Cables
	public static final BlockCableBase cableBase = new BlockCableBase();
	public static final BlockCableBase zoroCable = new BlockZoroCable();
	
	public static void init(){
		
		GameRegistry.registerBlock(zonusOres, ItemBlockZonusOre.class, "zonusOres");
		Materials.ZoroOre = new ItemStack(zonusOres,1,0);
		
		GameRegistry.registerBlock(machineBlock, "machine");
		GameRegistry.registerBlock(zoroFurnace, "zorofurnace");
		
		
		GameRegistry.registerBlock(cableBase, "cableBase");
		GameRegistry.registerBlock(zoroCable, "zoroCable");
	}
	
}
