package com.teammetallurgy.atum.worldgen.decorators;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.IPlantable;

import com.teammetallurgy.atum.AtumLoot;
import com.teammetallurgy.atum.blocks.AtumBlocks;

public class WorldGenOasis extends WorldGenerator {

	private final int minTreeHeight;
	private final int metaWood;
	private final int metaLeaves;

	public WorldGenOasis(boolean par1) {
		this(par1, 4, 0, 0);
	}

	public WorldGenOasis(boolean par1, int par2, int par3, int par4) {
		super(par1);
		this.minTreeHeight = par2;
		this.metaWood = par3;
		this.metaLeaves = par4;
	}

	@Override
	public boolean generate(World world, Random par2Random, int par3, int par4, int par5) {
		int width = par2Random.nextInt(6) + 6;
		int depth = par2Random.nextInt(5) + 5;
		int id = world.getBlockId(par3, par4 - 1, par5);
		if(id != AtumBlocks.BLOCK_SAND.blockID) {
			return false;
		} else {
			id = world.getBlockId(par3 + width, world.getHeightValue(par3 + width, par5) - 1, par5);
			if(id != AtumBlocks.BLOCK_SAND.blockID) {
				return false;
			} else {
				id = world.getBlockId(par3, world.getHeightValue(par3, par5 + depth) - 1, par5 + depth);
				if(id != AtumBlocks.BLOCK_SAND.blockID) {
					return false;
				} else {
					id = world.getBlockId(par3 + width, world.getHeightValue(par3 + width, par5 + depth) - 1, par5 + depth);
					if(id != AtumBlocks.BLOCK_SAND.blockID) {
						return false;
					} else {
						int minHeight = world.getHeightValue(par3, par5);
						int maxHeight = world.getHeightValue(par3, par5);
						int height = world.getHeightValue(par3 + width, par5);
						if(height < minHeight) {
							minHeight = height;
						} else if(height > maxHeight) {
							maxHeight = height;
						}

						height = world.getHeightValue(par3, par5 + depth);
						if(height < minHeight) {
							minHeight = height;
						} else if(height > maxHeight) {
							maxHeight = height;
						}

						height = world.getHeightValue(par3 + width, par5 + depth);
						if(height < minHeight) {
							minHeight = height;
						} else if(height > maxHeight) {
							maxHeight = height;
						}

						int papyrus;
						int i;
						int z;
						int dx;
						int y;
						if(maxHeight - minHeight < 6) {
							float treeCount = (float) width / 2.0F;
							float chest = (float) depth / 2.0F;

							for(papyrus = (int) (0.0F - treeCount - 6.0F); (float) papyrus <= treeCount + 6.0F; ++papyrus) {
								for(i = (int) (0.0F - chest - 6.0F); (float) i <= chest + 6.0F; ++i) {
									float x = (float) (papyrus * papyrus) / (treeCount * treeCount) + (float) (i * i) / (chest * chest);
									if(x <= 1.0F) {
										z = world.getHeightValue(papyrus + par3, i + par5);
										if(world.getBlockId(papyrus + par3, z - 1, i + par5) == AtumBlocks.BLOCK_SAND.blockID) {
											world.setBlock(papyrus + par3, z - 1, i + par5, Block.waterStill.blockID);
											if((double) x < 0.6D) {
												z = world.getHeightValue(papyrus + par3, i + par5);
												world.setBlock(papyrus + par3, z - 2, i + par5, Block.waterStill.blockID);
											}
										}
									} else {
										x = (float) (papyrus * papyrus) / ((treeCount + 4.0F) * (treeCount + 4.0F)) + (float) (i * i) / ((chest + 4.0F) * (chest + 4.0F));
										z = world.getHeightValue(papyrus + par3, i + par5);
										if(world.getBlockId(papyrus + par3, z - 1, i + par5) == AtumBlocks.BLOCK_SAND.blockID && x < 1.0F) {
											world.setBlock(papyrus + par3, z - 1, i + par5, AtumBlocks.BLOCK_FERTILESOIL.blockID);
											if((double) x < 0.3D && par2Random.nextInt(8) == 0) {
												for(y = -1; y <= 1; ++y) {
													for(dx = -1; dx <= 1; ++dx) {
														if(AtumBlocks.BLOCK_PAPYRUS.canBlockStay(world, par3 + papyrus + y, z, par5 + i + dx)) {
															world.setBlock(papyrus + par3 + y, z, i + par5 + dx, AtumBlocks.BLOCK_PAPYRUS.blockID);
														}
													}
												}
											}
										}
									}
								}
							}
						}

						int var24 = 0;

						for(int var26 = 0; var26 < 6; ++var26) {
							papyrus = par2Random.nextInt(width);
							i = par2Random.nextInt(depth);
							id = world.getBlockId(par3 + papyrus, world.getHeightValue(par3 + papyrus, par5 + i) - 1, par5 + i);
							if(id == AtumBlocks.BLOCK_FERTILESOIL.blockID) {
								(new WorldGenPalm(true, 5, 0, 0)).generate(world, par2Random, par3 + papyrus, world.getHeightValue(par3 + papyrus, par5 + i), par5 + i);
								++var24;
							}

							if(var24 > 2) {
								break;
							}
						}

						boolean var25 = false;
						boolean var27 = false;

						for(i = 0; i < 10; ++i) {
							int var28 = par2Random.nextInt(width);
							z = par2Random.nextInt(depth);
							y = world.getHeightValue(par3 + var28, par5 + z);
							id = world.getBlockId(par3 + var28, y - 1, par5 + z);
							if(!var25 && id == AtumBlocks.BLOCK_FERTILESOIL.blockID) {
								world.setBlock(par3 + var28, y, par5 + z, Block.chest.blockID);
								TileEntity var29 = world.getBlockTileEntity(par3 + var28, world.getHeightValue(par3 + var28, par5 + z), par5 + z);
								AtumLoot.fillChest((IInventory) var29, 5, 0.2F);
								var25 = true;
							} else {
								int dz;
								if(!var27 && Block.blocksList[id].canSustainPlant(world, par3 + var28, y, par5 + z, ForgeDirection.UP, (IPlantable) ((IPlantable) AtumBlocks.BLOCK_PAPYRUS))) {
									for(dx = -1; dx <= 1; ++dx) {
										for(dz = -1; dz <= 1; ++dz) {
											if(Block.blocksList[id].canSustainPlant(world, par3 + var28 + dx, y, par5 + z + dz, ForgeDirection.UP, (IPlantable) ((IPlantable) AtumBlocks.BLOCK_PAPYRUS))) {
												world.setBlock(par3 + var28, y, par5 + z, AtumBlocks.BLOCK_PAPYRUS.blockID);
												var27 = true;
											}
										}
									}
								} else if(par2Random.nextInt(5) == 0) {
									for(dx = -1; dx <= 1; ++dx) {
										for(dz = -1; dz <= 1; ++dz) {
											int currentY = world.getHeightValue(par3 + var28, par5 + z);
											int belowID = world.getBlockId(par3 + var28 + dx, currentY - 1, par5 + z + dz);
											int currentID = world.getBlockId(par3 + var28 + dx, currentY, par5 + z + dz);
											if(par2Random.nextInt(3) == 0 && belowID == AtumBlocks.BLOCK_FERTILESOIL.blockID && currentID == 0) {
												world.setBlock(par3 + var28 + dx, currentY, par5 + z + dz, AtumBlocks.BLOCK_FLAX.blockID, 13, 0);
											}
										}
									}
								}
							}
						}

						return false;
					}
				}
			}
		}
	}
}