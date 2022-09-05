package net.klnetwork.playerrolechecker.api.data.common;

import net.klnetwork.playerrolechecker.api.utils.CommonUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

public class SkinCache {
    private static BufferedImage steve;

    public BufferedImage getSteveImage() {
        try {
            steve = ImageIO.read(Objects.requireNonNull(SkinCache.class.getResourceAsStream("/steve.png")));
        } catch (IOException e) {
            /* ignored */
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