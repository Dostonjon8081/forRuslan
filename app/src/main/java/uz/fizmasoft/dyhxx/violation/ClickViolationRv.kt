package uz.fizmasoft.dyhxx.violation

interface ClickViolationRv {
    fun violationFileID(id: String, qaror: String)
    fun violationDetail(violationCarModel: ViolationCarModel)
}