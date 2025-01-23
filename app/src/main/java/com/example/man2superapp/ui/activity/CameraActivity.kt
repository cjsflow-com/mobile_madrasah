package com.example.man2superapp.ui.activity

import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.CameraX
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.man2superapp.databinding.ActivityCameraXBinding
import com.example.man2superapp.utils.Constant
import com.example.man2superapp.utils.Help
import com.google.android.gms.vision.face.Landmark
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.util.concurrent.Executors

class CameraActivity: AppCompatActivity()
{
    private lateinit var cameraBinding: ActivityCameraXBinding
    private lateinit var faceDetector: FaceDetector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cameraBinding = ActivityCameraXBinding.inflate(layoutInflater)
        setContentView(cameraBinding.root)
        setAccessCamera()
        setUpFaceDetector()
        startCamera()
    }

    @OptIn(ExperimentalGetImage::class)
    private fun startCamera()
    {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this@CameraActivity)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Konfigurasi Preview
            val preview = Preview.Builder().build().also {
                it.surfaceProvider = cameraBinding.previewView.surfaceProvider
            }

            // Konfigurasi ImageAnalysis
            val imageAnalysis = ImageAnalysis.Builder()
                .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_YUV_420_888)
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()){imageProxy ->
                processImageProxy(imageProxy)
            }

            // Default Kamera Depan
            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            try{
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this@CameraActivity,
                    cameraSelector,
                    preview,
                    imageAnalysis
                )
            }catch (e: Exception)
            {
                Log.e("TAG", "startCamera: Gagal memulai kamera")
            }
        },ContextCompat.getMainExecutor(this))
    }

    @ExperimentalGetImage
    private fun processImageProxy(imageProxy: ImageProxy)
    {
        val mediaImage = imageProxy.image
        if(mediaImage != null){
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val inputImage = InputImage.fromMediaImage(mediaImage,rotationDegrees)

            faceDetector.process(inputImage).addOnSuccessListener { faces ->
                if (faces.isNotEmpty()){
                    for(face in faces)
                    {
                        val requiredLandmark = listOf(
                            face.getLandmark(FaceLandmark.LEFT_EYE)?.position,
                            face.getLandmark(FaceLandmark.RIGHT_EYE)?.position,
                            face.getLandmark(FaceLandmark.NOSE_BASE)?.position,
                            face.getLandmark(FaceLandmark.LEFT_EAR)?.position,
                            face.getLandmark(FaceLandmark.RIGHT_EAR)?.position,
                        )
                        if(requiredLandmark.all { it != null }){
                            Log.d("TAG", "Semua landmark terdeteksi: $requiredLandmark")
                            val resultIntent = Intent().apply {
                                putExtra(Constant.IS_FACE_DETECTED, true)
                            }
                            setResult(RESULT_OK, resultIntent)
                            finish()
                        }else{
                            Log.d("TAG", "Landmark tidak lengkap, deteksi diabaikan")
                        }
                    }
                }
            }
                .addOnFailureListener { e ->
                    Log.e("TAG", "processImageProxy: Gagal mendeteksi wajah: ${e.message}")
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }else{
            imageProxy.close()
        }
    }


    private fun setUpFaceDetector()
    {
        val options = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()
        faceDetector = FaceDetection.getClient(options)
    }

    private fun setAccessCamera()
    {
        Dexter.withContext(this@CameraActivity).withPermission(android.Manifest.permission.CAMERA)
            .withListener(object: PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    Help.showToast(this@CameraActivity,"Izin diberikan")
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Help.showToast(this@CameraActivity,"Izin tidak diberikan, Aplikasi ini" +
                            "membutuhkan izin kamera agar berfungsi")
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    Help.showToast(this@CameraActivity,"Izin tidak diberikan, Aplikasi ini" +
                            "membutuhkan izin kamera agar berfungsi")
                }

            }).onSameThread().check()
    }

}