package com.mierzen.recall;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

public class Teleport
{
    public void tryTeleport(World world, EntityPlayer player)
    {
        System.out.println("trying  to teleport");

        if (!player.isSneaking())
        {
            performTeleport(world, player);
        }
    }

    public boolean performTeleport(World world, EntityPlayer player)
    {
        ChunkCoordinates destination = player.getBedLocation(world.provider.dimensionId); //getBedSpawnPosition

        if (destination == null)
        {
            destination = /*world.getSpawnPoint();*/world.provider.getSpawnPoint();
            destination.posY = world.getHeightValue(destination.posX, destination.posY);
        }

        if (destination != null)
            //player.addChatMessage(new ChatComponentText("World remote: " + world.isRemote + " || " + destination.toString()));

            if (!player.worldObj.isRemote)
            {
                player.setPositionAndUpdate(destination.posX - 0.5F, destination.posY + 1, destination.posZ - 0.5F);
                //player.addChatMessage(new ChatComponentText("TP:  World remote: " + world.isRemote + " || " + player.worldObj.isRemote));

                return Boolean.TRUE;
            }

        return Boolean.FALSE;
    }
}
