package com.mrhid6.zonusv2.client.render;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class RenderZoroFurnace extends BaseBlockRender{

	private RenderBlocks renderBlocks;
	private int count;

	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float renderTick) {

		if(tileEntity !=null && tileEntity instanceof TileEntityZoroFurnace){

			TileEntityZoroFurnace tileEntityZoroFurnace = (TileEntityZoroFurnace) tileEntity;
			
			try {
				renderBlocks.renderBlockByRenderType(ModBlocks.zoroFurnace, (int) x, (int) y, (int) z);
			} catch (NullPointerException ex) {
				count++;
				if (count > 2) {
					throw ex;
				}
				renderBlocks = new RenderBlocks(tileEntityZoroFurnace.getWorldObj());
			}
			
			renderTileEntityZoroFurnace(tileEntityZoroFurnace, tileEntity.getWorldObj(), (int)x, (int)y, (int)z);

		}
	}

	public void renderTileEntityZoroFurnace(TileEntityZoroFurnace tileEntityZoroFurnace, World world, int x, int y, int z){
		
		if (tileEntityZoroFurnace.getWorldObj() == null) {
			GL11.glPushMatrix();
			{
				GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5f, (float) z + 0.5F);
				GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);
				renderBlocks.renderBlockByRenderType(ModBlocks.zoroFurnace, (int) x, (int) y, (int) z);
			}
			GL11.glPopMatrix();
			return;
		}
		
		renderBlocks.renderBlockAllFaces(ModBlocks.zoroFurnace, x, y, z);
	}



	private void scaleTranslateRotate(double x, double y, double z, ForgeDirection orientation)
	{
		if (orientation == ForgeDirection.NORTH)
		{
			GL11.glTranslated(x + 1, y+1, z);
			GL11.glRotatef(180F, 0F, 1F, 0F);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
		}
		else if (orientation == ForgeDirection.EAST)
		{
			GL11.glTranslated(x + 1, y+1, z + 1);
			GL11.glRotatef(90F, 0F, 1F, 0F);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
		}
		else if (orientation == ForgeDirection.SOUTH)
		{
			GL11.glTranslated(x, y+1, z + 1);
			GL11.glRotatef(0F, 0F, 1F, 0F);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
		}
		else if (orientation == ForgeDirection.WEST)
		{
			GL11.glTranslated(x, y+1, z);
			GL11.glRotatef(-90F, 0F, 1F, 0F);
			GL11.glRotatef(-90F, 1F, 0F, 0F);
		}
	}

}
