package net.alexsobolev.mlhero.ui

import android.util.Size
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
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
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseDetector
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions
import com.google.mlkit.vision.segmentation.Segmentation
import com.google.mlkit.vision.segmentation.SegmentationMask
import com.google.mlkit.vision.segmentation.Segmenter
import com.google.mlkit.vision.segmentation.selfie.SelfieSegmenterOptions

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

//    var barcodes by remember { mutableStateOf<List<Barcode>>(emptyList()) }
//    var faces by remember { mutableStateOf<List<Face>>(emptyList()) }
//    var pose by remember { mutableStateOf<Pose?>(null) }
    var mask by remember { mutableStateOf<SegmentationMask?>(null) }

//    val barcodeScanner = buildBarcodeScanner()
//    val faceDetector = buildFaceDetector()
//    val poseDetector = buildPoseDetector()
    val segmenter = buildSegmenter()

    val cameraController = remember(camera) {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.Builder().requireLensFacing(camera).build()
            bindToLifecycle(lifecycleOwner)
            previewView.controller = this
            imageAnalysisTargetSize = CameraController.OutputSize(IMAGE_ANALYSIS_TARGET_SIZE)

            setImageAnalysisAnalyzer(
                ContextCompat.getMainExecutor(context),
                MlKitAnalyzer(
                    listOf(
//                        barcodeScanner,
//                        faceDetector,
//                        poseDetector,
                        segmenter
                    ),
                    ImageAnalysis.COORDINATE_SYSTEM_ORIGINAL,
                    ContextCompat.getMainExecutor(context)
                ) { result ->
                    /*result?.getValue(barcodeScanner)?.let { detectedBarcodes ->
                             barcodes = detectedBarcodes
                         }*/
                    /*result?.getValue(faceDetector)?.let { detectedFaces ->
                        faces = detectedFaces
                    }*/
                    /*result?.getValue(poseDetector)?.let { detectedPose ->
                        pose = detectedPose
                    }*/
                    result?.getValue(segmenter)?.let { detectedMask ->
                        mask = detectedMask
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

//    Barcodes(barcodes = barcodes)
//    Faces(faces = faces)
    /*Pose(
        pose = pose,
        resolution = cameraController.imageAnalysisTargetSize?.resolution,
        isCameraMirrored = camera == CameraSelector.LENS_FACING_FRONT
    )*/
    SegmentationMaskPreview(
        segmentationMask = mask,
        resolution = cameraController.imageAnalysisTargetSize?.resolution,
        isCameraMirrored = camera == CameraSelector.LENS_FACING_FRONT
    )
}

private fun buildBarcodeScanner(): BarcodeScanner =
    BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    )

private fun buildFaceDetector(): FaceDetector {
    val faceDetectorOptions = FaceDetectorOptions.Builder()
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_NONE)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setMinFaceSize(MIN_FACE_SIZE)
        .build()
    return FaceDetection.getClient(faceDetectorOptions)
}

private fun buildPoseDetector(): PoseDetector {
    val poseDetectorOptions = PoseDetectorOptions.Builder()
        .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
        .build()
    return PoseDetection.getClient(poseDetectorOptions)
}

private fun buildSegmenter(): Segmenter =
    Segmentation.getClient(
        SelfieSegmenterOptions.Builder()
            .setDetectorMode(SelfieSegmenterOptions.STREAM_MODE)
            .build()
    )

private val IMAGE_ANALYSIS_TARGET_SIZE = Size(480, 640)
private const val MIN_FACE_SIZE = 0.2f
