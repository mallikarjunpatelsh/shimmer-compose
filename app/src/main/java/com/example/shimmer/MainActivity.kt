package com.example.shimmer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shimmer.ui.theme.ShimmerTheme
import com.mallikarjunpatelsh.shimmer.ShimmerAsyncImage
import com.mallikarjunpatelsh.shimmer.ShimmerBox
import com.mallikarjunpatelsh.shimmer.ShimmerEffect

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShimmerTheme {
                ShimmerDemoScreen()
            }
        }
    }
}

@Composable
fun ShimmerDemoScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        Text(
            text  = "shimmer-compose demo",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        ShimmerEffect.entries.forEach { effect ->
            Text(
                text     = "ShimmerEffect.${effect.name}",
                style    = MaterialTheme.typography.labelMedium,
                color    = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
            )

            DemoCard(effect)
        }

        // ── ShimmerAsyncImage demo ────────────────────────────────────────────
        Text(
            text     = "ShimmerAsyncImage (Coil, real network)",
            style    = MaterialTheme.typography.labelMedium,
            color    = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 16.dp, top = 20.dp, bottom = 8.dp)
        )

        ShimmerAsyncImage(
            model              = "https://picsum.photos/seed/shimmer/800/400",
            contentDescription = "Sample image",
            modifier           = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(200.dp),
            shape  = RoundedCornerShape(12.dp),
            effect = ShimmerEffect.DIAGONAL,
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// Reusable card skeleton
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun DemoCard(effect: ShimmerEffect) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        // Large image thumbnail
        ShimmerBox(
            modifier = Modifier.fillMaxWidth().height(160.dp),
            shape    = RoundedCornerShape(12.dp),
            effect   = effect,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            // Avatar
            ShimmerBox(
                modifier = Modifier.size(44.dp),
                shape    = CircleShape,
                effect   = effect,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                ShimmerBox(Modifier.fillMaxWidth(0.55f).height(13.dp), effect = effect)
                ShimmerBox(Modifier.fillMaxWidth(0.80f).height(10.dp), effect = effect)
                ShimmerBox(Modifier.fillMaxWidth(0.65f).height(10.dp), effect = effect)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(60.dp, 80.dp, 56.dp).forEach { w ->
                ShimmerBox(
                    modifier = Modifier.width(w).height(28.dp),
                    shape    = RoundedCornerShape(50),
                    effect   = effect,
                )
            }
        }
    }
}