package top.offsetmonkey538.hcwt.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.HoneyBottleItem;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(HoneyBottleItem.class)
public abstract class HoneyBottleItemMixin {

    @WrapOperation(
            require = 0,
            method = "finishUsing",
            at = @At(
                    remap = false,
                    value = "INVOKE",
                    target = "Lnet/minecraft/class_1309;method_6016(Lnet/minecraft/class_1291;)Z"
            )
    )
    private boolean hcwt$oneTwenty$makeHoneyRemoveWitheringEffect(LivingEntity instance, StatusEffect type, Operation<Boolean> original) {
        // This is so cursed but for some reason I really want there to only be one jar file that supports 1.20 to 1.21
        try {
            original.call(instance, (StatusEffect) StatusEffects.class.getField(FabricLoader.getInstance().getMappingResolver().mapFieldName("intermediary", "net.minecraft.class_1294", "field_5920", "Z")).get(null));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        return original.call(instance, type);
    }

    @WrapOperation(
            require = 0,
            method = "finishUsing",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;removeStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"
            )
    )
    private boolean hcwt$oneTwentyOne$makeHoneyRemoveWitheringEffect(LivingEntity instance, RegistryEntry<StatusEffect> type, Operation<Boolean> original) {
        original.call(instance, StatusEffects.WITHER);
        return original.call(instance, type);
    }
}
