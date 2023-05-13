package strawberry.com.strawberry;

import java.util.HashMap;

/**
 * Created by huanggang on 2018/3/29.
 */

public class UtilsKey {

    /**
     * 询问页面地址
     */
    public static final String PAGE_CODE = "5A A5 03 81 03 02";

    /**
     * 回答页面地址
     */
    public static final String PAGE_CODE_ANSWER = "5A A5 05 81 03 02 ";

    /**
     * 统一切面头
     */
    public static final String PAGE_CODE_HEAD = "5A A5 04 80 03 ";

    /**
     * 登录失败页面
     */
    public static final String PAGE_CODE_LOGIN_FAIL = "5A A5 04 80 03 00 04";

    /**
     * 登录详情页
     */
    public static final String PAGE_CODE_LOGIN_DETAIL = "5A A5 04 80 03 00 05";

    /**
     * 吸水按钮现在数值
     */
    public static final String PAGE_CODE_1 = "5A A5 04 83 00 65 01";
    public static final String PAGE_CODE_1_ANSWER = "5A A5 06 83 00 65 01";

    /**
     * 刷盘按钮现在数值
     */
    public static final String PAGE_CODE_2 ="5A A5 04 83 00 75 01";
    public static final String PAGE_CODE_2_ANSWER ="5A A5 06 83 00 75 01";

    /**
     * 水量现在数值
     */
    public static final String PAGE_CODE_3 = "5A A5 04 83 00 55 01";
    public static final String PAGE_CODE_3_ANSWER ="5A A5 06 83 00 55 01";


    /**
     * 提交开机密码
     * 密码框清零
     */
    public static final String PAGE_CODE_PWD = "5A A5 06 83 00 01 01";
    public static final String PAGE_CODE_PWDII = "5A A5 07 83 00 01 01";


    /**
     * 累计清零
     */
    public static final String PAGE_CODE_CLEAR = "5A A5 06 83 00 35 01";

    /**
     * 累计清零 接收
     */
    public static final String PAGE_CODE_CLEAR_RECEIVE = "5A A5 05 82 00 35 ";

    public static final String PAGE_CODE_CLEAR_RECEIVEx = "5A A5 04 83 00 35 ";


    /**
     * 重置密码成功
     * 5A A5 04 80 03 00 0B
     */
    public static final String PAGE_CODE_RESET_SUCCESS = PAGE_CODE_HEAD+"00 0B";

    /**
     * 重置密码失败
     * 5A A5 04 80 03 00 0A
     */
    public static final String PAGE_CODE_RESET_FAIL = PAGE_CODE_HEAD+"00 0A";

    /**
     * 确认密码
     */
    public static final String PAGE_CODE_RESET_PWDI_CONFIRM = "5A A5 04 83 00 10 01";
    public static final String PAGE_CODE_RESET_PWDII_CONFIRM = "5A A5 04 83 00 20 01";


    /**
     * 重置密码1
     */
    public static final String PAGE_CODE_RESET_PWDI = "5A A5 06 83 00 10 01";
    public static final String PAGE_CODE_RESET_PWDIx = "5A A5 07 83 00 10 01";

    /**
     * 重置密码2
     */
    public static final String PAGE_CODE_RESET_PWDII = "5A A5 06 83 00 20 01";
    public static final String PAGE_CODE_RESET_PWDIIx = "5A A5 07 83 00 20 01";

    /**
     * 吸水电流
     */
    public static final String PAGE_CODE_ELETRIC_I = "5A A5 05 82 00 80";


    public static final String PAGE_CODE_ELETRIC_Ix = "82 00 80";

    /**
     * 刷盘电流
     */
    public static final String PAGE_CODE_ELETRIC_II = "5A A5 05 82 00 85";

    public static final String PAGE_CODE_ELETRIC_IIx = "82 00 85";


    /**
     * 小计时间
     */
    public static final String PAGE_CODE_TIME_I = "5A A5 05 82 00 90";


    public static final String PAGE_CODE_TIME_Ix = "82 00 90";

    /**
     * 累计时间
     */
    public static final String PAGE_CODE_TIME_II = "5A A5 07 82 00 95";

