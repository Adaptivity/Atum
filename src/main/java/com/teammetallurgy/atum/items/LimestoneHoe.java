package com.teammetallurgy.atum.items;

import com.teammetallurgy.atum.blocks.AtumBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;

public class LimestoneHoe extends ItemHoe {

    public LimestoneHoe(ToolMaterial par2ToolMaterial) {
        super(par2ToolMaterial);
    }

    @Override
    public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
        return Block.getBlockFromItem(par2ItemStack.getItem()) == AtumBlocks.BLOCK_LIMESTONECOBBLE;
    }

    @Override
    public void registerIcons(IIconRegister par1IIconRegister) {
        this.itemIcon = par1IIconRegister.registerIcon("atum:LimestoneHoe");
    }
}
