package rebelkeithy.mods.atum.cursedchest;

import net.minecraft.block.Block;
import net.minecraft.tileentity.WeightedRandomMinecart;
import net.minecraft.world.World;
import rebelkeithy.mods.atum.cursedchest.CursedChestBaseLogic;
import rebelkeithy.mods.atum.cursedchest.TileEntityChestSpawner;

class CursedChestSpawnerLogic extends CursedChestBaseLogic {

   final TileEntityChestSpawner field_98295_a;


   CursedChestSpawnerLogic(TileEntityChestSpawner tileEntityChestSpawner) {
      this.field_98295_a = tileEntityChestSpawner;
   }

   public void func_98267_a(int par1) {
      this.field_98295_a.worldObj.addBlockEvent(this.field_98295_a.xCoord, this.field_98295_a.yCoord, this.field_98295_a.zCoord, Block.mobSpawner.blockID, par1, 0);
   }

   public World getSpawnerWorld() {
      return this.field_98295_a.worldObj;
   }

   public int getSpawnerX() {
      return this.field_98295_a.xCoord;
   }

   public int getSpawnerY() {
      return this.field_98295_a.yCoord;
   }

   public int getSpawnerZ() {
      return this.field_98295_a.zCoord;
   }

   public void func_98277_a(WeightedRandomMinecart par1WeightedRandomMinecart) {
      super.setRandomMinecart(par1WeightedRandomMinecart);
      if(this.getSpawnerWorld() != null) {
         this.getSpawnerWorld().markBlockForUpdate(this.field_98295_a.xCoord, this.field_98295_a.yCoord, this.field_98295_a.zCoord);
      }

   }
}
