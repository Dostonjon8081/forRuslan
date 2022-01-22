package uz.fizmasoft.dyhxx.violation.violation_detail

import com.google.gson.annotations.SerializedName
import okio.Buffer
import okio.BufferedSource
import java.io.BufferedReader

//data class ViolationPdfModel ()

data class ViolationPDFResponseModel(
    @SerializedName("file") val file: ViolationPDFResponseModelFile,
    @SerializedName("eventId") val eventId: String
)

data class ViolationPDFResponseModelFile(
    @SerializedName("type") val type: String,
    @SerializedName("data") val pdfData: List<Int>
)
