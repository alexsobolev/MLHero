package net.alexsobolev.mlhero.ui

import android.text.TextPaint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.face.Face
import net.alexsobolev.mlhero.R
import kotlin.math.roundToInt

@Composable
fun Faces(
    faces: List<Face>
) {
    val resources = LocalContext.current.resources

    Canvas(modifier = Modifier.fillMaxSize()) {
        for (face in faces) {
            val left = face.boundingBox.left.toFloat()
            drawRoundRect(
                color = Color.Blue,
                style = Stroke(3.dp.toPx()),
                topLeft = Offset(left, face.boundingBox.top.toFloat()),
                size = Size(
                    face.boundingBox.width().toFloat(),
                    face.boundingBox.height().toFloat()
                ),
                cornerRadius = CornerRadius(24f, 24f)
            )

            val smilingProbability = ((face.smilingProbability ?: 0f) * 100).roundToInt()
            drawIntoCanvas {
                it.nativeCanvas.drawText(
                    resources.getString(
                        R.string.smile_x,
                        smilingProbability
                    ),
                    left,
                    face.boundingBox.bottom.toFloat(),
                    TextPaint().apply {
                        bgColor = Color.Blue.toArgb()
                        color = Color.White.toArgb()
                        textSize = 24.sp.toPx()
                    }
                )
            }
        }
    }
}
