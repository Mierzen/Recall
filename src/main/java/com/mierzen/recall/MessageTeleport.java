package com.mierzen.recall;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
            if (!player.isSneaking())
            {
                if (performTeleport(world, (EntityPlayerMP)player))
                    player.getEntityData().setLong("LastTeleported", now.getTime());
            }
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

    public long getDifference(Date lastUse, Date now)
    {
        long diff = now.getTime()-lastUse.getTime();

        return diff;
    }
}