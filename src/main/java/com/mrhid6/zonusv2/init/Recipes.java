package com.mrhid6.zonusv2.init;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void init(){
		
		GameRegistry.addRecipe(new ItemStack(ModItems.zoroIngot), " s ","sss", " s ", 's', new ItemStack(Items.iron_ingot));
		GameRegistry.addShapelessRecipe(new ItemStack(ModBlocks.machine), new ItemStack(ModItems.zoroIngot), new ItemStack(ModItems.zoroIngot), new ItemStack(new BlockStone()));
	}
	
}
