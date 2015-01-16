package com.mrhid6.zonusv2.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.mrhid6.zonusv2.client.textures.BlockLightTextures;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public abstract class BRZonus implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock( Block block, int metadata, int modelID, RenderBlocks renderer ) {
		Tessellator tessellator = Tessellator.instance;
	    GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, -1.0F, 0.0F);
	    renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 1.0F, 0.0F);
	    renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, -1.0F);
	    renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(0.0F, 0.0F, 1.0F);
	    renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(-1.0F, 0.0F, 0.0F);
	    renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
	    tessellator.draw();
	    tessellator.startDrawingQuads();
	    tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
	    tessellator.draw();
	    GL11.glTranslatef(0.5F, 0.5F, 0.5F);

	}
	
	protected void render3SidedLights(RenderBlocks renderer, Block block, IBlockAccess world, int x, int y, int z, ForgeDirection orientation, BlockLightTextures light){
		Tessellator tessellator = Tessellator.instance;
		
		Tessellator.instance.setColorOpaque_F( 1.0f, 1.0f, 1.0f );
		Tessellator.instance.setBrightness( 14 << 20 | 14 << 4 );
		
		switch(orientation){
		case SOUTH:
			renderer.renderFaceZPos(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceZNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXPos(block, x, y, z, light.getIcons()[1]);
			break;
		case NORTH:
			renderer.renderFaceZPos(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceZNeg(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceXNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXPos(block, x, y, z, light.getIcons()[1]);
			break;
		case EAST:
			renderer.renderFaceZPos(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceZNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXPos(block, x, y, z, light.getIcons()[0]);
			break;
		case WEST:				
			renderer.renderFaceZPos(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceZNeg(block, x, y, z, light.getIcons()[1]);
			renderer.renderFaceXNeg(block, x, y, z, light.getIcons()[0]);
			renderer.renderFaceXPos(block, x, y, z, light.getIcons()[1]);
			break;
		default:
			break;
		}
		
		renderer.renderFaceYNeg(block, x, y, z, light.getIcons()[2]);
		renderer.renderFaceYPos(block, x, y, z, light.getIcons()[2]);
		
		int bright = block.getMixedBrightnessForBlock(world, x, y, z);
		tessellator.setBrightness(bright);
	}
	
}
