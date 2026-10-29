package net.creeperhost.resourcefulcreepers;

import net.creeperhost.polylib.platform.Services;
import java.nio.file.Path;

public final class ResourcefulCreepersPlatform {
    private ResourcefulCreepersPlatform() {}
    public static Path getConfigDirectory() { return Services.PLATFORM.getConfigFolder(); }
}
