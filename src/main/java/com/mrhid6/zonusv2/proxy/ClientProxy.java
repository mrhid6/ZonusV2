package com.mrhid6.zonusv2.proxy;

import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.common.MinecraftForge;

import com.mrhid6.zonusv2.client.render.RenderZoroFurnace;
import com.mrhid6.zonusv2.client.renderers.BRCableBase;
import com.mrhid6.zonusv2.client.renderers.BRTriniumGenerator;
import com.mrhid6.zonusv2.client.renderers.BRZonusOres;
import com.mrhid6.zonusv2.client.renderers.BRZoroFurnace;
import com.mrhid6.zonusv2.client.textures.BlockLightTextures;
import com.mrhid6.zonusv2.reference.RenderIds;
import com.mrhid6.zonusv2.tileentity.TileEntityZoroFurnace;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {

	public void init() {
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void initRenderingAndTextures() {
		RenderIds.ZOROFURNACE = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.CABLEBASE = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.ZONUSORES = RenderingRegistry.getNextAvailableRenderId();
		RenderIds.TRINIUMGENERATOR = RenderingRegistry
				.getNextAvailableRenderId();

		RenderingRegistry.registerBlockHandler(RenderIds.ZOROFURNACE,
				new BRZoroFurnace());
		RenderingRegistry.registerBlockHandler(RenderIds.CABLEBASE,
				new BRCableBase());
		RenderingRegistry.registerBlockHandler(RenderIds.ZONUSORES,
				new BRZonusOres());
		RenderingRegistry.registerBlockHandler(RenderIds.TRINIUMGENERATOR,
				new BRTriniumGenerator());

		// ClientRegistry.bindTileEntitySpecialRenderer(TileEntityZoroFurnace.class,
		// new RenderZoroFurnace());
	}

	@SubscribeEvent
	public void updateTextureSheet(TextureStitchEvent.Pre ev) {

		if (ev.map.getTextureType() == 0) {
			for (BlockLightTextures et : BlockLightTextures.values())
				et.registerIcon(ev.map);
		}
	}

}
