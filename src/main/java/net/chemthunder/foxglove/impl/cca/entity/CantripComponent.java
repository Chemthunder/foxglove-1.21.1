package net.chemthunder.foxglove.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.cantrip.Cantrip;
import net.chemthunder.foxglove.impl.Foxglove;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class CantripComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<CantripComponent> KEY = MiscUtils.getOrCreateKey(Foxglove.id("held_spell"), CantripComponent.class);
    private final LivingEntity player;

    private int duration = 0;

    private Cantrip heldCantrip = Cantrip.EMPTY;

    public CantripComponent(LivingEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(player);
    }

    public void tick() {
        if (this.getDuration() > 0) {
            this.duration--;
            this.tickDebug();
            if (this.getDuration() == 0) {
                this.setHeldCantrip(Cantrip.EMPTY);
            }
        }
    }

    private void tickDebug() {
        if (this.player instanceof PlayerEntity p) {
            p.sendMessage(Text.literal(this.getDuration() + " " + this.getHeldCantrip().name() + " " + this.getHeldCantrip().effect().name() + " " + this.getHeldCantrip().effect().type().asString()), true);
        }
    }

    public void setDuration(int i) {
        this.duration = i;
        this.sync();
    }

    public void setHeldCantrip(Cantrip s) {
        this.heldCantrip = s;
        this.sync();
    }

    public void set(int duration, Cantrip cantrip) {
        this.duration = duration;
        this.heldCantrip = cantrip;
        this.sync();
    }

    public int getDuration() {
        return this.duration;
    }

    public Cantrip getHeldCantrip() {
        return this.heldCantrip;
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.duration = nbtCompound.getInt("Duration");

        if (nbtCompound.contains("HeldCantrip", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbtCompound.getCompound("HeldCantrip");
            this.heldCantrip = Cantrip.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Foxglove.LOGGER::error).orElseThrow();
        } else {
            this.heldCantrip = Cantrip.EMPTY;
        }
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Duration", duration);

        if (this.heldCantrip != Cantrip.EMPTY) {
            nbtCompound.put("HeldCantrip", Cantrip.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.heldCantrip).getOrThrow());
        }
    }
}
