package com.dlc.dlcconversiontool;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2018\5\18 0018.
 */

public class HexTool {
    private static final String HEX_CHARS = "0123456789abcdef";
    private static HexTool instance;

    public static HexTool getInstance() {
        if (instance == null) {
            instance = new HexTool();
        }
        return instance;
    }

    /**
     * 字节数组转十六进制字符串
     *
     * @param bytes
     * @return
     */
    public String bytesToHexString(byte[] bytes) {
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
    public byte[] hexStringToBytes(String hex) {
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
    public String hexStringToContent(String hex) throws UnsupportedEncodingException {
        return new String(hexStringToBytes(hex), "GB2312");
    }

    /**
     * 十六进制字符串转double类型，常用于经纬度转换
     *
     * @param hex
     * @return
     */
    public double hexStringToDouble(String hex) {
        return Double.longBitsToDouble(Long.valueOf(hex, 16));
    }

    /**
     * double类型转十六进制字符串，常用于经纬度转换
     *
     * @param d
     * @return
     */
    public String doubleToHexString(double d) {
        return Long.toHexString(Double.doubleToLongBits(d));
    }

    /**
     * 获取异或和，支持多字节
     *
     * @param hex
     * @return
     */
    public String getXOR(String hex) {
        if (hex.length() == 0) {
            return null;
        }
        if (hex.length() % 2 != 0) {
            hex = "0" + hex;
        }
        int or = 0;
        for (int i = 0, size = hex.length(); i < size; i = i + 2) {
            String subHex = hex.substring(i, i + 2);
            or = or ^ Integer.parseInt(subHex, 16);
        }
        String xor = Integer.toHexString(or) + "";
        if (xor.length() == 1) {
            xor = "0" + xor;
        }
        return xor;
    }

    /**
     * @param data
     * @param keepByteLength 保留多少字节数，用于补0
     * @return
     */
    public String getDataLengthHexStr(String data, int keepByteLength) {
        int length = data.getBytes().length;
        String dataLength = Integer.toHexString(length);
        while (dataLength.length() < keepByteLength * 2) {
            dataLength = "0" + dataLength;
        }
        return dataLength;
    }
}
