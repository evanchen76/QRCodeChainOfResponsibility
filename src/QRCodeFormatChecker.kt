

interface QRCodeFormatCheckerListener{
    fun onOpenBrowser(url: String) //開啟瀏覽器
    fun onCallPhone(phoneNo: String) //開啟播電話
    fun onOpenMail(mail: String) //開發Mail app
    fun onShowText(text: String) //顯示文字
}

//責任鏈
abstract class QRCodeFormatChecker (private val successor: QRCodeFormatChecker?){

    //責任鏈的檢查
    protected abstract fun internalCheck(source: String): Boolean
    //責任鏈的處理
    protected abstract fun action(source:String, listener: QRCodeFormatCheckerListener)

    fun check(source:String, listener: QRCodeFormatCheckerListener)
    {
        if (internalCheck(source)){
            //符合格式，action
            action(source, listener)
        }else{
            //不符合格式
            if (successor != null){
                //還有下一關
                successor.check(source, listener)
            }else{
                //沒有下一關了，listener 顯示文字
                listener.onShowText(source)
            }
        }
    }
}

//關卡：檢查是否符合網址格式
class WebUrlChecker(successor: QRCodeFormatChecker?): QRCodeFormatChecker(successor) {

    override fun action(source: String, listener: QRCodeFormatCheckerListener) {
        //listener 開啟瀏覽器
        listener.onOpenBrowser(source)
    }

    override fun internalCheck(source: String): Boolean {
        //檢查是否符合網址格式
        return source.startsWith("http")
    }
}

//關卡：檢查是否符合手機號碼
class PhoneChecker(successor: QRCodeFormatChecker?): QRCodeFormatChecker(successor) {

    override fun action(source: String, listener: QRCodeFormatCheckerListener) {
        //listener 開啟撥電話
        listener.onCallPhone(source)
    }

    override fun internalCheck(source: String): Boolean {
        //檢查是否符合手機號碼
        return source.matches("^(0)(9)([0-9]{8})$".toRegex())
    }
}

//關卡：檢查是否符合Email
class EmailChecker(successor: QRCodeFormatChecker?): QRCodeFormatChecker(successor) {

    override fun action(source: String, listener: QRCodeFormatCheckerListener) {
        //listener 開啟Mail
        listener.onOpenMail(source)
    }

    override fun internalCheck(source: String): Boolean {
        //檢查是否符合Email格式
        return source.matches("^\\w+([.]\\w+)*@\\w{2,}+[.]\\w{2,}([.][a-z0-9-]+)*$".toRegex());
    }
}