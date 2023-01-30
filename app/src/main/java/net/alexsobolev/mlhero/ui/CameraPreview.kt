package net.alexsobolev.mlhero.ui

import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode

@Composable
fun CameraPreview(
    camera: Int
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val previewView = remember {
        PreviewView(context).apply {
            scaleType = PreviewView.ScaleType.FILL_CENTER
            implementationMode = PreviewView.ImplementationMode.COMPATIBLE
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    var barcodes by remember { mutableStateOf<List<Barcode>>(emptyList()) }

    val barcodeScanner = buildBarcodeScanner()

    val cameraController = remember(camera) {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.Builder().requireLensFacing(camera).build()
            bindToLifecycle(lifecycleOwner)
            previewView.controller = this
            imageAnalysisTargetSize = CameraController.OutputSize(IMAGE_ANALYSIS_TARGET_SIZE)

            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                MlKitAnalyzer(
                    listOf(barcodeScanner),
                    CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED,
                    ContextCompat.getMainExecutor(context)
                ) { result ->
                    result?.getValue(barcodeScanner)?.let { detectedBarcodes ->
                        barcodes = detectedBarcodes
                    }
                }
            )
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = {
            previewView
        }
    )

    Barcodes(barcodes = barcodes)
}

private fun buildBarcodeScanner(): BarcodeScanner =
    BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    )

private val IMAGE_ANALYSIS_TARGET_SIZE = Size(480, 640)
