package com.mrhid6.zonusv2.client.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;


public abstract class BaseBlockRender extends TileEntitySpecialRenderer{


	protected int adjustBrightness(int v, double d)
	{
		int r = 0xff & (v >> 16);
		int g = 0xff & (v >> 8);
		int b = 0xff & ( v );

		r *= d;
		g *= d;
		b *= d;

		r = Math.min( 255, Math.max( 0, r ) );
		g = Math.min( 255, Math.max( 0, g ) );
		b = Math.min( 255, Math.max( 0, b ) );

		return (r << 16) | (g << 8) | b;
	}

}
