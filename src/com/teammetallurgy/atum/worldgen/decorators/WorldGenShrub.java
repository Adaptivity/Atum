package com.teammetallurgy.atum.worldgen.decorators;

import java.util.Random;

import com.teammetallurgy.atum.blocks.AtumBlocks;

import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenShrub extends WorldGenerator {

	private int deadBushID;
	private int groupSize;

	public WorldGenShrub(int par1, int par2) {
		this.deadBushID = par1;
		this.groupSize = par2;
	}

	@Override
	public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5) {
		int size = par2Random.nextInt(this.groupSize / 2) + this.groupSize / 2;

		for(int i1 = 0; i1 < size; ++i1) {
			byte range = 6;
			int x = par3 + par2Random.nextInt(range + 1) - range / 2;
			int z = par5 + par2Random.nextInt(range + 1) - range / 2;
			int y = par1World.getHeightValue(x, z);
			if(par1World.isAirBlock(x, y, z) && AtumBlocks.BLOCK_SHRUB.canBlockStay(par1World, x, y, z)) {
				par1World.setBlock(x, y, z, this.deadBushID);
			}
		}

		return true;
	}
}
