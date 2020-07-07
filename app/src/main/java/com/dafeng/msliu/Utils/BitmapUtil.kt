package com.dafeng.msliu.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Environment
import android.os.StatFs
import android.util.Log
import java.io.*
import java.net.URL
import java.net.URLEncoder

object BitmapUtil {
    /**
     * 读取本地资源的图片
     *
     * @param context
     * @param resId
     * @return
     */
    fun ReadBitmapById(context: Context, resId: Int): Bitmap? {
        val opt = BitmapFactory.Options()
        opt.inPreferredConfig = Bitmap.Config.RGB_565
        opt.inPurgeable = true
        opt.inInputShareable = true
        // 获取资源图片
        val `is` = context.resources.openRawResource(resId)
        return BitmapFactory.decodeStream(`is`, null, opt)
    }

    /***
     * 根据资源文件获取Bitmap
     *
     * @param context
     * @param drawableId
     * @return
     */
    fun ReadBitmapById(
        context: Context, drawableId: Int,
        screenWidth: Int, screenHight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        options.inInputShareable = true
        options.inPurgeable = true
        val stream = context.resources.openRawResource(drawableId)
        val bitmap = BitmapFactory.decodeStream(stream, null, options)
        return getBitmap(bitmap, screenWidth, screenHight)
    }

    /***
     * 等比例压缩图片
     *
     * @param bitmap
     * @param screenWidth
     * @param screenHight
     * @return
     */
    fun getBitmap(
        bitmap: Bitmap?, screenWidth: Int,
        screenHight: Int
    ): Bitmap {
        val w = bitmap!!.width
        val h = bitmap.height
        Log.e("jj", "图片宽度$w,screenWidth=$screenWidth")
        val matrix = Matrix()
        val scale = screenWidth.toFloat() / w
        val scale2 = screenHight.toFloat() / h

        // scale = scale < scale2 ? scale : scale2;

        // 保证图片不变形.
        matrix.postScale(scale, scale)
        // w,h是原图的属性.
        return Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true)
    }

    /***
     * 保存图片至SD卡
     *
     * @param bm
     * @param url
     * @param quantity
     */
    private const val FREE_SD_SPACE_NEEDED_TO_CACHE = 1
    private const val MB = 1024 * 1024
    const val DIR = "/sdcard/hypers"
    fun saveBmpToSd(bm: Bitmap, url: String, quantity: Int) {
        // 判断sdcard上的空间
        if (FREE_SD_SPACE_NEEDED_TO_CACHE > freeSpaceOnSd()) {
            return
        }
        if (Environment.MEDIA_MOUNTED != Environment
                .getExternalStorageState()
        ) return
        // 目录不存在就创建
        val dirPath = File(DIR)
        if (!dirPath.exists()) {
            dirPath.mkdirs()
        }
        val file = File("$DIR/$url")
        try {
            file.createNewFile()
            val outStream: OutputStream = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, quantity, outStream)
            outStream.flush()
            outStream.close()
        } catch (e: FileNotFoundException) {
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    /***
     * 获取SD卡图片
     *
     * @param url
     * @param quantity
     * @return
     */
    fun GetBitmap(url: String?, quantity: Int): Bitmap? {
        var inputStream: InputStream? = null
        var filename: String? = ""
        var map: Bitmap? = null
        var url_Image: URL? = null
        var LOCALURL = ""
        if (url == null) return null
        try {
            filename = url
        } catch (err: Exception) {
        }
        LOCALURL = URLEncoder.encode(filename)
        if (Exist("$DIR/$LOCALURL")) {
            map = BitmapFactory.decodeFile("$DIR/$LOCALURL")
        } else {
            try {
                url_Image = URL(url)
                inputStream = url_Image.openStream()
                map = BitmapFactory.decodeStream(inputStream)
                // url = URLEncoder.encode(url, "UTF-8");
                map?.let { saveBmpToSd(it, LOCALURL, quantity) }
                inputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }
        }
        return map
    }

    /***
     * 判断图片是存在
     *
     * @param url
     * @return
     */
    fun Exist(url: String): Boolean {
        val file = File(DIR + url)
        return file.exists()
    }

    /**
     * 计算sdcard上的剩余空间 * @return
     */
    private fun freeSpaceOnSd(): Int {
        val stat = StatFs(
            Environment.getExternalStorageDirectory()
                .path
        )
        val sdFreeMB = stat.availableBlocks.toDouble() * stat
            .blockSize.toDouble() / MB
        return sdFreeMB.toInt()
    }
}