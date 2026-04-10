package com.mallikarjunpatelsh.shimmer

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

/**
 * Applies a shimmer animation **in-place** on any composable.
 *
 * Because this is a [Modifier] extension it stacks with every modifier the
 * real view already has — size, padding, clip, clickable, etc. — so the
 * shimmer inherits all of them automatically. You never re-declare width,
 * height, or padding.
 *
 * The shimmer renders as a [background] overlay
 * using an animated [androidx.compose.ui.graphics.Brush].  When [visible] is
 * false the modifier is a no-op and the real content shows through unchanged.
 *
 * ---
 * ### Usage — toggling on a real view
 * ```kotlin
 * AsyncImage(
 *     model    = imageUrl,
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(200.dp)
 *         .clip(RoundedCornerShape(12.dp))
 *         .shimmer(
 *             effect  = ShimmerEffect.DIAGONAL,
 *             visible = isLoading,
 *         )
 * )
 * ```
 *
 * ### Usage — always-on placeholder (before the real view exists)
 * ```kotlin
 * Spacer(
 *     modifier = Modifier
 *         .fillMaxWidth()
 *         .height(200.dp)
 *         .shimmer(ShimmerEffect.PULSE, shape = RoundedCornerShape(12.dp))
 * )
 * ```
 *
 * @param effect   Which animation style to run. Defaults to [ShimmerEffect.HORIZONTAL].
 * @param shape    Clip shape for the shimmer overlay. Pass the same shape used
 *                 on the real view so corners match exactly.
 *                 Defaults to [RoundedCornerShape] with 4 dp radius.
 * @param colors   Custom shimmer color ramp. Defaults to light-grey sweep.
 * @param visible  When **false** this modifier is a complete no-op — the real
 *                 composable renders normally.  Defaults to **true**.
 */
fun Modifier.shimmer(
    effect: ShimmerEffect = ShimmerEffect.HORIZONTAL,
    shape: Shape = RoundedCornerShape(4.dp),
    colors: List<Color> = ShimmerColors,
    visible: Boolean = true,
): Modifier {
    if (!visible) return this

    return composed {
        var size by remember { mutableStateOf(IntSize.Zero) }

        val brush = rememberShimmerBrush(
            effect = effect,
            size = size,
            colors = colors
        )

        Modifier
            .onGloballyPositioned { coordinates ->
                size = coordinates.size
            }
            .clip(shape)
            .background(brush)
    }
}
