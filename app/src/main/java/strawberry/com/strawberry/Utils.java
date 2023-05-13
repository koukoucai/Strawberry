package strawberry.com.strawberry;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.example.androidbluetooch.BluetoothLeService;

/**
 * 工具类
 * Created by huanggang on 2018/3/29.
 */

public class Utils {
    private static final String TAG = "Utils";

    /**
     * 写数据
     * @param bluetoothLeService
     * @param target_chara
     * @param dataImpl
     */
    public static void writeCharacteristic(BluetoothLeService bluetoothLeService, BluetoothGattCharacteristic target_chara, DataImpl dataImpl) {
        byte[] bytes = dataImpl.dataResult();
        if (bluetoothLeService == null) return;

        if (target_chara == null) return;

        target_chara.setValue(bytes);
        bluetoothLeService.writeCharacteristic(target_chara);
    }


    public interface DataImpl {
        byte[] dataResult();
    }


    /**
     * 写数据
     * @param bluetoothLeService
     * @param target_chara
     * @param bytes
     */
    public static void writeCharacteristic(BluetoothLeService bluetoothLeService, BluetoothGattCharacteristic target_chara, byte[] bytes) {

//        Log.e(TAG, "writeCharacteristic:1111 bluetoothLeService == null.......+bytes==null....."+(bytes==null));
        if (bluetoothLeService == null) return;
//        Log.e(TAG, "writeCharacteristic:2222 target_chara == null .........."+(target_chara == null));
        if (target_chara == null) return;

//        Log.e(TAG, "writeCharacteristic:3333 实际写入............");
        target_chara.setValue(bytes);
        bluetoothLeService.writeCharacteristic(target_chara);
    }


