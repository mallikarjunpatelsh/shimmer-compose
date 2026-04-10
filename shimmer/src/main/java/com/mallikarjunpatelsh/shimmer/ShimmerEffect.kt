package com.mallikarjunpatelsh.shimmer

/**
 * All supported shimmer animation styles.
 *
 * Pass one of these into [Modifier.shimmer], [ShimmerBox], or [ShimmerAsyncImage].
 *
 * ```kotlin
 * ShimmerAsyncImage(
 *     model  = url,
 *     effect = ShimmerEffect.DIAGONAL,
 *     ...
 * )
 * ```
 */
enum class ShimmerEffect {

    /** Classic left-to-right gradient sweep. */
    HORIZONTAL,

    /** 135° diagonal gradient sweep across the surface. */
    DIAGONAL,

    /** Alpha pulse — fades in and out with no directional sweep. */
    PULSE,

    /** Radial spotlight that travels across the surface horizontally. */
    RADIAL,

    /** Top-to-bottom gradient sweep. */
    VERTICAL,
}
