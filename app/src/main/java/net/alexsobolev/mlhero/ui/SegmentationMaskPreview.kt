package net.alexsobolev.mlhero.ui

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.util.Size
import androidx.annotation.ColorInt
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import com.google.mlkit.vision.segmentation.SegmentationMask
import java.nio.ByteBuffer

@Composable
fun SegmentationMaskPreview(
    segmentationMask: SegmentationMask?,
    resolution: Size?,
    isCameraMirrored: Boolean
) {
    if (segmentationMask == null) return

    BoxWithConstraints(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        with(LocalDensity.current) {
            val scale = remember(resolution) {
                calculateScale(constraints, resolution)
            }
            Canvas(
                modifier = Modifier.size(
                    width = resolution?.width?.toDp() ?: 0.toDp(),
                    height = resolution?.height?.toDp() ?: 0.toDp()
                ).scale(scale * if (isCameraMirrored) -1f else 1f, scale)
            ) {
                val mask = segmentationMask.buffer
                val maskWidth = segmentationMask.width
                val maskHeight = segmentationMask.height
                mask.rewind()

                val bitmap = Bitmap.createBitmap(
                    maskBackground(mask, maskWidth, maskHeight),
                    maskWidth,
                    maskHeight,
                    Bitmap.Config.ARGB_8888
                )

                drawImage(
                    bitmap.asImageBitmap()
                )
                bitmap.recycle()
            }
        }
    }
}

@ColorInt
private fun maskBackground(
    byteBuffer: ByteBuffer,
    maskWidth: Int,
    maskHeight: Int
): IntArray {
    @ColorInt val colors =
        IntArray(maskWidth * maskHeight)
    for (i in 0 until maskWidth * maskHeight) {
        val backgroundCoefficient = 1 - byteBuffer.float
        if (backgroundCoefficient > SENSITIVITY) {
            colors[i] = BACKGROUND_COLOR
        } else {
            colors[i] = Color.TRANSPARENT
        }
    }
    return colors
}

private fun calculateScale(
    constraints: Constraints,
    imageAnalysisTargetSize: Size?
): Float {
    val heightRatio = constraints.maxHeight.toFloat() / (imageAnalysisTargetSize?.height ?: 0)
    val widthRatio = constraints.maxWidth.toFloat() / (imageAnalysisTargetSize?.width ?: 0)
    return java.lang.Float.max(heightRatio, widthRatio)
}

private const val SENSITIVITY = 0.75f
private val BACKGROUND_COLOR = Color.parseColor("#A303DAC5")
