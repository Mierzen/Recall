package com.mierzen.recall;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;

public class PlayerEnterDimension
{
    @SubscribeEvent
    public void onEvent(PlayerEvent.PlayerChangedDimensionEvent event)
    {
        EntityPlayer player = event.player;
        int fromDim = event.fromDim;
        int toDim = event.toDim;

        player.addChatMessage(new ChatComponentText("Player changed dimension from " + fromDim + " to " + toDim));

        if (event.toDim != 0) //if not overworld
        {
            player.addChatMessage(new ChatComponentText("Player loc:" + player.posX + " " + player.posY + " " + player.posZ));
            ChunkCoordinates portalPoint = getValidPortalPoint(player);

            player.getEntityData().setInteger("PortalPosX", portalPoint.posX);
            player.getEntityData().setInteger("PortalPosY", portalPoint.posY);
            player.getEntityData().setInteger("PortalPosZ", portalPoint.posZ);
        }
    }

    public ChunkCoordinates getValidPortalPoint(EntityPlayer player)
    {
        ChunkCoordinates portalPoint = new ChunkCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);

        if (player.worldObj.getBlock((int)player.posX, (int)player.posY, (int)player.posZ) == Blocks.air) //already done, but I'm keeping it anyway
        {
            portalPoint.posX = (int)player.posX;
            portalPoint.posY = (int)player.posY;
            portalPoint.posZ = (int)player.posZ;
        }
        else
        {
            for (int x = 0; x>3; x++)
            {
                for (int z = 0; z>3; z++)
                {
                    Block block = player.worldObj.getBlock((int)player.posX+x, (int)player.posY, (int)player.posZ+z);
                    Block blockBelow = player.worldObj.getBlock((int)player.posX+x, (int)player.posY-1, (int)player.posZ+z);

                    if ((block == Blocks.air) && ((blockBelow != Blocks.air)||(blockBelow != Blocks.lava)))
                    {
                        portalPoint.posX = (int)player.posX+x;
                        portalPoint.posY = (int)player.posY;
                        portalPoint.posZ = (int)player.posZ+z;

                        break;
                    }
                }
            }
        }

        return portalPoint;
    }
}
