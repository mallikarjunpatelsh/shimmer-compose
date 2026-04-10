package com.mallikarjunpatelsh.shimmer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter

/**
 * Drop-in replacement for Coil's [AsyncImage] that shows a shimmer placeholder
 * until the image is **fully painted** — no flicker, no blank frame.
 *
 * The shimmer is driven by [AsyncImagePainter.State] rather than an external
 * loading flag, so it disappears precisely when pixels are on screen.
 *
 * ### State machine
 * ```
 * State.Empty   → shimmer ON  (no model set yet)
 * State.Loading → shimmer ON  (network / disk request in flight)
 * State.Error   → shimmer ON  (swap for an error composable if needed)
 * State.Success → shimmer OFF (bitmap decoded, on-screen — zero flicker ✓)
 * ```
 *
 * ---
 * ### Usage — identical to plain AsyncImage, one extra param
 * ```kotlin
 * ShimmerAsyncImage(
 *     model              = imageUrl,
 *     contentDescription = "Profile photo",
 *     modifier           = Modifier.fillMaxWidth().height(200.dp),
 *     shape              = RoundedCornerShape(12.dp),
 *     effect             = ShimmerEffect.DIAGONAL,
 * )
 * ```
 *
 * ### Custom error state
 * ```kotlin
 * ShimmerAsyncImage(
 *     model    = imageUrl,
 *     modifier = Modifier.size(80.dp),
 *     shape    = CircleShape,
 *     effect   = ShimmerEffect.PULSE,
 *     onError  = { /* show a fallback icon */ },
 * )
 * ```
 *
 * @param model              Image URL or Coil [coil.request.ImageRequest].
 * @param modifier           Same modifier chain as the real [AsyncImage].
 * @param shape              Clip shape — applied to both the image and the shimmer.
 * @param effect             Which shimmer animation to show while loading.
 * @param colors             Custom shimmer color ramp.
 * @param contentScale       Forwarded to [AsyncImage]. Defaults to [ContentScale.Crop].
 * @param contentDescription Forwarded to [AsyncImage].
 * @param onSuccess          Optional callback when [AsyncImagePainter.State.Success] fires.
 * @param onError            Optional callback when [AsyncImagePainter.State.Error] fires.
 */
@Composable
fun ShimmerAsyncImage(
    model              : Any?,
    modifier           : Modifier = Modifier,
    shape              : Shape = RoundedCornerShape(4.dp),
    effect             : ShimmerEffect = ShimmerEffect.HORIZONTAL,
    colors             : List<Color>      = ShimmerColors,
    contentScale       : ContentScale     = ContentScale.Crop,
    contentDescription : String?          = null,
    onSuccess          : ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError            : ((AsyncImagePainter.State.Error)   -> Unit)? = null,
) {
    // Reset to "loading" whenever the URL/model changes (important in LazyColumn).
    var isPainting by remember(model) { mutableStateOf(true) }

    Box(modifier = modifier) {

        // ── Real image ───────────────────────────────────────────────────────
        AsyncImage(
            model              = model,
            contentDescription = contentDescription,
            contentScale       = contentScale,
            modifier           = Modifier
                .matchParentSize()
                .clip(shape),
            onState = { state ->
                when (state) {
                    is AsyncImagePainter.State.Success -> {
                        isPainting = false
                        onSuccess?.invoke(state)
                    }
                    is AsyncImagePainter.State.Error -> {
                        isPainting = false      // stop shimmer, show broken image
                        onError?.invoke(state)
                    }
                    else -> isPainting = true   // Loading / Empty
                }
            }
        )

        // ── Shimmer overlay ──────────────────────────────────────────────────
        // Sits on top, inherits parent Box size via matchParentSize.
        // Removed from composition (not just hidden) once loading is done.
        if (isPainting) {
            ShimmerBox(
                modifier = Modifier.matchParentSize(),
                shape = shape,
                effect = effect,
                colors = colors,
            )
        }
    }
}
