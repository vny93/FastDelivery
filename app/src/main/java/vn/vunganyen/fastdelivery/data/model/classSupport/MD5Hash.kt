package vn.vunganyen.fastdelivery.data.model.classSupport

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MD5Hash {
    fun md5Code(str : String): String{
        val md: MessageDigest
        var result = ""
        try {
            md = MessageDigest.getInstance("MD5")
            md.update(str.toByteArray())
            val bi = BigInteger(1, md.digest())
            result = bi.toString(16)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return result
    }
}