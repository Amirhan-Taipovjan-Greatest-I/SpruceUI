/*
 * Copyright © 2020 LambdAurora <aurora42lambda@gmail.com>
 *
 * This file is part of SpruceUI.
 *
 * Licensed under the MIT license. For more information,
 * see the LICENSE file.
 */

package me.lambdaurora.spruceui.option;

import me.lambdaurora.spruceui.SpruceButtonWidget;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Represents a cycling option.
 * <p>
 * Works the same as the vanilla one but can provide a tooltip.
 *
 * @author LambdAurora
 * @version 1.3.2
 * @since 1.0.0
 */
public class SpruceCyclingOption extends SpruceOption
{
    private final Consumer<Integer>                     setter;
    private final Function<SpruceCyclingOption, String> messageProvider;
    private final Text                                  tooltip;

    public SpruceCyclingOption(@NotNull String key, @NotNull Consumer<Integer> setter, @NotNull Function<SpruceCyclingOption, String> messageProvider, @Nullable Text tooltip)
    {
        super(key);
        this.setter = setter;
        this.messageProvider = messageProvider;
        this.tooltip = tooltip;
    }

    /**
     * Cycles the option.
     *
     * @param amount The amount to cycle.
     */
    public void cycle(int amount)
    {
        this.setter.accept(amount);
    }

    @Override
    public @NotNull AbstractButtonWidget createButton(@NotNull GameOptions options, int x, int y, int width)
    {
        SpruceButtonWidget button = new SpruceButtonWidget(x, y, width, 20, this.getMessage(), btn -> {
            this.cycle(1);
            btn.setMessage(this.getMessage());
        });
        button.setTooltip(this.tooltip);
        return button;
    }

    /**
     * Gets the option message.
     *
     * @return The option message.
     */
    public @NotNull String getMessage()
    {
        return this.messageProvider.apply(this);
    }
}
