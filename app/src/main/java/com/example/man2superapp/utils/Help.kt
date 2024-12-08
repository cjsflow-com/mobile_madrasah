package com.example.man2superapp.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Locale



object Help {

    private const val FILENAME_FORMAT = "dd-MMM-yyyy"

    fun showToast(context: Context,message: String)
    {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    private val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT,
        Locale.US
    ).format(System.currentTimeMillis())

    fun reduceFileImage(file: File): File{
        val bitmap =  BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int

        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality,bmpStream)
            val bmpPicByteArray = bmpStream.toByteArray()
            streamLength = bmpPicByteArray.size
            compressQuality -= 5
        }while (streamLength > 1000000)

        bitmap.compress(Bitmap.CompressFormat.JPEG,compressQuality,FileOutputStream(file))
        return file
    }

    fun uriToFile(selectPdf: Uri, context: Context): File{
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)

        val inputStream = contentResolver.openInputStream(selectPdf) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf,0,len)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private fun createCustomTempFile(context: Context): File{
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp,".jpg",storageDir)
    }

     fun alertDialog(context: Activity)
    {
        MaterialAlertDialogBuilder(context)
            .setTitle("Keluar Aplikasi")
            .setMessage("Apakah Anda yakin ingin keluar?")
            .setPositiveButton("Ya"){_,_ ->
                context.finishAffinity()
            }
            .setNegativeButton("Tidak"){dialog,_ ->
                dialog.dismiss()
            }
            .show()
    }

}