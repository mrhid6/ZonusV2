package com.mrhid6.zonusv2.client.gui.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiZonus extends GuiContainer{
	
	protected int mousex;
	protected int mousey;
	
	public GuiZonus(Container container) {
		super(container);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer( int par1, int par2 ) {

		GL11.glDisable(2896);
		GL11.glDisable(2929);

		drawTooltips();

		GL11.glEnable(2896);
		GL11.glEnable(2929);
	}
	
	public void drawToolTip( String text ) {
		drawCreativeTabHoveringText(text, mousex, mousey);
	}
	
	protected abstract void drawTooltips();
	
	@Override
	public void handleMouseInput() {

		int x = Mouse.getEventX() * width / mc.displayWidth;
		int y = height - Mouse.getEventY() * height / mc.displayHeight - 1;

		mousex = (x - (width - xSize) / 2);
		mousey = (y - (height - ySize) / 2);

		super.handleMouseInput();
	}
	
	/*
	 * @params
	 *
	 *
	 */
	public boolean isHovering( int minX, int maxX, int minY, int maxY ) {
		return (mousex > minX && mousex < maxX && mousey > minY && mousey < maxY);
	}

}
