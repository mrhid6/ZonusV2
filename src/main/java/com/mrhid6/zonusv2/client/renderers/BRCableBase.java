package com.mrhid6.zonusv2.client.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;

import org.lwjgl.opengl.GL11;

import com.mrhid6.zonusv2.api.IMachineConnectable;
import com.mrhid6.zonusv2.api.IMachineSidedConnectable;
import com.mrhid6.zonusv2.block.cable.BlockCableBase;
import com.mrhid6.zonusv2.block.cable.BlockZoroCable;
import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.cable.TileEntityCableBase;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class BRCableBase implements ISimpleBlockRenderingHandler {

	@Override
	public int getRenderId() {
		return RenderIds.CABLEBASE;
	}

	public void renderCableEnd(Block block, RenderBlocks renderer,
			float offset, float Thickness, int x, int y, int z,
			ForgeDirection orientation) {
		renderer.renderAllFaces = true;
		float offX = orientation.offsetX / 2.0F;
		float offY = orientation.offsetY / 2.0F;
		float offZ = orientation.offsetZ / 2.0F;

		float centerX = 0.5F;
		float centerY = 0.5F;
		float centerZ = 0.5F;

		centerX += orientation.offsetX * -offset;
		centerY += orientation.offsetY * -offset;
		centerZ += orientation.offsetZ * -offset;

		float thickX = Math.abs(orientation.offsetX) > 0.1D ? 0.076F
				: Thickness / 2;
		float thickY = Math.abs(orientation.offsetY) > 0.1D ? 0.076F
				: Thickness / 2;
		float thickZ = Math.abs(orientation.offsetZ) > 0.1D ? 0.076F
				: Thickness / 2;

		renderer.setRenderBounds(centerX + offX - thickX, centerY + offY
				- thickY, centerZ + offZ - thickZ, centerX + offX + thickX,
				centerY + offY + thickY, centerZ + offZ + thickZ);

		Block tex = ModBlocks.machineBlock;

		GL11.glPushMatrix();
		{
			renderer.renderStandardBlock(tex, x, y, z);
		}
		GL11.glPopMatrix();
		renderer.renderAllFaces = false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {

		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if (!(tileEntity instanceof TileEntityCableBase)) {
			System.out.println(tileEntity);
			return true;
		}

		TileEntityCableBase cable = (TileEntityCableBase) tileEntity;

		float th = (float) cable.getCableThickness();
		float sp = (1.0F - th) / 2.0F;

		boolean[] connections = cable.getConnections();

		IIcon texture = block.getIcon(world, x, y, z, 0);

		IIcon lightTexture = null;

		if (block instanceof BlockCableBase) {
			lightTexture = BlockLightTextures.CableBaseLights.getIcons()[0];
		}

		if (block instanceof BlockZoroCable) {
			lightTexture = BlockLightTextures.ZoroCableLights.getIcons()[0];
		}

		double xD = x;
		double yD = y;
		double zD = z;

		int mask = 1;
		int connectivity = 0;
		int renderSide = 0;

		int[] invertedsides = new int[] { 5, 4, 1, 0, 3, 2 };
		int[] sides = new int[] { 4, 5, 0, 1, 2, 3 };
		for (int i = 0; i < 6; i++) {

			TileEntity neighbor = null;

			int xoffset = ForgeDirection.getOrientation(sides[i]).offsetX;
			int yoffset = ForgeDirection.getOrientation(sides[i]).offsetY;
			int zoffset = ForgeDirection.getOrientation(sides[i]).offsetZ;

			if ((cable.getWorldObj() != null)
					&& (cable.getWorldObj().blockExists(x + xoffset, y
							+ yoffset, z + zoffset))) {
				neighbor = cable.getWorldObj().getTileEntity(x + xoffset,
						y + yoffset, z + zoffset);
			}

			if ((neighbor != null)) {

				if (cable.canIConnectWithTileEntity(neighbor, ForgeDirection
						.getOrientation(sides[i]).getOpposite())) {
					connectivity |= mask;
					renderSide |= mask;
				}
			}

			mask *= 2;
		}

		for (int i = 0; i < 6; i++) {

			int x1 = x + ForgeDirection.getOrientation(sides[i]).offsetX;
			int y1 = y + ForgeDirection.getOrientation(sides[i]).offsetY;
			int z1 = z + ForgeDirection.getOrientation(sides[i]).offsetZ;

			TileEntity te1 = world.getTileEntity(x1, y1, z1);

			if (cable.canIConnectWithTileEntity(te1, ForgeDirection
					.getOrientation(sides[i]).getOpposite())) {
				if (te1 instanceof IMachineConnectable
						|| te1 instanceof IMachineSidedConnectable) {
					renderCableEnd(block, renderer, 0.076F, th + 0.125F, x, y,
							z, ForgeDirection.getOrientation(sides[i]));
				}
			}

		}

		renderCable(renderer, block, connectivity, renderSide, sp, th, texture,
				x, y, z, world, true);
		renderCable(renderer, block, connectivity, renderSide, sp, th,
				lightTexture, x, y, z, world, false);
		return true;
	}

	public void renderCable(RenderBlocks renderer, Block block,
			int connectivity, int renderSide, float sp, float th,
			IIcon texture, int x, int y, int z, IBlockAccess world,
			boolean useLighting) {
		Tessellator tessellator = Tessellator.instance;

		if (useLighting)
			tessellator.setBrightness(block.getMixedBrightnessForBlock(world,
					x, y, z));
		else {
			tessellator.setBrightness(14 << 20 | 14 << 4);
		}

		double xD = x;
		double yD = y;
		double zD = z;

		if (connectivity == 0) {
			block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderFaceZNeg(block, xD, yD, zD, texture);
			renderer.renderFaceZPos(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderFaceXNeg(block, xD, yD, zD, texture);
			renderer.renderFaceXPos(block, xD, yD, zD, texture);
		} else if (connectivity == 3) {
			block.setBlockBounds(0.0F, sp, sp, 1.0F, sp + th, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderFaceZNeg(block, xD, yD, zD, texture);
			renderer.renderFaceZPos(block, xD, y, zD, texture);

			if ((renderSide & 0x1) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x2) != 0) {
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 12) {
			block.setBlockBounds(sp, 0.0F, sp, sp + th, 1.0F, sp + th);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
			renderer.renderFaceZNeg(block, xD, yD, zD, texture);
			renderer.renderFaceZPos(block, xD, y, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderFaceXNeg(block, xD, yD, zD, texture);
			renderer.renderFaceXPos(block, xD, yD, zD, texture);

			if ((renderSide & 0x4) != 0) {
				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
			}

			if ((renderSide & 0x8) != 0) {
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
			}
		} else if (connectivity == 48) {
			block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, 1.0F);
			renderer.setRenderBoundsFromBlock(block);

			tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
			renderer.renderFaceYNeg(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
			renderer.renderFaceYPos(block, xD, yD, zD, texture);
			tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
			renderer.renderFaceXNeg(block, xD, yD, zD, texture);
			renderer.renderFaceXPos(block, xD, yD, zD, texture);

			if ((renderSide & 0x10) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, y, zD, texture);
			}

			if ((renderSide & 0x20) != 0) {
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZPos(block, xD, yD, zD, texture);
			}
		} else {
			if ((connectivity & 0x1) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(0.0F, sp, sp, sp, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, yD, zD, texture);
				renderer.renderFaceZPos(block, xD, y, zD, texture);

				if ((renderSide & 0x1) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderer.renderFaceXNeg(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x2) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp + th, sp, sp, 1.0F, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, yD, zD, texture);
				renderer.renderFaceZPos(block, xD, y, zD, texture);

				if ((renderSide & 0x2) != 0) {
					tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
					renderer.renderFaceXPos(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x4) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, 0.0F, sp, sp + th, sp, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, yD, zD, texture);
				renderer.renderFaceZPos(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x4) != 0) {
					tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
					renderer.renderFaceYNeg(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x8) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp + th, sp, sp + th, 1.0F, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, yD, zD, texture);
				renderer.renderFaceZPos(block, xD, y, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x8) != 0) {
					tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
					renderer.renderFaceYPos(block, xD, yD, zD, texture);
				}

			}

			if ((connectivity & 0x10) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZNeg(block, xD, y, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, 0.0F, sp + th, sp + th, sp);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x10) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderer.renderFaceZNeg(block, xD, y, zD, texture);
				}

			}

			if ((connectivity & 0x20) == 0) {
				block.setBlockBounds(sp, sp, sp, sp + th, sp + th, sp + th);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
				renderer.renderFaceZPos(block, xD, yD, zD, texture);
			} else {
				block.setBlockBounds(sp, sp, sp + th, sp + th, sp + th, 1.0F);
				renderer.setRenderBoundsFromBlock(block);

				tessellator.setColorOpaque_F(0.5F, 0.5F, 0.5F);
				renderer.renderFaceYNeg(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
				renderer.renderFaceYPos(block, xD, yD, zD, texture);
				tessellator.setColorOpaque_F(0.6F, 0.6F, 0.6F);
				renderer.renderFaceXNeg(block, xD, yD, zD, texture);
				renderer.renderFaceXPos(block, xD, yD, zD, texture);

				if ((renderSide & 0x20) != 0) {
					tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
					renderer.renderFaceZPos(block, xD, yD, zD, texture);
				}
			}
		}

		block.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		renderer.setRenderBoundsFromBlock(block);
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		// TODO Auto-generated method stub
		return false;
	}
}
