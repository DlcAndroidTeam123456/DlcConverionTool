package com.dlc.dlcconversiontool;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class HexTool {
    private static final String HEX_CHARS = "0123456789abcdef";

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(HEX_CHARS.charAt(bytes[i] >>> 4 & 0x0F));
            sb.append(HEX_CHARS.charAt(bytes[i] & 0x0F));
        }
        return sb.toString();
    }

    /**
     * 十六进制字符串转字节数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexStringToBytes(String hex) {
        byte[] buf = new byte[hex.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++) {
            buf[i] = (byte) ((Character.digit(hex.charAt(j++), 16) << 4) | Character
                    .digit(hex.charAt(j++), 16));
        }
        return buf;
    }

    /**
     * 十六进制字符串转中文内容
     *
     * @param hex
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String hexStringToContent(String hex) throws UnsupportedEncodingException {
        return new String(hexStringToBytes(hex), "GB2312");
    }

    /**
     * 十六进制字符串转double类型，常用于经纬度转换
     *
     * @param hex
     * @return
     */
    public static double hexStringToDouble(String hex) {
        return Double.longBitsToDouble(Long.valueOf(hex, 16));
    }

    /**
     * double类型转十六进制字符串，常用于经纬度转换
     *
     * @param d
     * @return
     */
    public static String doubleToHexString(double d) {
        return Long.toHexString(Double.doubleToLongBits(d));
    }

    /**
     * @param data
     * @param keepByteLength 保留多少字节数，用于补0
     * @return
     */
    public static String getDataLengthHexStr(String data, int keepByteLength) {
        int length = data.getBytes().length;
        String dataLength = Integer.toHexString(length);
        while (dataLength.length() < keepByteLength * 2) {
            dataLength = "0" + dataLength;
        }
        return dataLength;
    }

    /**
     * int转byte数组，高字节在前
     *
     * @param val
     * @param byteLength 数组长度
     * @return
     */
    public static byte[] intToBytes(int val, int byteLength) {
        byte[] bytes = new byte[byteLength];
        if (byteLength == 1) {
            bytes[0] = (byte) (val & 0xff);
        }
        if (byteLength == 2) {
            bytes[1] = (byte) (val & 0xff);
            bytes[0] = (byte) ((val >> 8) & 0xff);
        }
        if (byteLength == 3) {
            bytes[2] = (byte) (val & 0xff);
            bytes[1] = (byte) ((val >> 8) & 0xff);
            bytes[0] = (byte) ((val >> 16) & 0xff);
        }
        if (byteLength == 4) {

            bytes[3] = (byte) (val & 0xff);
            bytes[2] = (byte) ((val >> 8) & 0xff);
            bytes[1] = (byte) ((val >> 16) & 0xff);
            bytes[0] = (byte) ((val >> 24) & 0xff);
        }
        return bytes;
    }

    /**
     * 获取异或结果
     *
     * @param data
     * @return
     */
    public static byte getXOR(byte[] data) {
        byte temp = data[0];
        for (int i = 1; i < data.length; i++) {
            temp ^= data[i];
        }
        return temp;
    }

    /**
     * 把所有数据进行异或，并把异或结果加在数据后进行返回
     *
     * @param temp
     * @return
     */
    public static byte[] getDataAfterXOR(byte[] temp) {
        byte xor = getXOR(temp);
        byte[] data = new byte[temp.length + 1];
        System.arraycopy(temp, 0, data, 0, temp.length);
        data[data.length - 1] = xor;
        return data;
    }

    /**
     * 数组合并，并返回一个新的数组
     *
     * @param data1
     * @param data2
     * @return
     */
    public static byte[] mergeData(byte[] data1, byte[] data2) {
        byte[] finalData = new byte[data1.length + data2.length];
        System.arraycopy(data1, 0, finalData, 0, data1.length);
        System.arraycopy(data2, 0, finalData, data1.length, data2.length);
        return finalData;
    }

    /**
     * 获取数组的某一段并返回
     * @param data 要获取的数组
     * @param start 开始字节位置
     * @param end 结束字节位置
     * @return
     */
    public static byte[] getSomeData(byte[] data, int start, int end) {
        byte[] finalData = new byte[end - start + 1];
        System.arraycopy(data, start, finalData, 0, end - start + 1);
        return finalData;
    }

    /**
     * 字节数组转int
     * @param bytes
     * @param isHighFront 是否高字节在前
     * @return
     */
    public static int byteArrayToInt(byte[] bytes, boolean isHighFront) {
        if (isHighFront) {
         return Integer.parseInt(HexTool.bytesToHexString(bytes));
        } else {
            byte[] finalBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                finalBytes[i] = bytes[bytes.length-1-i];
            }
            return Integer.parseInt(HexTool.bytesToHexString(finalBytes));
        }
    }

    /**
     * 获取指定数据的从左开始第n个bit位的值
     *
     * @param bytes      包含目标数据的原始字节数组
     * @param dataOffset 目标数据在原始字节数组中的开始位置，从0开始
     * @param bitPos     需要获取的bit位在目标数据中的位置，从1开始，比如需要获取目标数组第2个字节的第3个bit位，则bitPos=(2-1)*8+3
     * @return
     */
    public static int getBitFromLeft(byte[] bytes, int dataOffset, int bitPos) {
        int byteIndex = dataOffset + (bitPos - 1) / 8;
        int bitOffset = 8 - (bitPos - 1) % 8 - 1;
        if ((bytes[byteIndex] & (1 << bitOffset)) != 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取指定数据的从左开始第n个bit位的值
     *
     * @param bytes  目标数据
     * @param bitPos 需要获取的bit位在目标数据中的从左开始位置，从1开始，比如需要获取目标数组第2个字节的第3个bit位，则bitPos=(2-1)*8+3
     * @return
     */
    public static int getBitFromLeft(byte[] bytes, int bitPos) {
        return getBitFromLeft(bytes, 0, bitPos);
    }

    /**
     * 获取指定数据的从右开始第n个bit位的值
     *
     * @param bytes      包含目标数据的原始字节数组
     * @param dataOffset 目标数据在原始字节数组中的开始位置，从0开始
     * @param dataLen    目标数据的字节长度
     * @param bitPos     需要获取的bit位在目标数据中的从右开始位置，从1开始，比如需要获取目标数组第2个字节的第3个bit位，则bitPos=(2-1)*8+3
     * @return
     */
    public static int getBitFromRight(byte[] bytes, int dataOffset, int dataLen, int bitPos) {
        int byteIndex = dataOffset + dataLen - 1 - (bitPos - 1) / 8;
        int bitOffset = (bitPos - 1) % 8;
        if ((bytes[byteIndex] & (1 << bitOffset)) != 0) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取指定数据的从右开始第n个bit位的值
     *
     * @param bytes      目标数据
     * @param bitPos     需要获取的bit位在目标数据中的从右开始位置，从1开始，比如需要获取目标数组第2个字节的第3个bit位，则bitPos=(2-1)*8+3
     * @return
     */
    public static int getBitFromRight(byte[] bytes, int bitPos) {
        return getBitFromRight(bytes, 0, bytes.length, bitPos);
    }
}
