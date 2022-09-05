package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.UUID;

public class SkinCache {
    private static BufferedImage steve;

    public BufferedImage getSteveImage() {
        try {
            if (steve == null) {
                steve = ImageIO.read(Objects.requireNonNull(SkinCache.class.getResourceAsStream("/steve.png")));
            }
        } catch (Exception e) {
            /* if caught error, please open to issues */
            e.printStackTrace();
        }

        return steve;
    }

    private BufferedImage skin;

    public BufferedImage getSkin(UUID uuid, boolean f) {
        try {
            if (uuid == null) {
                return getSteveImage();
            }

            if (skin == null) {
                skin = CommonUtils.getHeadSkin(uuid, f);
            }
        } catch (Exception ex) {
            skin = getSteveImage();
        }

        return skin;
    }
}