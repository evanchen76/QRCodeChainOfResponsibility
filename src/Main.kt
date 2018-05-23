

fun main(args : Array<String>) {
    val qrCodeChecker = WebUrlChecker(PhoneChecker(EmailChecker(null)))

    val qrCodeText = "http://www.google.com"

    qrCodeChecker.check(qrCodeText, object: QRCodeFormatCheckerListener{
        override fun onCallPhone(phoneNo: String) {
            println("開啟播電話功能:$phoneNo")
        }

        override fun onOpenMail(mail: String) {
            println("開啟Mail")
        }

        override fun onOpenBrowser(url: String) {
            println("開啟瀏覽器:$url")
        }

        override fun onShowText(text: String) {
            println("顯示文字:$text")
        }
    })
}

