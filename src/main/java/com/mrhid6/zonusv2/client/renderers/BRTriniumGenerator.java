package com.mrhid6.zonusv2.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.TileEntityTriniumGenerator;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

public class BRTriniumGenerator extends BRZonus {

	@Override
	public int getRenderId() {
		return RenderIds.TRINIUMGENERATOR;
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (tileEntity == null
				|| !(tileEntity instanceof TileEntityTriniumGenerator))
			return false;

		TileEntityTriniumGenerator tileEntityTrinGen = (TileEntityTriniumGenerator) tileEntity;

		ForgeDirection orientation = tileEntityTrinGen.getOrientation();

		renderer.renderStandardBlock(block, x, y, z);

		if (tileEntityTrinGen.getState() == 1) {
			BlockLightTextures light = BlockLightTextures.TriniumGeneratorLights;

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
