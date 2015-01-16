package com.mrhid6.zonusv2.init;

import java.util.ArrayList;

import com.mrhid6.zonusv2.utility.ItemHelper;

import net.minecraft.block.BlockStone;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes {
	
	public static void init(){
		
		ZoroFurnaceRecipes.init();
		
		//TODO
		//GameRegistry.addSmelting(ModBlocks.ZoriteOre, ModItems.zoroIngot, 0.5f);
		
		GameRegistry.addSmelting(new ItemStack(ModBlocks.zonusOres, 1, 0), new ItemStack(ModItems.zoroIngot), 0.5F);
		
		GameRegistry.addRecipe(new ItemStack(ModBlocks.machineBlock), "is","si", 's', new ItemStack(Blocks.stone), 'i', new ItemStack(ModItems.zoroIngot));
		GameRegistry.addRecipe(new ItemStack(ModBlocks.zoroFurnace), "sis","mfm","sms", 
				's', new ItemStack(Blocks.stone), 'm', new ItemStack(ModBlocks.machineBlock), 'f', new ItemStack(Blocks.furnace), 'i', new ItemStack(ModItems.zoroIngot));
		
	}
	
	public static class ZoroFurnaceRecipes{
		
		private static ArrayList<ZoroFurnaceRecpie> recipes = new ArrayList<ZoroFurnaceRecpie>();
		
		public static void init(){
			recipes.add(new ZoroFurnaceRecpie(new ItemStack(Items.iron_ingot), new ItemStack(ModItems.zoroIngot)));
		}
		
		public static ItemStack getResultFor(ItemStack itemStack){
			if(itemStack == null) return null;
			
			ItemStack result = null;
			
			for(int i=0;i<recipes.size();i++){
				ZoroFurnaceRecpie recipe = recipes.get(i);
				if(itemStack.isItemEqual(recipe.getInput())){
					result = recipe.getOutput();
					break;
				}
			}
			if(result==null){
				return null;
			}
			return ItemHelper.cloneItemStack(result,result.stackSize);
		}
		
		public static class ZoroFurnaceRecpie{
			
			private ItemStack input, output;
			
			public ZoroFurnaceRecpie(ItemStack input, ItemStack output) {
				this.input = input;
				this.output = output;
			}
			
			public ItemStack getInput(){
				return ItemHelper.cloneItemStack(input, input.stackSize);
			}
			
			public ItemStack getOutput(){
				return ItemHelper.cloneItemStack(output, output.stackSize);
			}
		}
	}
	
}
