package com.mrhid6.zonusv2.client.renderers;

import org.lwjgl.opengl.GL11;

import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.reference.RenderIds;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

public class BRZonusOres extends BRZonus {

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		super.renderInventoryBlock(block, metadata, modelID, renderer);

		BlockLightTextures light = null;

		if (metadata == 0) {
			light = BlockLightTextures.ZoroOreLights;
		}

		if (light != null) {
			Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			Tessellator.instance.setBrightness(14 << 20 | 14 << 4);

			Tessellator tessellator = Tessellator.instance;
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			tessellator.setBrightness(14 << 20 | 14 << 4);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D,
					light.getIcons()[0]);
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		int metadata = world.getBlockMetadata(x, y, z);

		renderer.renderStandardBlock(block, x, y, z);
		BlockLightTextures light = null;

		if (metadata == 0) {
			light = BlockLightTextures.ZoroOreLights;
		}

		if (light != null) {

			Tessellator.instance.setColorOpaque_F(1.0f, 1.0f, 1.0f);
			Tessellator.instance.setBrightness(14 << 20 | 14 << 4);

			renderer.renderFaceZPos(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceZNeg(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceXNeg(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceXPos(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceYPos(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceYNeg(block, x, y, z, light.getIcons()[0]);
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return RenderIds.ZONUSORES;
	}

}
