package net.chemthunder.foxglove.impl.cca.entity;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.chemthunder.foxglove.api.magic.Spell;
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

public class HeldSpellComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<HeldSpellComponent> KEY = MiscUtils.getOrCreateKey(Foxglove.id("held_spell"), HeldSpellComponent.class);
    private final LivingEntity player;

    private int duration = 0;

    private Spell heldSpell = Spell.EMPTY;

    public HeldSpellComponent(LivingEntity player) {
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
                this.setHeldSpell(Spell.EMPTY);
            }
        }
    }

    private void tickDebug() {
        if (this.player instanceof PlayerEntity p) {
            p.sendMessage(Text.literal(this.getDuration() + " " + this.getHeldSpell().getName() + " " + this.getHeldSpell().getComponent().getName() + " " + this.getHeldSpell().getComponent().getType().asString()), true);
        }
    }

    public void setDuration(int i) {
        this.duration = i;
        this.sync();
    }

    public void setHeldSpell(Spell s) {
        this.heldSpell = s;
        this.sync();
    }

    public void set(int duration, Spell spell) {
        this.duration = duration;
        this.heldSpell = spell;
        this.sync();
    }

    public int getDuration() {
        return this.duration;
    }

    public Spell getHeldSpell() {
        return this.heldSpell;
    }

    public void readFromNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.duration = nbtCompound.getInt("Duration");

        if (nbtCompound.contains("HeldSpell", NbtElement.COMPOUND_TYPE)) {
            NbtCompound compound = nbtCompound.getCompound("HeldSpell");
            this.heldSpell = Spell.CODEC.parse(wrapperLookup.getOps(NbtOps.INSTANCE), compound).resultOrPartial(Foxglove.LOGGER::error).orElseThrow();
        } else {
            this.heldSpell = Spell.EMPTY;
        }
    }

    public void writeToNbt(NbtCompound nbtCompound, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbtCompound.putInt("Duration", duration);

        if (this.heldSpell != Spell.EMPTY) {
            nbtCompound.put("HeldSpell", Spell.CODEC.encodeStart(wrapperLookup.getOps(NbtOps.INSTANCE), this.heldSpell).getOrThrow());
        }
    }
}
