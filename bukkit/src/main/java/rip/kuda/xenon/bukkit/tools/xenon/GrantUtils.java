package rip.kuda.xenon.bukkit.tools.xenon;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import rip.kuda.xenon.shared.XenonConstants;
import rip.kuda.xenon.shared.grant.Grant;

@UtilityClass
public class GrantUtils {

    public String getAuthor(Grant grant) {
        return grant.getAuthor().equals(XenonConstants.getConsoleUUID()) ? "&cCONSOLE" : Bukkit.getOfflinePlayer(grant.getAuthor()).getName();
    }

    public String getRemover(Grant grant) {
        return grant.getRemovedBy().equals(XenonConstants.getConsoleUUID()) ? "&cCONSOLE" : Bukkit.getOfflinePlayer(grant.getRemovedBy()).getName();
    }

}
