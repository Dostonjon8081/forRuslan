package uz.fizmasoft.dyhxx.violation

import android.view.View

interface ClickViolationRv{
    fun violationFileID(id: String, qaror: String)
    fun violationDetail(violationCarModel: ViolationCarModel)
}