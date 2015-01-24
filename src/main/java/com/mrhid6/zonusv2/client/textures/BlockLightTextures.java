package com.mrhid6.zonusv2.client.textures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.mrhid6.zonusv2.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum BlockLightTextures {

	ZoroFurnaceLights("zorofurnace", 3), TriniumGeneratorLights(
			"tringenerator", 3), CableBaseLights("cablebase", 1), ZoroCableLights(
			"zorocable", 1), ZoroOreLights("multiOre.zoroore", 1);

	final private String name;
	public IIcon[] icons;

	public ResourceLocation GuiTexture() {
		return new ResourceLocation(Reference.MOD_ID, "textures/blocks/"
				+ this.name());
	}

	public String getName() {
		return this.name;
	}

	private BlockLightTextures(String name, int iconcount) {
		this.name = name + "_lights";
		this.icons = new IIcon[iconcount];
	}

	public IIcon[] getIcons() {
		return this.icons;
	}

	public void registerIcon(TextureMap map) {
		if (icons.length == 1) {
			this.icons[0] = map
					.registerIcon(Reference.MOD_ID + ":" + this.name);
		} else {
			for (int i = 0; i < icons.length; i++) {
				this.icons[i] = map.registerIcon(Reference.MOD_ID + ":"
						+ this.name + "_" + i);
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getMissing() {
		return ((TextureMap) Minecraft.getMinecraft().getTextureManager()
				.getTexture(TextureMap.locationBlocksTexture))
				.getAtlasSprite("missingno");
	}

}
