package com.mrhid6.zonusv2.itemblocks;

import com.mrhid6.zonusv2.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockZonusOre extends ItemBlock {

	private final static String[] subNames = { "zoroore", "Trinium Ore", "Noxite Ore", "Stearillium Ore", "yellow", "lightGreen", "pink", "darkGrey", "lightGrey", "cyan", "purple", "blue", "brown", "green", "red", "black" };

	public ItemBlockZonusOre(Block b) {
		super(b);
		setHasSubtypes(true);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack) {
		return getUnlocalizedName()+"."+subNames[itemStack.getItemDamage()];
	}

	@Override
	public int getMetadata( int damageValue ) {
		return damageValue;
	}

}
