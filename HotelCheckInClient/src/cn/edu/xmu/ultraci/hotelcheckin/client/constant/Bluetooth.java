package cn.edu.xmu.ultraci.hotelcheckin.client.constant;

import java.util.UUID;

/**
 * 蓝牙打印机协议
 * 
 * @author LuoXin
 *
 */
public class Bluetooth {
	public static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	public static final byte[][] PRINTER_CMD = { { 0x1b, 0x40 }, // 0.复位打印机
			{ 0x1b, 0x4d, 0x00 }, // 1.标准ASCII字体
			{ 0x1b, 0x4d, 0x01 }, // 2.压缩ASCII字体
			{ 0x1d, 0x21, 0x00 }, // 3.字体不放大
			{ 0x1d, 0x21, 0x11 }, // 4.宽高加倍
			{ 0x1b, 0x45, 0x00 }, // 5.取消加粗模式
			{ 0x1b, 0x45, 0x01 }, // 6.选择加粗模式
			{ 0x1d, 0x42, 0x00 }, // 7.取消黑白反显
			{ 0x1d, 0x42, 0x01 }, // 8.选择黑白反显
	};
}
