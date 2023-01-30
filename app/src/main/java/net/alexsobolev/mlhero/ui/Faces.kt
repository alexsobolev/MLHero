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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.mlkit.vision.face.Face
import net.alexsobolev.mlhero.R
import kotlin.math.roundToInt

@OptIn(ExperimentalTextApi::class)
@Composable
fun Faces(
    faces: List<Face>
) {
    val resources = LocalContext.current.resources
    val textMeasurer = rememberTextMeasurer()

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
            drawText(
                textMeasurer = textMeasurer,
                text = resources.getString(
                    R.string.smile_x,
                    smilingProbability
                ),
                topLeft = Offset(left, face.boundingBox.bottom.toFloat()),
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp,
                    background = Color.Blue
                )
            )
        }
    }
}
