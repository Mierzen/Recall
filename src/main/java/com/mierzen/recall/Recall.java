package com.mierzen.recall;

import com.mierzen.recall.proxies.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = References.MODID, name = References.NAME, version = References.VERSION)
public class Recall
{
    @Mod.Instance
    public static Recall instance = new Recall();

    @SidedProxy(clientSide = "com.mierzen.recall.proxies.ClientProxy", serverSide = "com.mierzen.recall.proxies.ServerProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper network;

    @EventHandler
    public void preInit(FMLPreInitializationEvent e)
    {
        proxy.preInit(e);
        network = NetworkRegistry.INSTANCE.newSimpleChannel("MyChannel");
        network.registerMessage(MessageTeleport.class, MessageTeleport.class, 0, Side.SERVER);
        network.registerMessage(MessageParticleFX.class, MessageParticleFX.class, 1, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent e)
    {
        proxy.init(e);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e)
    {
        proxy.postInit(e);
    }
}
