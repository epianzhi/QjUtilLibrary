package com.qj.ulibrary.retrofit.util;

import android.text.format.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 取系统时间工具类
 */
public class GetTime {
    //volatile关键字确保当instance被初始化为ChineseUtil的实例时，多个线程正确处理instance变量
    private volatile static GetTime instance;
    private List list = new ArrayList();
    // 当前date
    private Date curDate;

    /**
     * 单例
     *
     * @return
     */
    public static GetTime getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (GetTime.class) {
                if (instance == null) {
                    instance = new GetTime();
                }
            }
        }
        return instance;
    }

    /**
     * @param @param   size
     * @param @return2 设定文件
     * @return String 返回类型
     * @throws
     * @Title: GetTime
     * @Description: 取时间
     */
    public String GetTime(int size) {
        Time t = new Time();
        t.setToNow();
        list.add(0, t.year + "");
        list.add(1, String.format("%02d", t.month));
        list.add(2, String.format("%02d", t.monthDay));
        list.add(3, String.format("%02d", t.hour));
        list.add(4, String.format("%02d", t.minute));
        list.add(5, String.format("%02d", t.second));
        return (String) list.get(size);
    }

    /**
     * 取Date格式的当前时间
     *
     * @return
     */
    public Date GetDate() {
        curDate = new Date(System.currentTimeMillis());//获取当前时间
        return curDate;
    }

    /**
     * 把Date格式的时间转换成String
     *
     * @return
     */
    public String Format(Date date, int i) {
        String s = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
        SimpleDateFormat formatterNoBr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter3 = new SimpleDateFormat("HHmmss");
        SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter5 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        switch (i) {
            case 0:
                s = formatter.format(date);
                break;
            case 1:
                s = formatterNoBr.format(date);
                break;
            case 2:
                s = formatter2.format(date);
                break;
            case 3:
                s = formatter3.format(date);
                break;
            case 4:
                s = formatter4.format(date);
                break;
            case 5:
                s = formatter5.format(date);
                break;
            case 6:
                s = formatter6.format(date);
                break;
        }
        return s;
    }

    public String FormatTime(int i) {
        String str;
        // 获取当前时间
        curDate = new Date(System.currentTimeMillis());
        // 格式化
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd\nHH:mm:ss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat formatter3 = new SimpleDateFormat("HHmmss");
        SimpleDateFormat formatterNoBr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        SimpleDateFormat formatterDatePr = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat formatterTimePr = new SimpleDateFormat("HH:mm:ss");
        if (i == 1) {
            str = formatter.format(curDate);
        } else if (i == 2) {
            str = formatter2.format(curDate);
        } else if (i == 3) {
            str = formatter3.format(curDate);
        } else if (i == 4) {
            str = formatterNoBr.format(curDate);
        } else {
            return null;
        }
        return str;
    }

    /**
     * @param @param      l1
     * @param @param      l2
     * @param @return2    设定文件
     * @return String    返回类型
     * @throws
     * @Title: FormatTime
     * @Description: 将表中的时间long转换成时间string用于显示。
     */
    public String FormatTime(long l1, long l2) {
        // int i = S.Parse(l1);
        // 如果时间为5位数则转换为6位。 例30205转换为030205，
        String s;
        int c = Long.toString(l2).length();
        // l1.ToString().Length.ToString();
        if (c != 6) {
            s = String.format("%06d", l2);
        } else {
            s = Long.toString(l2);
        }
        String str = null;
        // 格式化
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = formatter.parse(l1 + "" + s);
            str = formatter2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // SimpleDateFormat formatter = new SimpleDateFormat(
        // "yyyy-MM-dd\nHH:mm:ss");
        // str = formatter.format(data);
        return str;
    }

    /**
     * 记录上次事件发生事件间隔(用于防止段时间内点击多次按钮导致触发多次点击事件)
     */
    private long lastClickTime = 0;

    /**
     * true:表示两次事件发生间隔小于n毫秒
     * false:大于n毫秒
     *
     * @return
     */
    public boolean isFastDoubleClick(int millisecond) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < millisecond) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 将时间转换为时间戳
     */
    public String dateToStamp(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime() / 1000;
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间转换为时间戳
     */
    public long dateToStamp2(String s, int i) {
        long res;
        Date date = new Date();
        switch (i) {
            case 1:
                SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    date = simpleDateFormat1.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    date = simpleDateFormat2.parse(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
        res = date.getTime() / 1000;
        return res;
    }
}
