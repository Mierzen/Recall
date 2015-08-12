package com.mierzen.recall.keys;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

public class KeyBindings
{
    public static KeyBinding recallKey;

    @SideOnly(Side.CLIENT)
    public static void RegisterKeyBindings()
    {
        ClientRegistry.registerKeyBinding(recallKey = new KeyBinding("Recall", Keyboard.KEY_B, "Recall"));
    }
}
