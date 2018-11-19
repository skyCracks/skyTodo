package com.skycracks.todo.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.telephony.TelephonyManager
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL

object NetWorkUtil {

    private const val TIMEOUT = 3000

    private const val NETWORK_AVAILABLE = "available"

    private const val NETWORK_CONNECTED = "connected"

    private const val NETWORK_WIFI = "wifi"

    private const val NETWORK_MOBILE = "mobile"

    private const val NETWORK_2G = "2g"

    private const val NETWORK_3G = "3g"

     private fun isNetworkInfoStatus(context: Context?, status: String): Boolean {
         context?.let{
             val manager = it.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
             manager.activeNetworkInfo?.run {
                 when(status){
                     NETWORK_AVAILABLE ->{
                         return this.isAvailable
                     }
                     NETWORK_CONNECTED ->{
                         return this.isConnected
                     }
                     NETWORK_WIFI ->{
                         return this.type == ConnectivityManager.TYPE_WIFI
                     }
                     NETWORK_MOBILE ->{
                         if (this.type == ConnectivityManager.TYPE_MOBILE) {
                             return this.isAvailable
                         }
                     }
                     NETWORK_2G ->{
                         when(this.subtype){
                             TelephonyManager.NETWORK_TYPE_EDGE,
                             TelephonyManager.NETWORK_TYPE_GPRS,
                             TelephonyManager.NETWORK_TYPE_CDMA ->{
                                 return true
                             }
                             else ->{
                                 return false
                             }
                         }
                     }
                     NETWORK_3G ->{
                         return this.type == ConnectivityManager.TYPE_MOBILE
                     }
                     else ->{
                         return false
                     }
                 }
             }
         }
         return false
     }
        /**
         * check NetworkAvailable
         *
         * @param context
         * @return
         */
        @JvmStatic
        fun isNetworkAvailable(context: Context?): Boolean {
            return isNetworkInfoStatus(context,NETWORK_AVAILABLE)
        }

        /**
         * check NetworkConnected
         *
         * @param context
         * @return
         */
        fun isNetworkConnected(context: Context?): Boolean {
            return isNetworkInfoStatus(context,NETWORK_CONNECTED)
        }

    /**
     * isWifi
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun isWifi(context: Context?): Boolean {
       return isNetworkInfoStatus(context,NETWORK_WIFI)
    }

    /**
     * 判断MOBILE网络是否可用
     */
    fun isMobile(context: Context?): Boolean {
        return isNetworkInfoStatus(context, NETWORK_MOBILE)
    }

    /**
     * check is3G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is3G(context: Context?): Boolean {
        return isNetworkInfoStatus(context, NETWORK_3G)
    }

    /**
     * is2G
     *
     * @param context
     * @return boolean
     */
    @JvmStatic
    fun is2G(context: Context?): Boolean {
        return isNetworkInfoStatus(context, NETWORK_2G)
    }



        /**
         * 得到ip地址
         *
         * @return
         */
        @JvmStatic
     fun getLocalIpAddress(): String {
            var ret = ""
            try {
                val en = NetworkInterface.getNetworkInterfaces()
                while (en.hasMoreElements()) {
                    val enumIpAddress = en.nextElement().inetAddresses
                    while (enumIpAddress.hasMoreElements()) {
                        val netAddress = enumIpAddress.nextElement()
                        if (!netAddress.isLoopbackAddress) {
                            ret = netAddress.hostAddress.toString()
                        }
                    }
                }
            } catch (ex: SocketException) {
                ex.printStackTrace()
            }

            return ret
        }


        /**
         * ping "http://www.baidu.com"
         *
         * @return
         */
        @JvmStatic
        private fun pingNetWork(): Boolean {
            var result = false
            var httpUrl: HttpURLConnection? = null
            try {
                httpUrl = URL("http://www.baidu.com")
                        .openConnection() as HttpURLConnection
                httpUrl.connectTimeout = TIMEOUT
                httpUrl.connect()
                result = true
            } catch (e: IOException) {
            } finally {
                if (null != httpUrl) {
                    httpUrl.disconnect()
                }
            }
            return result
        }

    }