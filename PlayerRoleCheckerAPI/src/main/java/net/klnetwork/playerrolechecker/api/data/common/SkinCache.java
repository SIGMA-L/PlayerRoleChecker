package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import java.awt.image.BufferedImage;
import java.util.UUID;

public class SkinCache {
    private BufferedImage skin;

    public BufferedImage getSkin(UUID uuid, boolean f) {
        if (uuid == null) {
            return null;
        }

        if (skin == null) {
            skin = CommonUtils.getHeadSkin(uuid, f);
        }

        return skin;
    }
}
