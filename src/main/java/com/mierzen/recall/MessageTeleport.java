package com.mierzen.recall;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;

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


        //timers (ticks)
        /*NBTTagCompound tagCompound = player.getEntityData();//.getTag("TeleportCooldown");
        NBTBase modeTag = tag.getTag("MyInt");
        if (modeTag != null)
        {
            player.addChatMessage(new ChatComponentText("Current int: " + ((NBTTagInt)modeTag).data));
        }
        else
        {
            cooldownTag.
        }
        NBTBase tag = player.getEntityData().getTag("TeleportCooldown");
*/
        double channelTime = player.getEntityData().getDouble("TeleportChannelTicks");
        double cooldownTime = player.getEntityData().getDouble("TeleportCooldownTicks");

        player.addChatMessage(new ChatComponentText("Cooldown: " + cooldownTime));

        System.out.println("trying  to teleport");
        if (!player.isSneaking())
        {
            if (cooldownTime == 0.0F)
            {
                if (performTeleport(world, (EntityPlayerMP)player))
                    player.getEntityData().setDouble("TeleportCooldownTicks", 150);
            }
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