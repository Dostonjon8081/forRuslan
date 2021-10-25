package uz.fizmasoft.dyhxx.helper.util

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import uz.fizmasoft.dyhxx.helper.pdfviewer.PdfViewerActivity
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.Executors


object PDFUtils {

    fun checkStoragePermission(activity: Activity): Boolean {
//        return if (ContextCompat.checkSelfPermission(
//                activity,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            true
//        } else {
        var bol = false
        if (SDK_INT >= Build.VERSION_CODES.R) {
            bol = if (Environment.isExternalStorageManager()) {
                true
            } else {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                val uri = Uri.fromParts("package", activity.packageName, null);
                intent.data = uri
                activity.startActivity(intent);
                false
            }
        } else {
            bol = if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                true
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    STORAGE_REQUEST_CODE
                )
                false
            }
        }
        return bol
    }

    fun createPdf(dirPath: String, pdf: String, id: String) {
        val scopeComplete = CoroutineScope(Dispatchers.IO).launch {
            val dir = File("$dirPath")

            val idPdf = File(dir, "$id.pdf")

            if (!idPdf.exists()) {
                idPdf.createNewFile()
            }

            try {
                val pdfAsBytes: ByteArray? =
                    android.util.Base64.decode(pdf, android.util.Base64.DEFAULT)
                val os = FileOutputStream(idPdf, false)

                os.write(pdfAsBytes)
                os.flush()
                os.close()


            } catch (e: Exception) {
                logd("catch da")
            }
        }
        if (scopeComplete.isCompleted){
            scopeComplete.cancel()
        }
    }

    fun openPDF(activity: Activity, dirPath: String, id: String) {
        logd(dirPath)
        val executors = Executors.newFixedThreadPool(1)
        executors.submit(Runnable {
            activity.startActivity(
                PdfViewerActivity.launchPdfFromPath(
                    activity, "$dirPath/$id.pdf", id, "files", true
                )
            )
        })
        executors.shutdown()
    }

}
