package com.mrhid6.zonusv2.item;

import com.mrhid6.zonusv2.init.ModBlocks;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemZoroCable extends ItemCableBase{

	public ItemZoroCable() {
		super();
		this.setUnlocalizedName("zorocable");
	}
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int par7, float par8, float par9, float par10) {
		return placeCable(itemStack, player, world, x, y, z, par7, ModBlocks.zoroCable);
	}
}