    public static final String PAGE_CODE_TIME_IIx = "82 00 95";


    /**
     * 电量
     */
    public static final String PAGE_CODE_POWER = "5A A5 05 82 00 40";

    public static final String PAGE_CODE_POWERx = "5A A5 05 82 00 40";


    /**
     * 水量图标
     */
    public static final String PAGE_CODE_WATERI = "5A A5 05 82 00 50";

    public static final String PAGE_CODE_WATERIx = "82 00 50";

    /**
     * 吸水
     */
    public static final String PAGE_CODE_WATERII = "5A A5 05 82 00 60";


    public static final String PAGE_CODE_WATERIIx = "82 00 60";

    /**
     * 刷盘
     */
    public static final String PAGE_CODE_BRUSH = "5A A5 05 82 00 70";


    public static final String PAGE_CODE_BRUSHx = "82 00 70";


    //1黑色是 00 00
    //2红色是 F8 00

    /**
     * 吸水电流颜色显示的数据地址是 10 03
     */

    public static final String PAGE_CODE_ELETRIC_I_BLACK = "5A A5 05 82 10 03 00 00";

    public static final String PAGE_CODE_ELETRIC_I_RED = "5A A5 05 82 10 03 F8 00";

    /**
     * 刷盘电流颜色显示的数据地址是 10 53
     */
    public static final String PAGE_CODE_ELETRIC_II_BLACK = "5A A5 05 82 10 53 00 00";

    public static final String PAGE_CODE_ELETRIC_II_RED = "5A A5 05 82 10 53 F8 00";

    /**
     * 状态颜色显示的数据地址是 11 03
     */
    public static final String PAGE_CODE_STATUS_BLACK = "5A A5 05 82 11 03 00 00";

    public static final String PAGE_CODE_STATUS_RED = "5A A5 05 82 11 03 F8 00";

    /**
     * 吸水开关 0 是开 1是关
     */
    public static final String WATER_SWITCH = "5A A5 06 83 00 65 01 00 0";
//    public static final String WATER_OFF = "5A A5 06 83 00 65 01 00 0";

    public static final String WATER_STATUS = "5A A5 06 82 00 65 00 00";

    /**
     * 刷子开关  0 是开 1是关
     */
    public static final String BRUSH_SWITCH = "5A A5 06 83 00 75 01 00 0";
//    public static final String BRUSH_OFF = "5A A5 06 83 00 75 01 00 0";

    public static final String BRUSH_STATUS = "5A A5 05 82 00 75 00 00";

    /**
     * 水量开关 0 1 2 3
     */
    public static final String WATER_VOLUME = "5A A5 06 83 00 55 01 00 0";

    /**
     * 水量按钮
     */
    public static final String WATER_DISPLAY = "5A A5 05 82 00 55";
    public static final String WATER_DISPLAYx = "82 00 55";

    /**
     * 关机
     */
    public static final String CLOSE  = "5A A5 06 83 00 30 01 00 00";


    /**
     * 运行状态
     */
    public static final String STATUS_CODE = "5A A5 13 82 01 00 ";

    /**
     * 状态栏值
     */
    public static final HashMap<String,String> statusMap;
    static{
        statusMap = new HashMap<>();
        statusMap.put("20 20 20 20 20 20 D4 CB D0 D0 20 20 20 20 20 20","运行");
        statusMap.put("20 20 20 20 20 20 CD A3 D6 B9 20 20 20 20 20 20","停止");
        statusMap.put("20 20 B5 E7 B3 D8 B5 E7 D1 B9 B9 FD B5 CD 20 20","电池电压过低");
        statusMap.put("B5 E7 BB FA BF AA B9 D8 C9 CF B5 E7 B4 ED CE F3","电机开关上电错误");
        statusMap.put("20 20 B8 DF CE C2 BB F2 D5 DF B5 CD CE C2 20 20","高温或低温");
        statusMap.put("CE FC CB AE B5 E7 BB FA B5 E7 C1 F7 B4 ED CE F3","吸水电机电流错误");
        statusMap.put("CE FC CB AE B5 E7 BB FA BF AA C2 B7 B4 ED CE F3","吸水电机开路错误");
        statusMap.put("CB A2 C5 CC B5 E7 BB FA B5 E7 C1 F7 B4 ED CE F3","刷盘电机电流错误");
        statusMap.put("CB A2 C5 CC B5 E7 BB FA BF AA C2 B7 B4 ED CE F3","刷盘电机开路错误");
        statusMap.put("20 20 D4 A4 B3 E4 B5 E7 B9 CA D5 CF 00 20 20 20","预充电故障");
        statusMap.put("20 20 20 20 45 45 50 52 4F 4D B4 ED CE F3 20 20","EEPROM");
        statusMap.put("D6 F7 BD D3 B4 A5 C6 F7 BF AA C2 B7 B9 CA D5 CF","主接触器开路故障");
        statusMap.put("D6 F7 BD D3 B4 A5 C6 F7 B1 D5 BA CF B9 CA D5 CF","主接触器闭合故障");
        statusMap.put("20 20 CE DB CB AE BF AA B9 D8 BD D3 CD A8 20 20","污水开关接通");

    }


