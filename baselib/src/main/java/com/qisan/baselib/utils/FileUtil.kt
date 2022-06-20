package com.qisan.baselib.utils

import android.util.Log
import java.io.*
import java.nio.channels.FileChannel
import java.util.*

/**
 * Created by qisan 2022/6/17
 * com.qisan.wanandroid.utils
 */
object FileUtil {

    private val TAG = FileUtil::class.java.simpleName

    /**
     * 文件转字节
     */
    fun file2Bytes(filePath: String): ByteArray? {
        var buffer: ByteArray? = null
        try {
            val file = File(filePath)
            if (!file.exists()) {
                Log.w(TAG, "$filePath file is not exist!")
                return null
            }
            val fis = FileInputStream(file)
            val bos = ByteArrayOutputStream(1024)
            val b = ByteArray(1024)
            var n: Int
            while (fis.read(b).also { n = it } != -1) {
                bos.write(b, 0, n)
            }
            fis.close()
            bos.close()
            buffer = bos.toByteArray()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return buffer
    }

    /**
     * 字节转文件
     */
    fun bytes2File(bfile: ByteArray?, filePath: String?): File? {
        var fos: FileOutputStream? = null
        var bos: BufferedOutputStream? = null
        var file: File? = null
        try {
            file = filePath?.let { File(it) }
            if (file?.parentFile != null && !file.parentFile?.exists()!!) {
                file.parentFile?.mkdirs()
            }
            if (file != null) {
                if (!file.exists()) {
                    file.createNewFile()
                }
            }
            fos = FileOutputStream(file)
            bos = BufferedOutputStream(fos)
            bos.write(bfile)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            if (bos != null) {
                try {
                    bos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
            if (fos != null) {
                try {
                    fos.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
        Log.i(TAG, "file : $file")
        return file
    }

    /**
     * 大文件转字节（推荐，性能较好）
     *
     * @param filePath 文件的绝对路径
     * @return 转换后的字节数组
     */
    fun bigFile2Bytes(filePath: String?): ByteArray? {
        var result: ByteArray? = null
        var fc: FileChannel? = null
        try {
            fc = RandomAccessFile(filePath, "rw").channel
            val byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
                fc.size()).load()
            Log.i(TAG, "byteBuffer isLoaded :" + byteBuffer.isLoaded)
            result = ByteArray(fc.size().toInt())
            if (byteBuffer.remaining() > 0) {
                byteBuffer[result, 0, byteBuffer.remaining()]
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fc?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        Log.i(TAG, "bigFile2Bytes result: " + Arrays.toString(result))
        return result
    }

    /**
     * InputStream to byte
     *
     * @param inputStream xx
     * @return xx
     */
    fun readBinaryFileContent(inputStream: InputStream?): ByteArray? {
        try {
            if (inputStream == null) {
                return null
            }
            val baos = ByteArrayOutputStream()
            val buff = ByteArray(1024)
            var len: Int
            while (inputStream.read(buff).also { len = it } != -1) {
                baos.write(buff, 0, len)
            }
            baos.flush()
            return baos.toByteArray()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

}