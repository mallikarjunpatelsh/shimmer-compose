package com.mallikarjunpatelsh.shimmer

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize

// Default shimmer color ramp — light grey sweep on a slightly darker grey base.
internal val ShimmerColors = listOf(
    Color(0xFFD0D0D0),
    Color(0xFFF5F5F5),
    Color(0xFFD0D0D0),
)

/**
 * Internal factory that returns the correct animated [Brush] for [effect].
 *
 * [size] is the pixel dimensions of the composable being shimmered.
 * Pass [IntSize.Zero] on the first frame; the brush will update itself once
 * [androidx.compose.ui.layout.onGloballyPositioned] fires with the real size.
 *
 * All animations are driven by a single [rememberInfiniteTransition] per call
 * so there is exactly one animation clock per composable.
 */
@Composable
internal fun rememberShimmerBrush(
    effect : ShimmerEffect,
    size   : IntSize,
    colors : List<Color> = ShimmerColors,
): Brush {
    val transition = rememberInfiniteTransition(label = "shimmer_${effect.name}")

    // Safe fallback dimensions so the brush is valid before size is measured.
    val w = size.width .toFloat().coerceAtLeast(200f)
    val h = size.height.toFloat().coerceAtLeast(200f)

    return when (effect) {

        // ── Horizontal ───────────────────────────────────────────────────────
        ShimmerEffect.HORIZONTAL -> {
            val x by transition.animateFloat(
                initialValue  = -w * 2,
                targetValue   =  w * 2,
                animationSpec = infiniteRepeatable(
                    tween(1200, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = "shimmer_h_x"
            )
            Brush.linearGradient(
                colors = colors,
                start  = Offset(x, 0f),
                end    = Offset(x + w, 0f)
            )
        }

        // ── Diagonal ─────────────────────────────────────────────────────────
        ShimmerEffect.DIAGONAL -> {
            val d by transition.animateFloat(
                initialValue  = -(w + h),
                targetValue   =  (w + h),
                animationSpec = infiniteRepeatable(
                    tween(1400, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = "shimmer_d_d"
            )
            Brush.linearGradient(
                colors = colors,
                start  = Offset(d, d),
                end    = Offset(d + w, d + h)
            )
        }

        // ── Pulse ────────────────────────────────────────────────────────────
        ShimmerEffect.PULSE -> {
            val alpha by transition.animateFloat(
                initialValue  = 0.2f,
                targetValue   = 0.85f,
                animationSpec = infiniteRepeatable(
                    tween(900, easing = FastOutSlowInEasing),
                    RepeatMode.Reverse
                ),
                label = "shimmer_p_alpha"
            )
            Brush.linearGradient(
                colors = listOf(
                    colors.first().copy(alpha = alpha),
                    colors.first().copy(alpha = alpha),
                )
            )
        }

        // ── Radial ───────────────────────────────────────────────────────────
        ShimmerEffect.RADIAL -> {
            val cx by transition.animateFloat(
                initialValue  = 0f,
                targetValue   = w,
                animationSpec = infiniteRepeatable(
                    tween(1500, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = "shimmer_r_cx"
            )
            Brush.radialGradient(
                colors = listOf(
                    Color(0xFFF5F5F5),
                    Color(0xFFCCCCCC),
                    Color(0xFFD8D8D8),
                ),
                center = Offset(cx, h / 2f),
                radius = (w / 2f).coerceAtLeast(100f)
            )
        }

        // ── Vertical ─────────────────────────────────────────────────────────
        ShimmerEffect.VERTICAL -> {
            val y by transition.animateFloat(
                initialValue  = -h * 2,
                targetValue   =  h * 2,
                animationSpec = infiniteRepeatable(
                    tween(1300, easing = LinearEasing),
                    RepeatMode.Restart
                ),
                label = "shimmer_v_y"
            )
            Brush.linearGradient(
                colors = colors,
                start  = Offset(0f, y),
                end    = Offset(0f, y + h)
            )
        }
    }
}