    /**
     * 运行状态
     */
    public static final String STATUS_CODEII = "5A A5 13 82 01 00";
    public static final String STATUS_CODEIII = "13 82 01 00";
    public static final String STATUS_CODEIV = "82 01 00";

    /**
     * 状态栏值
     */
    public static final HashMap<String,String> statusMapII;
    static{
//        82 00 95 FF FF FF FF 5A A5 13 82 01 00 20 20 20 20 20 20 CD
        statusMapII = new HashMap<>();
        statusMapII.put("202020202020D4CBD0D0202020202020","运行");
        statusMapII.put("202020202020CDA3D6B9202020202020","停止");
        statusMapII.put("2020B5E7B3D8B5E7D1B9B9FDB5CD2020","电池电压过低");
        statusMapII.put("B5E7BBFABFAAB9D8C9CFB5E7B4EDCEF3","电机开关上电错误");
        statusMapII.put("2020B8DFCEC2BBF2D5DFB5CDCEC22020","高温或低温");
        statusMapII.put("CEFCCBAEB5E7BBFAB5E7C1F7B4EDCEF3","吸水电机电流错误");
        statusMapII.put("CEFCCBAEB5E7BBFABFAAC2B7B4EDCEF3","吸水电机开路错误");
        statusMapII.put("CBA2C5CCB5E7BBFAB5E7C1F7B4EDCEF3","刷盘电机电流错误");
        statusMapII.put("CBA2C5CCB5E7BBFABFAAC2B7B4EDCEF3","刷盘电机开路错误");
        statusMapII.put("2020D4A4B3E4B5E7B9CAD5CF00202020","预充电故障");
        statusMapII.put("20202020454550524F4DB4EDCEF32020","EEPROM");
        statusMapII.put("D6F7BDD3B4A5C6F7BFAAC2B7B9CAD5CF","主接触器开路故障");
        statusMapII.put("D6F7BDD3B4A5C6F7B1D5BACFB9CAD5CF","主接触器闭合故障");
        statusMapII.put("2020CEDBCBAEBFAAB9D8BDD3CDA82020","污水开关接通");
    }


    //卸载刷子
    public static final String UNLOAD_BRUSH_CODE_1 = "5A A5 06 83 00 97 01 ";
    public static final String UNLOAD_BRUSH_CODE_2 = "5A A5 06 83 00 99 01 ";
    public static final String UNLOAD_BRUSH_CODE_3 = "5A A5 06 83 00 98 01 ";


    public static final String UNLOAD_BRUSH_CODE_4 = "5A A5 05 82 00 97 ";
    public static final String UNLOAD_BRUSH_CODE_5 = "5A A5 05 82 00 99 ";
    public static final String UNLOAD_BRUSH_CODE_6 = "5A A5 05 82 00 98 ";


    //卸刷
    public static final String UNLOAD_BRUSH_CODE_1_RE = "5A A5 04 83 00 97 01 ";
    public static final String UNLOAD_BRUSH_CODE_2_RE = "5A A5 04 83 00 99 01 ";
    public static final String UNLOAD_BRUSH_CODE_3_RE = "5A A5 04 83 00 98 01 ";

}
