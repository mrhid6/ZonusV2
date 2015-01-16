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

public class BRZoroFurnace extends BRZonus{

	@Override
	public int getRenderId() {
		return RenderIds.ZOROFURNACE;
	}

	@Override
	public boolean renderWorldBlock( IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer ) {
		
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		
		if(tileEntity == null || !(tileEntity instanceof TileEntityZoroFurnace)) return false;
		
		TileEntityZoroFurnace tileEntityZoroFurnace = (TileEntityZoroFurnace)tileEntity;
		
		ForgeDirection orientation = tileEntityZoroFurnace.getOrientation();
		
		renderer.renderStandardBlock(block, x, y, z);
		
		if(tileEntityZoroFurnace.getState() == 1){
			BlockLightTextures light = BlockLightTextures.ZoroFurnaceLights;
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
		}
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return true;
	}
}
