package com.teammetallurgy.atum.entity;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class EntityStone extends EntityMob
{

    public EntityStone(World par1World)
    {
        super(par1World);
    }

    public boolean isPotionApplicable(PotionEffect par1PotionEffect)
    {
        int i = par1PotionEffect.getPotionID();

        if (i == Potion.poison.id)
        {
            return false;
        }

        return true;
    }
}
