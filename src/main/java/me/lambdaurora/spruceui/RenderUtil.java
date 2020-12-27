/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of SpruceUI.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package me.lambdaurora.spruceui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;

public final class RenderUtil {
    private RenderUtil() {
        throw new IllegalStateException("RenderUtil only contains static-definitions.");
    }

    /**
     * Renders the dirt background texture.
     *
     * @param client the client instance
     * @param x the X coordinate
     * @param y the Y coordinate
     * @param width the width
     * @param height the height
     * @param vOffset the v offset
     * @see #renderBackgroundTexture(MinecraftClient, int, int, int, int, float, int, int, int, int)
     */
    public static void renderBackgroundTexture(MinecraftClient client, int x, int y, int width, int height, float vOffset) {
        renderBackgroundTexture(client, x, y, width, height, vOffset, 64, 64, 64, 255);
    }

    /**
     * Renders the dirt background texture.
     *
     * @param client the client instance
     * @param x the X-coordinate
     * @param y the Y-coordinate
     * @param width the width
     * @param height the height
     * @param vOffset the v offset
     * @param red the red-component color value
     * @param green the green-component color value
     * @param blue the blue-component color value
     * @param alpha the alpha-component alpha value
     */
    public static void renderBackgroundTexture(MinecraftClient client, int x, int y, int width, int height, float vOffset,
                                               int red, int green, int blue, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        client.getTextureManager().bindTexture(Screen.OPTIONS_BACKGROUND_TEXTURE);
        RenderSystem.color4f(1.f, 1.f, 1.f, 1.f);

        int right = x + width;
        int bottom = y + height;

        bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE_COLOR);
        bufferBuilder.vertex(x, bottom, 0)
                .texture(0, bottom / 32.f + vOffset)
                .color(red, green, blue, alpha).next();
        bufferBuilder.vertex(right, bottom, 0)
                .texture(right / 32.f, bottom / 32.f + vOffset)
                .color(red, green, blue, alpha).next();
        bufferBuilder.vertex(right, y, 0)
                .texture(right / 32.f, y / 32.f + vOffset)
                .color(red, green, blue, alpha).next();
        bufferBuilder.vertex(x, y, 0)
                .texture(0, y / 32.f + vOffset)
                .color(red, green, blue, alpha).next();
        tessellator.draw();
    }

    /**
     * Renders a selection box as background.
     *
     * @param x the X-coordinate of the selection box
     * @param y the Y-coordinate of the selection box
     * @param width the width of the selection box
     * @param height the height of the selection box
     * @param red the red-component color value of the outer border
     * @param green the green-component color value of the outer border
     * @param blue the blue-component color value of the outer border
     * @param alpha the alpha-component color value of the outer border
     */
    public static void renderSelectionBox(int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.disableTexture();

        int top = y + height;
        int right = x + width;

        RenderSystem.color4f(red / 255.f, green / 255.f, blue / 255.f, alpha / 255.f);
        bufferBuilder.begin(7, VertexFormats.POSITION);
        bufferBuilder.vertex(x, top, 0).next();
        bufferBuilder.vertex(right, top, 0).next();
        bufferBuilder.vertex(right, y, 0).next();
        bufferBuilder.vertex(x, y, 0).next();
        tessellator.draw();
        RenderSystem.color4f(0, 0, 0, 1.f);
        bufferBuilder.begin(7, VertexFormats.POSITION);
        bufferBuilder.vertex(x + 1, top - 1, 0).next();
        bufferBuilder.vertex(right - 1, top - 1, 0).next();
        bufferBuilder.vertex(right - 1, y + 1, 0).next();
        bufferBuilder.vertex(x + 1, y + 1, 0).next();
        tessellator.draw();
        RenderSystem.enableTexture();
    }
}
