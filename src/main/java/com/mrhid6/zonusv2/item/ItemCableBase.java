package com.mrhid6.zonusv2.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.mrhid6.zonusv2.init.ModBlocks;

public class ItemCableBase extends ItemZonus{
	
	
	public ItemCableBase() {
		super();
		this.setUnlocalizedName("cablebase");
	}
	
	@Override
	public boolean onItemUse( ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10 ) {
		return placeCable(itemStack, player, world, x, y, z, par7, ModBlocks.cableBase);
	}
	
	protected boolean placeCable(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, Block cableBlock){
		if (world.getBlock(x, y, z) != Blocks.snow && world.getBlock(x, y, z) != Blocks.snow_layer) {
			if (par7 == 0) {
				--y;
			}

			if (par7 == 1) {
				++y;
			}

			if (par7 == 2) {
				--z;
			}

			if (par7 == 3) {
				++z;
			}

			if (par7 == 4) {
				--x;
			}

			if (par7 == 5) {
				++x;
			}

			if (!world.isAirBlock(x, y, z)) {
				return false;
			}
		}

		if (!player.canPlayerEdit(x, y, z, par7, itemStack)) {
			return false;
		} else {
			if (cableBlock.canPlaceBlockAt(world, x, y, z)) {
				--itemStack.stackSize;
				world.setBlock(x, y, z, cableBlock);
				world.playSoundAtEntity(player, "step.cloth", 1.0f, 1.0f);
			}

			return true;
		}
	}
}
