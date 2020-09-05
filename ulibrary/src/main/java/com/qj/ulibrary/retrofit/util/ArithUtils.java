package com.qj.ulibrary.retrofit.util;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Description: double运算工具类
 */

public class ArithUtils {
    //volatile关键字确保当instance被初始化为ChineseUtil的实例时，多个线程正确处理instance变量
    private volatile static ArithUtils instance;

    /**
     * 单例
     *
     * @return
     */
    public static ArithUtils getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (ArithUtils.class) {
                if (instance == null) {
                    instance = new ArithUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 由于Java的简单类型不能够精确的对浮点数进行运算，这个工具类提供精
     * 确的浮点数运算，包括加减乘除和四舍五入。
     */
    //默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    //这个类不能实例化
    private ArithUtils() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的类型转换(Float)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }

    /**
     * 提供精确的类型转换(Int)不进行四舍五入
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * 提供精确的类型转换(Long)
     *
     * @param v 需要被转换的数字
     * @return 返回转换结果
     */
    public long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * 返回两个数中大的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     */
    public double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }

    /**
     * 返回两个数中小的一个值
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中小的一个值
     */
    public double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }

    /**
     * 解析字符串获得双精度型数值，
     * @param str
     * @return
     */
    public static double getDoubleValue(String str)
    {
        double d = 0;

        if(str!=null && str.length()!=0)
        {
            StringBuffer bf = new StringBuffer();

            char[] chars = str.toCharArray();
            for(int i=0;i<chars.length;i++)
            {
                char c = chars[i];
                if(c>='0' && c<='9')
                {
                    bf.append(c);
                }
                else if(c=='.')
                {
                    if(bf.length()==0)
                    {
                        continue;
                    }
                    else if(bf.indexOf(".")!=-1)
                    {
                        break;
                    }
                    else
                    {
                        bf.append(c);
                    }
                }
                else
                {
                    if(bf.length()!=0)
                    {
                        break;
                    }
                }
            }
            try
            {
                d = Double.parseDouble(bf.toString());
            }
            catch(Exception e)
            {}
        }

        return d;
    }

    /***
     * 取小数点前边
     * @param str
     * @param character
     * @return
     */
    public static String removeCharacters(String str, String character) {
        if (str.contains(character)) {
            int index = str.indexOf(character);
            str = str.substring(0, index);
        }else {
            return str;
        }
        return str;
    }

    /**
     * 找出数组中的最大值
     *
     * @param arr 目标数组
     * @return 最大值
     */
    public static float getMax(List<Float> arr) {
        float max = arr.get(0);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(0) > max) {
                max = arr.get(0);
            }
        }
        return max;
    }

    /***
     * 对100取整
     * @param data
     * @param mode
     * @return
     */
    public static double getModeData(double data,int mode){
        while (data % mode != 0) {
            data++;
        }
        return data;
    }
}
