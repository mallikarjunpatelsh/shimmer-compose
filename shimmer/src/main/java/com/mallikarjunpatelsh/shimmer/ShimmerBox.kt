package com.mallikarjunpatelsh.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * A zero-content [Box] that renders only a shimmer animation.
 *
 * Apply **the exact same** size / shape / padding modifiers you would put on
 * the real view and the shimmer matches it pixel-perfectly.
 *
 * ---
 * ### Usage
 * ```kotlin
 * if (isLoading) {
 *     ShimmerBox(
 *         modifier = Modifier.fillMaxWidth().height(200.dp),
 *         shape    = RoundedCornerShape(12.dp),
 *         effect   = ShimmerEffect.PULSE,
 *     )
 * } else {
 *     AsyncImage(
 *         model    = url,
 *         modifier = Modifier.fillMaxWidth().height(200.dp).clip(RoundedCornerShape(12.dp)),
 *     )
 * }
 * ```
 *
 * ### Inside a LazyColumn skeleton screen
 * ```kotlin
 * repeat(5) {
 *     ShimmerBox(Modifier.fillMaxWidth().height(80.dp), effect = ShimmerEffect.HORIZONTAL)
 *     Spacer(Modifier.height(8.dp))
 * }
 * ```
 *
 * @param modifier  Every modifier from the real view — size, padding, etc.
 * @param effect    Which shimmer animation to run.
 * @param shape     Clip shape — must match the real view's clip.
 * @param colors    Custom shimmer color ramp.
 */
@Composable
fun ShimmerBox(
    modifier : Modifier       = Modifier,
    effect   : ShimmerEffect = ShimmerEffect.HORIZONTAL,
    shape    : Shape          = RoundedCornerShape(4.dp),
    colors   : List<Color>    = ShimmerColors,
) {
    Box(modifier = modifier.shimmer(effect = effect, shape = shape, colors = colors))
}
