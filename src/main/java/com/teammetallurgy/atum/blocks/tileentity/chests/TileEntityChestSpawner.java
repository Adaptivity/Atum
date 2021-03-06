package com.teammetallurgy.atum.blocks.tileentity.chests;

import com.teammetallurgy.atum.blocks.BlockChestSpawner;
import net.minecraft.block.BlockChest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;

import java.util.Iterator;
import java.util.List;

public class TileEntityChestSpawner extends TileEntityChest implements IInventory {

    private final CursedChestSpawnerLogic chestSpawner = new CursedChestSpawnerLogic(this);
    public float f;
    public float g;
    public int h;
    private ItemStack[] chestContents = new ItemStack[36];
    private int ticksSinceSync;
    private int field_94046_i = -1;
    private String field_94045_s;

    public TileEntityChestSpawner() {
        int entityID = (int) (Math.random() * 6.0D);
        if (entityID == 0) {
            this.chestSpawner.setEntityName("AtumMummy");
        }

        if (entityID == 1) {
            this.chestSpawner.setEntityName("AtumBanditWarrior");
        }

        if (entityID == 2) {
            this.chestSpawner.setEntityName("AtumBanditArcher");
        }

        if (entityID == 3) {
            this.chestSpawner.setEntityName("AtumDustySkeleton");
        }

        if (entityID == 4) {
            this.chestSpawner.setEntityName("AtumDesertGhost");
        }

        if (entityID == 5) {
            this.chestSpawner.setEntityName("AtumStoneSoldier");
        }

        if (entityID == 6) {
            this.chestSpawner.setEntityName("AtumDesertWolf");
        }

        this.chestSpawner.minSpawnDelay = 0;
    }

    public void setSpawnerEntity(String name) {
        this.chestSpawner.setEntityName(name);
    }

    public void setMaxEntities(int max) {
        this.chestSpawner.spawnCount = max;
    }

    public void setDelay(int min, int max) {
        this.chestSpawner.minSpawnDelay = min;
        this.chestSpawner.maxSpawnDelay = max;
    }

    public void setRange(int range) {
        this.chestSpawner.spawnRange = range;
    }

