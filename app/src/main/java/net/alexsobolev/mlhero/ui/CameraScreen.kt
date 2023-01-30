package net.alexsobolev.mlhero.ui

import androidx.camera.core.CameraSelector
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun CameraScreen() {
    var camera by remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }
    CameraPreview(
        camera = camera
    )
    Controls(
        onCameraChange = { camera = switchCamera(camera) }
    )
}

private fun switchCamera(camera: Int) = if (CameraSelector.LENS_FACING_FRONT == camera) {
    CameraSelector.LENS_FACING_BACK
} else {
    CameraSelector.LENS_FACING_FRONT
}
