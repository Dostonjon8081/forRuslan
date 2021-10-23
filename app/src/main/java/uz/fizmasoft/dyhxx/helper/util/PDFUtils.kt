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
import com.rajat.pdfviewer.PdfViewerActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


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

        CoroutineScope(Dispatchers.IO).launch {
            val dir = File("$dirPath/dyhxx")

            if (!dir.exists()) {
                dir.mkdir()
            }

            val idPdf = File(dir, "$id.pdf")
            if (!idPdf.exists()) {
                idPdf.createNewFile()
            }

            try {
                val downloadsPath = File("$dir/$id.pdf")
                val pdfAsBytes: ByteArray? =
                    android.util.Base64.decode(pdf, android.util.Base64.DEFAULT)
                val os = FileOutputStream(downloadsPath, false)

                os.write(pdfAsBytes)
                os.flush()
                os.close()
            } catch (e: Exception) {
            }
        }
    }

    fun openPDF(activity: Activity, dirPath: String, id: String) {

        activity.startActivity(
            PdfViewerActivity.launchPdfFromPath(
                activity, dirPath, id, "dyhxx", true
            )
        )
//        val browseStorage = Intent(Intent.ACTION_GET_CONTENT)
//        browseStorage.type = "application/pdf"
//        browseStorage.addCategory(Intent.ACTION_OPEN_DOCUMENT)
//        activity.startActivity(
//            Intent.createChooser(browseStorage, "Select PDF")
//        )

//        ----------------------------------------------------------------------------------------------
//        var file: File? = null
//        file = File("$dirPath/$id.pdf")
//
//        if (file.exists()) {
//            val target = Intent(Intent.ACTION_VIEW)
//            target.setDataAndType(Uri.fromFile(file), "dyhxx/$id.pdf")
//            target.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//
//            val intent = Intent.createChooser(target, "Open File")
//            try {
//                context.startActivity(intent)
//            } catch (e: ActivityNotFoundException) {
//                // Instruct the user to install a PDF reader here, or something
//            }
//        }
    }

}