    @Override
    public int getSizeInventory() {
        return 27;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.chestContents[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.chestContents[par1] != null) {
            ItemStack itemstack;
            if (this.chestContents[par1].stackSize <= par2) {
                itemstack = this.chestContents[par1];
                this.chestContents[par1] = null;
                this.markDirty();
                return itemstack;
            } else {
                itemstack = this.chestContents[par1].splitStack(par2);
                if (this.chestContents[par1].stackSize == 0) {
                    this.chestContents[par1] = null;
                }

                this.markDirty();
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.chestContents[par1] != null) {
            ItemStack itemstack = this.chestContents[par1];
            this.chestContents[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.chestContents[par1] = par2ItemStack;
        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.field_94045_s : "container.chest";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.field_94045_s != null && this.field_94045_s.length() > 0;
    }

    @Override
    public void func_145976_a(String p_145976_1_) {
        this.field_94045_s = p_145976_1_;
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Items", 0);
        this.chestContents = new ItemStack[this.getSizeInventory()];
        if (par1NBTTagCompound.hasKey("CustomName")) {
            this.field_94045_s = par1NBTTagCompound.getString("CustomName");
        }

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound) nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.chestContents.length) {
                this.chestContents[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

        this.chestSpawner.readFromNBT(par1NBTTagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.chestContents.length; ++i) {
            if (this.chestContents[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.chestContents[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        par1NBTTagCompound.setTag("Items", nbttaglist);
        if (this.hasCustomInventoryName()) {
            par1NBTTagCompound.setString("CustomName", this.field_94045_s);
        }

        this.chestSpawner.writeToNBT(par1NBTTagCompound);
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer par1EntityPlayer) {
        double d0 = 4.0D;
        double d1 = 3.0D;
        List list = super.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getBoundingBox((double) super.xCoord - d0, (double) super.yCoord - d1, (double) super.zCoord - d0, (double) super.xCoord + d0, (double) super.yCoord + d1, (double) super.zCoord + d0));
        if (!list.isEmpty()) {
            if (!super.worldObj.isRemote) {
                par1EntityPlayer.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("chat.atum.enemies")));
            }

            return false;
        } else {
            return super.worldObj.getTileEntity(super.xCoord, super.yCoord, super.zCoord) != this ? false : par1EntityPlayer.getDistanceSq((double) super.xCoord + 0.5D, (double) super.yCoord + 0.5D, (double) super.zCoord + 0.5D) <= 64.0D;
        }
    }


    @Override
    public void updateEntity() {
        this.chestSpawner.updateSpawner();
        ++this.ticksSinceSync;
        float f;

        if (!this.worldObj.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + this.xCoord + this.yCoord + this.zCoord) % 200 == 0) {
            this.numPlayersUsing = 0;
            f = 5.0F;
            List list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox((double) ((float) this.xCoord - f), (double) ((float) this.yCoord - f), (double) ((float) this.zCoord - f), (double) ((float) (this.xCoord + 1) + f), (double) ((float) (this.yCoord + 1) + f), (double) ((float) (this.zCoord + 1) + f)));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityPlayer entityplayer = (EntityPlayer) iterator.next();

                if (entityplayer.openContainer instanceof ContainerChest) {
                    IInventory iinventory = ((ContainerChest) entityplayer.openContainer).getLowerChestInventory();

                    if (iinventory == this || iinventory instanceof InventoryLargeChest && ((InventoryLargeChest) iinventory).isPartOfLargeChest(this)) {
                        ++this.numPlayersUsing;
                    }
                }
            }
        }

        this.prevLidAngle = this.lidAngle;
        f = 0.1F;
        double d2;

        if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
            double d1 = (double) this.xCoord + 0.5D;
            d2 = (double) this.zCoord + 0.5D;

            if (this.adjacentChestZPos != null) {
                d2 += 0.5D;
            }

            if (this.adjacentChestXPos != null) {
                d1 += 0.5D;
            }

            this.worldObj.playSoundEffect(d1, (double) this.yCoord + 0.5D, d2, "random.chestopen", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
        }

        if (this.numPlayersUsing == 0 && this.lidAngle > 0.0F || this.numPlayersUsing > 0 && this.lidAngle < 1.0F) {
            float f1 = this.lidAngle;

            if (this.numPlayersUsing > 0) {
                this.lidAngle += f;
            } else {
                this.lidAngle -= f;
            }

            if (this.lidAngle > 1.0F) {
                this.lidAngle = 1.0F;
            }

            float f2 = 0.5F;

            if (this.lidAngle < f2 && f1 >= f2 && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
                d2 = (double) this.xCoord + 0.5D;
                double d0 = (double) this.zCoord + 0.5D;

                if (this.adjacentChestZPos != null) {
                    d0 += 0.5D;
                }

                if (this.adjacentChestXPos != null) {
                    d2 += 0.5D;
                }

                this.worldObj.playSoundEffect(d2, (double) this.yCoord + 0.5D, d0, "random.chestclosed", 0.5F, this.worldObj.rand.nextFloat() * 0.1F + 0.9F);
            }

            if (this.lidAngle < 0.0F) {
                this.lidAngle = 0.0F;
            }
        }
    }

    @Override
    public boolean receiveClientEvent(int par1, int par2) {
        if (par1 == 1) {
            super.numPlayersUsing = par2;
            return true;
        } else {
            return super.receiveClientEvent(par1, par2);
        }
    }

    @Override
    public void openInventory() {
        if (super.numPlayersUsing < 0) {
            super.numPlayersUsing = 0;
        }

        ++super.numPlayersUsing;
        super.worldObj.addBlockEvent(super.xCoord, super.yCoord, super.zCoord, this.getBlockType(), 1, super.numPlayersUsing);
        super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, this.getBlockType());
        super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord - 1, super.zCoord, this.getBlockType());
    }

    @Override
    public void closeInventory() {
        if (this.getBlockType() != null && this.getBlockType() instanceof BlockChestSpawner) {
            --super.numPlayersUsing;
            super.worldObj.addBlockEvent(super.xCoord, super.yCoord, super.zCoord, this.getBlockType(), 1, super.numPlayersUsing);
            super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord, super.zCoord, this.getBlockType());
            super.worldObj.notifyBlocksOfNeighborChange(super.xCoord, super.yCoord - 1, super.zCoord, this.getBlockType());
        }
    }

    @Override
    public void invalidate() {
        super.invalidate();
        this.updateContainingBlockInfo();
    }

    @Override
    public int func_145980_j() {
        if (this.field_94046_i == -1) {
            if (super.worldObj == null || !(this.getBlockType() instanceof BlockChest)) {
                return 0;
            }

            this.field_94046_i = ((BlockChest) this.getBlockType()).field_149956_a;
        }

        return this.field_94046_i;
    }

}
