package net.alexsobolev.mlhero.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.barcode.common.Barcode

@OptIn(ExperimentalTextApi::class)
@Composable
fun Barcodes(
    barcodes: List<Barcode>
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(modifier = Modifier.fillMaxSize()) {
        for (barcode in barcodes) {
            barcode.boundingBox?.let { boundingBox ->
                val left = boundingBox.left.toFloat()
                drawRoundRect(
                    color = Color.Blue,
                    style = Stroke(3.dp.toPx()),
                    topLeft = Offset(left, boundingBox.top.toFloat()),
                    size = Size(
                        boundingBox.width().toFloat(),
                        boundingBox.height().toFloat()
                    ),
                    cornerRadius = CornerRadius(24f, 24f)
                )

                drawText(
                    textMeasurer = textMeasurer,
                    text = barcode.displayValue ?: "",
                    topLeft = Offset(left, boundingBox.bottom.toFloat()),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp,
                        background = Color.Blue
                    )
                )
            }
        }
    }
}
