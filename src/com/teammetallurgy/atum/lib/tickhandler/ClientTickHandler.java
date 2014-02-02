package com.teammetallurgy.atum.lib.tickhandler;

import java.util.EnumSet;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.teammetallurgy.atum.AtumIDS;
import com.teammetallurgy.atum.items.Items;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler {
	private boolean raining;
	private boolean overlay;
	public static int defaultFog;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

		if(type.equals(EnumSet.of(TickType.RENDER))) {
		}

		if(type.equals(EnumSet.of(TickType.PLAYER))) {
			// if(Minecraft.getMinecraft().theWorld != null && Minecraft.getMinecraft().theWorld.loadedEntityList.size() > 0) {
			// List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
			// for(EntityPlayer player : players) {
			// if(player != null && player.getEntityName() == "RebelKeithy" || player.getEntityName() == "Shadowclaimer") {
			// String cloakURL = "http://images.mccapes.com/capes/standardmc/RebelKeithy.png";
			// if(player.cloakUrl != cloakURL)
			// player.cloakUrl = cloakURL;
			//
			// Minecraft.getMinecraft().renderEngine.obtainImageData(player.cloakUrl, new ImageBufferDownload());
			// }
			// }
			// }

			EntityPlayer player = (EntityPlayer) tickData[0];

			boolean nightvision = false;

			if(player.getCurrentArmor(3) != null) {
				if(player.getCurrentArmor(3).itemID == Items.rasGlory.itemID) {
					nightvision = true;
				}

			}

			if(player.dimension == AtumIDS.DIMENSION_ID) {
				if(Minecraft.getMinecraft().gameSettings.renderDistance < (nightvision ? 1 : 2)) {
					defaultFog = Minecraft.getMinecraft().gameSettings.renderDistance;
					Minecraft.getMinecraft().gameSettings.renderDistance = nightvision ? 1 : 2;
				}

				if(player.worldObj.isRaining()) {
					raining = true;
					if(Minecraft.getMinecraft().gameSettings.renderDistance < (nightvision ? 2 : 3)) {
						Minecraft.getMinecraft().gameSettings.renderDistance = nightvision ? 2 : 3;
					}

					Random random = new Random();
					int particlesPerTick = (3 - Minecraft.getMinecraft().gameSettings.particleSetting) * 6;
					for(int i = 0; i < particlesPerTick; i++) {
						float x = random.nextInt(4) - 2;
						float z = random.nextInt(4) - 2;
						float y = (random.nextFloat() - 0.7F) * 2F;

						float vx = 0.1F + random.nextFloat() * 0.1F;
						float vz = 0.1F + random.nextFloat() * 0.1F;

						player.worldObj.spawnParticle("sand", player.posX + x, player.posY + y, player.posZ + z, vx + player.motionX, 0.0D, vz + player.motionZ);
					}
				} else {
					if(raining == true && defaultFog < (nightvision ? 1 : 2)) {
						raining = false;
						Minecraft.getMinecraft().gameSettings.renderDistance = nightvision ? 1 : 2;

					}
				}
			}

		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {

		Minecraft minecraft = FMLClientHandler.instance().getClient();
		EntityPlayer player = minecraft.thePlayer;
		ItemStack currentItemStack = null;

		if(type.contains(TickType.RENDER)) {
			if(player != null && player.getCurrentArmor(3) != null) {
				if(player.getCurrentArmor(3).itemID == Items.mummyHelmet.itemID) {
					ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
					int par1 = scaledresolution.getScaledWidth();
					int par2 = scaledresolution.getScaledHeight();

					Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("atum", "textures/hud/mummyblur.png"));
					Tessellator tessellator = Tessellator.instance;
					tessellator.startDrawingQuads();
					tessellator.addVertexWithUV(0.0D, par2, -100, 0.0D, 1.0D);
					tessellator.addVertexWithUV(par1, par2, -100, 1.0D, 1.0D);
					tessellator.addVertexWithUV(par1, 0.0D, -100, 1.0D, 0.0D);
					tessellator.addVertexWithUV(0.0D, 0.0D, -100, 0.0D, 0.0D);
					tessellator.draw();
				}

			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		return "Atum.TickHandler.Player";
	}

}
