package net.alexsobolev.mlhero.ui

import android.util.Size
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark
import java.lang.Float.max

@Composable
fun Pose(
    pose: Pose?,
    resolution: Size?,
    isCameraMirrored: Boolean
) {
    if (pose == null) return

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
                ).scale(
                    scale * if (isCameraMirrored) -1f else 1f,
                    scale
                )
            ) {
                val strokeWidth = 1.dp.toPx()
                val whitePaint = SolidColor(Color.White)
                val bluePaint = SolidColor(Color.Blue)
                val grayPaint = SolidColor(Color.LightGray)

                val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
                val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
                val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
                val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
                val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
                val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
                val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
                val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
                val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
                val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
                val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
                val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)
                val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
                val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
                val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
                val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
                val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
                val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
                val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
                val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
                val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
                val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)

                connectLandMarks(
                    leftShoulder,
                    rightShoulder,
                    whitePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftHip,
                    rightHip,
                    whitePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftShoulder,
                    leftElbow,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftElbow,
                    leftWrist,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftShoulder,
                    leftHip,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftHip,
                    leftKnee,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftKnee,
                    leftAnkle,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftWrist,
                    leftThumb,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftWrist,
                    leftPinky,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftWrist,
                    leftIndex,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftIndex,
                    leftPinky,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftAnkle,
                    leftHeel,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    leftHeel,
                    leftFootIndex,
                    bluePaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightShoulder,
                    rightElbow,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightElbow,
                    rightWrist,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightShoulder,
                    rightHip,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightHip,
                    rightKnee,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightKnee,
                    rightAnkle,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightWrist,
                    rightThumb,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightWrist,
                    rightPinky,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightWrist,
                    rightIndex,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightIndex,
                    rightPinky,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightAnkle,
                    rightHeel,
                    grayPaint,
                    strokeWidth
                )
                connectLandMarks(
                    rightHeel,
                    rightFootIndex,
                    grayPaint,
                    strokeWidth
                )

                drawPoint(
                    leftShoulder,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightShoulder,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftElbow,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightElbow,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftWrist,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightWrist,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftHip,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightHip,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftKnee,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightKnee,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftAnkle,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightAnkle,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftPinky,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightPinky,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftIndex,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightIndex,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftThumb,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightThumb,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftHeel,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightHeel,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    leftFootIndex,
                    whitePaint,
                    strokeWidth
                )
                drawPoint(
                    rightFootIndex,
                    whitePaint,
                    strokeWidth
                )
            }
        }
    }
}

private fun DrawScope.connectLandMarks(
    startLandmark: PoseLandmark?,
    endLandmark: PoseLandmark?,
    paint: Brush,
    strokeWidth: Float
) {
    if (startLandmark != null && endLandmark != null) {
        drawLine(
            brush = paint,
            start = Offset(
                startLandmark.position.x,
                startLandmark.position.y
            ),
            end = Offset(
                endLandmark.position.x,
                endLandmark.position.y
            ),
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawPoint(
    landmark: PoseLandmark?,
    paint: Brush,
    strokeWidth: Float
) {
    if (landmark == null) {
        return
    }

    drawCircle(
        brush = paint,
        center = Offset(
            landmark.position.x,
            landmark.position.y
        ),
        radius = strokeWidth
    )
}

private fun calculateScale(
    constraints: Constraints,
    imageAnalysisTargetSize: Size?
): Float {
    val heightRatio = constraints.maxHeight.toFloat() / (imageAnalysisTargetSize?.height ?: 0)
    val widthRatio = constraints.maxWidth.toFloat() / (imageAnalysisTargetSize?.width ?: 0)
    return max(heightRatio, widthRatio)
}