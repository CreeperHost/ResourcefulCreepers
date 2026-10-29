package net.creeperhost.resourcefulcreepers.client;

import net.minecraft.resources.Identifier;

import java.util.HashMap;

public class ResourcefulCreeperClient
{
    public static HashMap<String, Identifier> TEXTURE_MAP = new HashMap<>();

    public static void addTexture(String resourceName, Identifier resourceLocation)
    {
        if(!TEXTURE_MAP.containsKey(resourceName))
        {
            TEXTURE_MAP.put(resourceName, resourceLocation);
        }
    }

    public static Identifier getTexture(String resourceName)
    {
        if(TEXTURE_MAP.containsKey(resourceName))
        {
            return TEXTURE_MAP.get(resourceName);
        }
        return null;
    }
}
