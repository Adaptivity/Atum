package rebelkeithy.mods.atum.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import rebelkeithy.mods.atum.Atum;
import rebelkeithy.mods.atum.AtumBlocks;

public class ItemScarab extends Item {

	public ItemScarab(int id) {
		super(id);
		super.maxStackSize = 1;
	}

	public boolean hasEffect(ItemStack par1ItemStack) {
		return true;
	}

	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		int blockID = par3World.getBlockId(par4, par5, par6);
		if (blockID == Block.sandStone.blockID) {
			if (!AtumBlocks.portal.tryToCreatePortal(par3World, par4, par5 + 1, par6)) {
				if (par2EntityPlayer.capabilities.isCreativeMode) {
					par3World.setBlock(par4 + 2, par5 + 1, par6, Block.sandStone.blockID);
					par3World.setBlock(par4 + 2, par5 + 2, par6, Block.sandStone.blockID);
					par3World.setBlock(par4 - 2, par5 + 1, par6, Block.sandStone.blockID);
					par3World.setBlock(par4 - 2, par5 + 2, par6, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 1, par6 + 2, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 2, par6 + 2, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 1, par6 - 2, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 2, par6 - 2, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 3, par6 + 1, Block.sandStone.blockID);
					par3World.setBlock(par4, par5 + 3, par6 - 1, Block.sandStone.blockID);
					par3World.setBlock(par4 - 1, par5 + 3, par6, Block.sandStone.blockID);
					par3World.setBlock(par4 + 1, par5 + 3, par6, Block.sandStone.blockID);
				}
			} else {
				--par2EntityPlayer.getCurrentEquippedItem().stackSize;
			}
		}

		return true;
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Atum.modID + ":Scarab");
	}
}
