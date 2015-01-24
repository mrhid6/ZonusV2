package com.mrhid6.zonusv2.block.cable;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mrhid6.zonusv2.init.ModItems;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityZoroCable;

public class BlockZoroCable extends BlockCableBase {

	public BlockZoroCable() {
		super();

		this.setBlockName("zorocable");
	}

	@Override
	public Item getItem(World world, int x, int y, int z) {
		return ModItems.zorocable;
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_,
			int p_149650_3_) {
		// TODO Auto-generated method stub
		return ModItems.zorocable;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int metadata) {
		return new TileEntityZoroCable();
	}
}
