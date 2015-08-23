package com.mierzen.recall;

import cpw.mods.fml.common.network.NetworkRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * Big thanks to sham1, who was a tremendous help with this!
 */
public class MessageTeleport extends MessageBase<MessageTeleport>
{
    public MessageTeleport()
    {
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
    }

    @Override
    public void handleClientSide(MessageTeleport message, EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(MessageTeleport message, EntityPlayer player)
    {
        World world = player.worldObj;

        System.out.println("message called");

        //minutes
        long channelTime = 8/60;
        long cooldownTime = 5;

        Date lastTeleported = new Date(player.getEntityData().getLong("LastTeleported"));
        Date now = new Date();

        float diffInMinutes = getDifference(lastTeleported, now) /1000F/60F; //milliseconds to minutes

        boolean canTeleport = (diffInMinutes>=cooldownTime) ? true : false;

        if (canTeleport)
        {
            System.out.println("trying  to teleport");
            if (performTeleport(world, (EntityPlayerMP)player))
                player.getEntityData().setLong("LastTeleported", now.getTime());
        }
        else
        {
            DecimalFormat secondFormat = new DecimalFormat("#");
            DecimalFormat minuteFormat = new DecimalFormat("#.##");

            float dif = cooldownTime - diffInMinutes;

            String str;
            if (dif<1)
                str = secondFormat.format(dif * 60F) + " seconds";
            else
                str = minuteFormat.format(dif) + " minutes";

            player.addChatMessage(new ChatComponentText("Cooldown: " + str));
        }
    }

    public boolean performTeleport(World currentWorld, EntityPlayer player)
    {
        ChunkCoordinates destination = player.getBedLocation(currentWorld.provider.dimensionId); //getBedSpawnPosition

        if (destination == null)
        {
            if ((player.dimension == 0) || player.isSneaking())  //if in overworld, get randomized point, else get portal location
            {
                if (player.dimension != 0)
                    player.travelToDimension(0);
                destination = /*EntityPlayer.verifyRespawnCoordinates(currentWorld,*/player.worldObj.provider.getRandomizedSpawnPoint();//,player.isSpawnForced(player.dimension));
                destination.posY = getTopBlockY(player.worldObj, destination.posX, destination.posZ);
            }
            else
            {
                destination = new ChunkCoordinates();
                destination.posX=player.getEntityData().getInteger("PortalPosX");
                destination.posY=player.getEntityData().getInteger("PortalPosY");
                destination.posZ=player.getEntityData().getInteger("PortalPosZ");
            }
        }

        if (destination != null)
            //player.addChatMessage(new ChatComponentText("World remote: " + currentWorld.isRemote + " || " + destination.toString()));

            if (!player.worldObj.isRemote)
            {
                Recall.network.sendToAllAround(new MessageParticleFX(player.posX, player.posY, player.posZ), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
                player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "mob.endermen.portal", 1.0F, 1.0F); //TODO: try to play sound twice, but only once for the current player
                player.setPositionAndUpdate(destination.posX - 0.5F, destination.posY + 1.0F, destination.posZ - 0.5F);
                Recall.network.sendToAllAround(new MessageParticleFX(player.posX, player.posY, player.posZ), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 64));
                //player.addChatMessage(new ChatComponentText("TP:  World remote: " + currentWorld.isRemote + " || " + player.worldObj.isRemote));

                return Boolean.TRUE;
            }

        return Boolean.FALSE;
    }

    public long getDifference(Date lastUse, Date now)
    {
        long diff = now.getTime()-lastUse.getTime();

        return diff;
    }

    public int getTopBlockY(World world, int x, int z)
    {
        for (int i = 256; i >= 0; i--)
        {
            Block block = world.getBlock(x, i, z);

            if (block != Blocks.air)
                return i+1;
        }

        return 256;
    }
}