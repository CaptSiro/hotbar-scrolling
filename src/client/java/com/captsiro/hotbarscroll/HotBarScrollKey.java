package com.captsiro.hotbarscroll;

import net.minecraft.client.option.KeyBinding;

public class HotBarScrollKey<A> {
    public interface Action<C> {
        void act(C context);
    }



    protected KeyBinding keyBinding;
    protected Action<A> action;
    protected boolean pressed;
    protected int pressedTime;

    protected int delay;
    protected int interval;

    public HotBarScrollKey(KeyBinding keyBinding, Action<A> action, int delay, int interval) {
        this.keyBinding = keyBinding;
        this.action = action;
        this.pressed = false;
        this.pressedTime = 0;

        this.delay = delay;
        this.interval = interval;
    }

    protected boolean shouldAct() {
        if (!this.keyBinding.isPressed()) {
            return false;
        }

        if (!this.pressed) {
            return true;
        }

        if (this.pressedTime < this.delay) {
            return false;
        }

        return ((this.pressedTime - this.delay) % this.interval) == 0;
    }

    public void act(A context) {
        if (shouldAct()) {
            this.action.act(context);
        }

        this.pressed = this.keyBinding.isPressed();
        this.pressedTime = this.keyBinding.isPressed()
            ? this.pressedTime + 1
            : 0;
    }
}
