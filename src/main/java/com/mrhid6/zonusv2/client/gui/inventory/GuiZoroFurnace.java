package com.mrhid6.zonusv2.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import com.mrhid6.zonusv2.inventory.ContainerZoroFurnace;
import com.mrhid6.zonusv2.reference.Reference;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

public class GuiZoroFurnace extends GuiZonus{

	private TileEntityZoroFurnace tileEntityZoroFurnace;

	public GuiZoroFurnace(InventoryPlayer inventoryPlayer, TileEntityZoroFurnace tileEntityZoroFurnace) {
		super(new ContainerZoroFurnace(inventoryPlayer, tileEntityZoroFurnace));
		this.tileEntityZoroFurnace = tileEntityZoroFurnace;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		// draw your Gui here, only thing you need to change is the path
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		mc.getTextureManager().bindTexture(new ResourceLocation(Reference.MOD_ID,"textures/gui/zorofurnace.png"));
		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		int l = tileEntityZoroFurnace.getScaledEnergyStored(102);
		if (l > 0) {
			drawTexturedModalRect(x + 42, y + 61, 0, 166, l, 10);
		}

		l = tileEntityZoroFurnace.getScaledProgress(24);
		if (l > 0) {
			drawTexturedModalRect(x + 79, y + 33, 176, 16, l, 16);
		}

		if (tileEntityZoroFurnace.getState() == 1) {
			drawTexturedModalRect(x + 56, y + 42, 176, 0, 16, 16);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer( int param1, int param2 ) {
		fontRendererObj.drawString("Zoro Furnace", 50, 6, 4210752);
		fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, ySize - 96 + 2, 4210752);
		super.drawGuiContainerForegroundLayer(param1, param2);
	}
	
	@Override
	protected void drawTooltips() {

		if (isHovering(42, 144, 61, 71)) {
			drawToolTip(tileEntityZoroFurnace.getStoredPower() + "z " + "/ " + tileEntityZoroFurnace.getMaxPower() + "z");
		}

		/*if (isHovering(10, 26, 16, 32)) {
			drawToolTip(container.tileEntity.getModeText());

		} else if (isHovering(10, 26, 34, 50)) {
			drawToolTip(container.tileEntity.getColourText());
		}*/

	}

}