    /**
     * 十六进制转byte数组
     * @param hex
     * @return
     */
    public static byte[] hexString2Bytes(String hex) {
        Log.e(TAG, "hexString2Bytes: hex： "+hex );
        hex = hex.replace(" ","");

        if ((hex == null) || (hex.equals(""))){
            return null;
        }
        else if (hex.length()%2 != 0){
            return null;
        }
        else{
            hex = hex.toUpperCase();
            int len = hex.length()/2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i=0; i<len; i++){
                int p=2*i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p+1]));
            }
            return b;
        }

    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    /*
    * 10进制转字节
    */
    public static byte int2Byte(int i){
        byte r = (byte) i;
        return r;
    }


    /*
     * 字节数组转16进制字符串
    */
    public static String bytes2HexString(byte[] b) {
        String r = "";

        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            r += hex.toUpperCase();
        }

        return r;
    }

    /**
     * 十六进制转10进制
     * @param hex
     * @return
     */
    public static int hexToInt(String hex){
        hex = hex.replace(" ","");
        if ((hex == null) || (hex.equals(""))){
            return 0;
        }

        if (hex.contains("0x")){
            return Integer.parseInt(hex.substring(2),16);
        }else{
            return Integer.parseInt(hex,16);
        }
    }

    /**
     * 十六进制转10进制  除以10
     * @param hex
     * @return
     */
    public static String hexToIntString(String hex){
        int hexInt = hexToInt(hex);
        float hexFloat = Float.valueOf(hexInt)/10;
        return String.valueOf(hexFloat);
    }

    /**
     * 地址转10进制
     * @param data
     * @param address
     * @return
     */
    public static String dataToHexToIntString(String data,String address) throws Exception{
        data = data.replace(" ","");
        address = address.replace(" ","");
        String result = data.substring(data.indexOf(address)+address.length(),data.indexOf(address)+address.length()+4);
        return hexToIntString(result);
    }


    /**
     * 地址转10进制
     * @param data
     * @param address
     * @return
     */
    public static String dataToHexToIntStringEight(String data,String address) throws Exception{
        data = data.replace(" ","");
        address = address.replace(" ","");
        String result = data.substring(data.indexOf(address)+address.length(),data.indexOf(address)+address.length()+8);
        return hexToIntString(result);
    }


    /**
     * 地址转10进制
     * 不除10
     * @param data
     * @param address
     * @return
     */
    public static int dataToHexToIntStringII(String data,String address) throws Exception{
        data = data.replace(" ","");
        address = address.replace(" ","");
        String result = data.substring(data.indexOf(address)+address.length(),data.indexOf(address)+address.length()+4);
        Log.e(TAG, "dataToHexToIntStringII: result "+result );
        return hexToInt(result);
    }


    /**
     * 获取电量
     * @param power
     * @return
     */
    public static int getPowerImg(int power){
        int id = R.drawable.power0;
        switch (power){
            case 0:
                id = R.drawable.power0;
                break;
            case 1:
                id = R.drawable.power1;
                break;
            case 2:
                id = R.drawable.power2;
                break;
            case 3:
                id = R.drawable.power3;
                break;
            case 4:
                id = R.drawable.power4;
                break;
            case 5:
                id = R.drawable.power5;
                break;
            case 6:
                id = R.drawable.power6;
                break;
            case 7:
                id = R.drawable.power7;
                break;
            case 8:
                id = R.drawable.power8;
                break;
            case 9:
                id = R.drawable.power9;
                break;
        }

        return id;
    }

    /**
     * 水量
     * @param water
     * @return id
     */
    public static int getWaterImg(int water){
        int id = R.drawable.wd0;
        switch (water) {
            case 0:
                id = R.drawable.wd0;
                break;
            case 1:
                id = R.drawable.wd1;
                break;
            case 2:
                id = R.drawable.wd2;
                break;
            case 3:
                id = R.drawable.wd3;
                break;
        }
        return id;
    }



    /**
     * 吸水
     * @param water
     * @return id
     */
    public static int getWaterImgII(int water){
        int id = R.drawable.water0;
        switch (water) {
            case 0:
                id = R.drawable.water0;
                break;
            case 1:
                id = R.drawable.water_ani;
                break;
            case 2:
                id = R.drawable.water4;
                break;
        }
        return id;
    }


    /**
     * 刷盘
     * @param brush
     * @return id
     */
    public static int getBrushImg(int brush){
        int id = R.drawable.brush0;
        switch (brush) {
            case 0:
                id = R.drawable.brush0;
                break;
            case 1:
                id = R.drawable.brush_ani;
                break;
            case 2:
                id = R.drawable.brush4;
                break;
        }
        return id;
    }

    /**
     * 调节水量
     * @param volume
     * @return
     */
    public static int getVolumeWaterImg(int volume){
        int id = R.drawable.wb0;
        switch (volume) {
            case 0:
                id = R.drawable.wb0;
                break;
            case 1:
                id = R.drawable.wb1;
                break;
            case 2:
                id = R.drawable.wb2;
                break;
            case 3:
                id = R.drawable.wb3;
                break;
        }
        return id;
    }

    /**
     * 判断密码长度，不足补0
     *  Log.e(TAG, "onCreate: 99999:" +Integer.toHexString(Integer.valueOf("99999")) );//1869f
     Log.e(TAG, "onCreate:十六进制  fffff:" +hexToInt("fffff") );//1869f  4095<  <=  1859f  改为 07

     Log.e(TAG, "onCreate:十六进制  ffff:" +hexToInt("ffff") );//65535   4095<   <=65535   OK
     Log.e(TAG, "onCreate:十六进制  fff:" +hexToInt("fff") );//4095      255<   <=4095  补一位 0
     Log.e(TAG, "onCreate:十六进制  ff:" +hexToInt("ff") );//255         15<    <=255   补两位 00
     Log.e(TAG, "onCreate:十六进制  f:" +hexToInt("f") );//15            <= 15  补三位 000

     * @return
     */
    public static int passwordJudgement(int pwd){
        if (4095< pwd&&pwd <=65535 ) return 0;
        if (255< pwd&&pwd  <=4095 )  return 1;
        if ( 15< pwd&&pwd   <=255)   return 2;
        if (pwd<=15)                 return 3;
        if (pwd>65535)               return 4;

        return 0;
    }

    public static void main(String[] args) {
        String str1 = "0xdd";
        String str2 = "35 0B";
        System.out.println("str1 "+ hexToInt(str1)+ "  "+hexToIntString(str1) );
        System.out.println("str2 "+ hexToInt(str2)+ "  "+hexToIntString(str2) );

        String testKet = "5a 5a a5 "+UtilsKey.PAGE_CODE_ELETRIC_I+" 00 65"+" ss ssss";

        String result = testKet.substring(testKet.indexOf(UtilsKey.PAGE_CODE_ELETRIC_I)+UtilsKey.PAGE_CODE_ELETRIC_I.length(),testKet.indexOf(UtilsKey.PAGE_CODE_ELETRIC_I)+(UtilsKey.PAGE_CODE_ELETRIC_I.length()+6));

        System.out.println("testKet: "+testKet+" result : "+result+"  index : "+testKet.indexOf(UtilsKey.PAGE_CODE_ELETRIC_I));


        String test1 = UtilsKey.PAGE_CODE_ELETRIC_I+"00 65";
        try {
            System.out.println("result； "+dataToHexToIntString(test1,UtilsKey.PAGE_CODE_ELETRIC_I));
        } catch (Exception e) {
            e.printStackTrace();
        }


        String testa = "1 2 3 4 5 ";
        String testb = "1 2 3 4 5 6 7 8";
        System.out.println("结果： "+testb.substring(testb.indexOf(testa)+testa.length(),testb.length()));
    }
}
