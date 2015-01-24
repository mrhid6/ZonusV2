package com.mrhid6.zonusv2.world;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;

import com.mrhid6.zonusv2.init.ModBlocks;
import com.mrhid6.zonusv2.reference.Materials;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGenBase implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		if (world.isRemote) {
			return;
		}

		switch (world.provider.dimensionId) {
		// nether
		case -1:
			break;

		case 0:
			genZoroOre(world, random, chunkX * 16, chunkZ * 16);
			break;

		case 1:
			// generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	private void genZoroOre(World world, Random rand, int chunkX, int chunkZ) {
		for (int k = 0; k < 10; k++) {
			int firstBlockXCoord = chunkX + rand.nextInt(16);
			int firstBlockYCoord = rand.nextInt(64);
			int firstBlockZCoord = chunkZ + rand.nextInt(16);
			(new WorldGenMinable(ModBlocks.zonusOres,
					Materials.ZoroOre.getItemDamage(), 6, Blocks.stone))
					.generate(world, rand, firstBlockXCoord, firstBlockYCoord,
							firstBlockZCoord);
		}
	}

}
