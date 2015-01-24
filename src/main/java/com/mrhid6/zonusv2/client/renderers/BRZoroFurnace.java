package com.mrhid6.zonusv2.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

public class BRZoroFurnace extends BRZonus {

	@Override
	public int getRenderId() {
		return RenderIds.ZOROFURNACE;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity == null
				|| !(tileEntity instanceof TileEntityZoroFurnace))
			return false;

		TileEntityZoroFurnace tileEntityZoroFurnace = (TileEntityZoroFurnace) tileEntity;

		ForgeDirection orientation = tileEntityZoroFurnace.getOrientation();

		renderer.renderStandardBlock(block, x, y, z);

		if (tileEntityZoroFurnace.getState() == 1) {
			BlockLightTextures light = BlockLightTextures.ZoroFurnaceLights;
			render3SidedLights(renderer, block, world, x, y, z, orientation,
					light);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return true;
	}
}
