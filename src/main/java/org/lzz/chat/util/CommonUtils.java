package org.lzz.chat.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.i18n.LocaleContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * 工具类
 *
 * @author cp
 */
public class CommonUtils {

    public static boolean hasFour(int a){
        String x = a+"";
        for(int i = 0; i < x.length();i++){
            if(x.charAt(i) == '4'){
                return true;
            }
        }
        return false;
    }

    /**
     * 根据反射获取当前的语言版本的该属性的数据
     * @param value
     * @param obj
     * @param getMethodName 获取属性的方法名称
     * @return
     */
    public static String GetMultiLanguageValueByReflect(String value,Object obj,String getMethodName){
        try {
            String languageKey = CommonUtils.GetLanguageKey();

            if(languageKey != null){
                Class<?> newClass = obj.getClass();

                String className = newClass.getSimpleName(); //得到类名

                Method getMultiLanguage_Method = newClass.getMethod("get"+ className +"MultiLanguage",String.class); //得到根据语言key获取当前语言版本数据的方法

                //获取到当前的语言版本数据对象
                Object currentMultiLanguage = getMultiLanguage_Method.invoke(obj,languageKey);

                if(currentMultiLanguage != null){
                    Method getFieldMultiLanguage_Method = currentMultiLanguage.getClass().getMethod(getMethodName);

                    //调用方法获取属性值
                    String tempValue = (String) getFieldMultiLanguage_Method.invoke(currentMultiLanguage);

                    if(isEmpty(tempValue)){
                        return value;
                    }
                    return tempValue;
                }else{
                    return value;
                }
            }else{
                return value;
            }
        } catch (NoSuchMethodException e) {

        } catch (InvocationTargetException e) {

        } catch (IllegalAccessException e) {

        }
        return value;
    }

    //得到当前的语言key
    public static String GetLanguageKey(){
        Locale locale = LocaleContextHolder.getLocale();
        String currentLanguageStr = locale.toString();

        if("zh_CN".equals(currentLanguageStr)){
            return null;
        }
        if("zh_TW".equals(currentLanguageStr)){
            currentLanguageStr = "cht";
            return  currentLanguageStr;
        }
        if("en_US".equals(currentLanguageStr)){
            currentLanguageStr = "en";
            return currentLanguageStr;
        }
        return null;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * @param obj
     * @return
     * @Description：判断是否为空
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if ("".equals(obj) || "null".equals(obj) || "NULL".equals(obj) || "".equals(obj.toString().trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(List list) {
        return !isEmpty(list);
    }

    /**
     * @return
     * @Description：判断是否为空
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(List list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object[] c) {
        return !isEmpty(c);
    }

    /**
     * @param c
     * @return
     * @Description：判断数组是否为空
     */
    public static boolean isEmpty(Object[] c) {
        if (c == null || c.length <= 0) {
            return true;
        }
        return false;
    }

    /**
     * 替换字符串函数
     * String strSource - 源字符串
     * String strFrom   - 要替换的子串
     * String strTo     - 替换为的字符串
     */
    public static String replace(String strSource, String strFrom, String strTo) {
        // 如果要替换的子串为空，则直接返回源串
        if (strFrom == null || strFrom.equals("")) {
            return strSource;
        }
        //String strDest = "";
        StringBuffer strDest = new StringBuffer("");
        // 要替换的子串长度
        int intFromLen = strFrom.length();
        int intPos;
        // 循环替换字符串
        while ((intPos = strSource.indexOf(strFrom)) != -1) {
            // 获取匹配字符串的左边子串
            //strDest = strDest + strSource.substring(0,intPos);
            strDest.append(strSource.substring(0, intPos));
            // 加上替换后的子串
            //strDest = strDest + strTo;
            strDest.append(strTo);
            // 修改源串为匹配子串后的子串
            strSource = strSource.substring(intPos + intFromLen);
        }
        // 加上没有匹配的子串
        //strDest = strDest + strSource;
        strDest.append(strSource);
        // 返回
        return strDest.toString();
    }

    /**
     * 为字符串替换新的分隔符
     *
     * @param str          替换的字符串值
     * @param oldSeparator 字符串中存在的分隔符
     * @param newSeparator 字符串中新的分隔符
     * @return
     */
    public static String replaceAll(String str, char oldSeparator, char newSeparator) {
        // TODO:modifier.songfq.010112 : String 组装修改
        StringBuilder v = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            v = (c == oldSeparator) ? v.append(newSeparator) : v.append(c);
        }
        return v.toString();
    }

    /**
     * 取得字符串的长度
     *
     * @param str 需要操作的字符串
     * @return
     */
    public static int length(String str) {
        if (isEmpty(str)) {
            return 0;
        }
        return str.trim().length();
    }

    /**
     * 两个日期的差
     *
     * @param param0 被减数
     * @param param1 减数
     * @return
     * @throws ParseException
     */
    public static Long subDate(Date param0, Date param1) throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        if (param0 == null) {
            return null;
        }
        if (param1 == null) {
            return null;
        }
        long quot = df.parse(df.format(param0)).getTime() - df.parse(df.format(param1)).getTime();
        return quot / 1000 / 60 / 60 / 24;
    }

    /**
     * 判断两个日期年月是否相等
     *
     * @param beginDate
     * @param endDate
     * @return
     * @Description：
     */
    public static boolean isMonthEquals(Date beginDate, Date endDate) {
        if (beginDate == null || endDate == null) {
            return false;
        }
        DateFormat df = new SimpleDateFormat("yyyyMM");
        return Integer.parseInt(df.format(beginDate)) == Integer.parseInt(df.format(endDate));
    }

    /**
     * 获取当前时间字符串 例如 2015-01-22 11:09:00
     *
     * @return
     * @Description：
     */
    public static String getNowTimeStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前日期字符串 例如 2015-01-22
     *
     * @return
     * @Description：
     */
    public static String getNowDateStr() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        return df.format(new Date());
    }

    /**
     * 获取当前日期字符串 例如 201501221421
     *
     * @return
     * @Description：
     */
    public static String getNowTimeStr2() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        return df.format(new Date());
    }

    /**
     * @param obj
     * @return
     * @Description：转换为字符串
     */
    public static String parseString(Object obj) {
        if (isEmpty(obj)) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * 判断某个字符串是否存在于数组中
     *
     * @param stringArray 原数组
     * @param source      查找的字符串
     * @return 是否找到
     */
    public static boolean arryContains(String[] stringArray, String source) {
        // 转换为list
        List<String> tempList = Arrays.asList(stringArray);
        // 利用list的包含方法,进行判断
        if (tempList.contains(source)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 格式化金额（将金额从分转为元，并统一保留两位小数）
     *
     * @Description:
     * @param amount
     * 金额
     * @return
     */
    public static final BigDecimal divisor = new BigDecimal(100);

    public static String formatAmount(String amount) {
        if (isEmpty(amount)) {
            return "0.00";
        }
        BigDecimal b1 = new BigDecimal(amount);
        BigDecimal b2 = b1.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
        return b2.toString();
    }

    // 运算时精度
    private static final int CALCULATE_SCALE = 1;
    // 最终结果显示精度
    private static final int DISPLAY_SCALE = 2;

    /**
     * 保留两位小数
     *
     * @param parameter
     * @return
     */
    public static double getDoubleForParameter(double parameter) {
        java.text.NumberFormat formater = DecimalFormat.getInstance();
        formater.setMaximumFractionDigits(2);
        formater.setMinimumFractionDigits(2);
        String value = formater.format(parameter);
        return Double.parseDouble(value);
    }

    /**
     * 处理空字符串
     *
     * @param obj
     * @return
     */
    public static String getStr(Object obj) {
        String str = "";
        if (obj != null) {
            str = obj.toString().trim();
        }
        return str;
    }

    /**
     * 转化时间成字符串
     *
     * @param dateTime 要转化的时间
     * @param format   时间格式串
     * @return 转化后的字符串
     */
    public static String formatDateToString(Date dateTime, String format) {
        try {
            SimpleDateFormat innerFormat = new SimpleDateFormat(format);

            return innerFormat.format(dateTime);
        } catch (Exception e) {
            return "";
        }
    }

    public static String formatToChinese(Date date) {
        return formatDateToString(date,"yyyy年M月d日 HH:mm");
    }

    public static String getCurrentDateTime() {
        return formatDateToString(new Date(), "yyyy-M-d HH:mm");
    }

    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static Date formatStringToDate(String dateStr, String formatStr) {
        String fStr = formatStr;
        if (dateStr == null || "".equals(dateStr.trim())) {
            return null;
        }
        if (fStr == null) {
            fStr = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(fStr);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static Date formatDateToDate(Date date, String formatStr) {
        if (date == null) {
            return null;
        } else {
            String str = CommonUtils.formatDateToString(date, formatStr);
            return CommonUtils.formatStringToDate(str, formatStr);
        }
    }

    public static Object trimString(Object source) {
        Class<? extends Object> sourceClass = source.getClass();
        Field[] fieldSource = sourceClass.getDeclaredFields();
        for (Field fs : fieldSource) {
            try {
                fs.setAccessible(true);
                Object o = fs.get(source);
                if (o != null && (o instanceof String)) {
                    fs.set(source, ((String) o).trim());
                }
            } catch (IllegalArgumentException e) {
                return null;
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return source;
    }

    public static double getDecimalDouble(double money) {
        DecimalFormat deFormat = new DecimalFormat("0.00");
        if (money != 0) {
            return Double.parseDouble(deFormat.format(money));
        } else {
            return 0;
        }
    }

    /**
     * 转换double值
     *
     * @param info
     * @return
     */
    @SuppressWarnings("static-access")
    public static double getDisplayDouble(double info) {
        BigDecimal b1 = BigDecimal.ONE.valueOf(info);
        if (info != 0) {
            return b1.setScale(DISPLAY_SCALE, RoundingMode.HALF_UP).doubleValue();
        } else {
            return 0;
        }
    }

    /**
     * 把科学计数转换成普通double类型,并以String显示
     *
     * @param info
     * @return String
     */
    public static String getDoubleChangeString(Object info) {
        if (null == info) {
            return "";
        } else {
            BigDecimal asd = new BigDecimal(Double.parseDouble(info.toString()));
            return String.valueOf(asd.setScale(2, BigDecimal.ROUND_DOWN));
        }
    }

    /**
     * 加法
     *
     * @param v1
     * @param v2
     * @return
     */
    @SuppressWarnings("static-access")
    public static double add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.ONE.valueOf(v1);
        BigDecimal b2 = BigDecimal.ONE.valueOf(v2);
        return b1.add(b2).setScale(CALCULATE_SCALE, RoundingMode.HALF_UP)
                .doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    @SuppressWarnings("static-access")
    public static double sub(double v1, double v2) {
        BigDecimal b1 = BigDecimal.ONE.valueOf(v1);
        BigDecimal b2 = BigDecimal.ONE.valueOf(v2);
        return b1.subtract(b2).setScale(CALCULATE_SCALE, RoundingMode.HALF_UP).doubleValue();

    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    @SuppressWarnings("static-access")
    public static double mul(double v1, double v2) {
        BigDecimal b1 = BigDecimal.ONE.valueOf(v1);
        BigDecimal b2 = BigDecimal.ONE.valueOf(v2);
        return b1.multiply(b2).setScale(CALCULATE_SCALE, RoundingMode.HALF_UP).doubleValue();

    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * <p>
     * 小数点以后4位，以后的数字全舍。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, CALCULATE_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * <p>
     * 定精度，以后的数字全舍
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    @SuppressWarnings("static-access")
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精度必须为正整数或零");
        }
        BigDecimal b = BigDecimal.ONE.valueOf(v1);
        BigDecimal one = BigDecimal.ONE.valueOf(v2);
        return b.divide(one, scale, BigDecimal.ROUND_DOWN).doubleValue();
    }

    /**
     * 还原字符串 存入数据库
     *
     * @param toBack 要还原的字符串
     * @return
     */
    public static String backString(String toBack) {

        if (toBack != null) {
            return toBack.replaceAll("&gt", ">").replaceAll("&lt", "<")
                    .replaceAll("&quot;", "\"");
        } else {
            return toBack;
        }
    }

    /**
     * 抹零 去除小数点后面的数据
     *
     * @param d
     * @return
     */
    @SuppressWarnings("static-access")
    public static double getValue(Double d, boolean toZero) {
        if (toZero) {
            return BigDecimal.ONE.valueOf(d).setScale(0, RoundingMode.FLOOR).intValue();
        } else {
            return BigDecimal.ONE.valueOf(d).setScale(CALCULATE_SCALE, RoundingMode.HALF_UP).doubleValue();
        }
    }

    /**
     * 获取Double 如果double为null转化为0
     *
     * @param number
     */
    public static double getDoubleValue(Double number) {
        if (number == null) {
            return 0;
        } else {
            return number;
        }
    }

    public static double formatDouble(Double d) {
        if (d == null) {
            return 0;
        }
        BigDecimal b = new BigDecimal(d);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 获取项目的工程名 格式: /xxx
     *
     * @param request
     * @return
     */
    public static String getPath(HttpServletRequest request) {
        return request.getContextPath();
    }

    /**
     * 获取工程的绝对URL 格式：http://localost:8888/xxxx/
     *
     * @param request
     * @return
     */
    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    }

    /**
     * 只允许为数字和字母
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterOne(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String    regEx   =   "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile("^[a-zA-Z0-9]+$");

        Matcher m = p.matcher(str == null ? "" : str);
        return m.find();
    }

    /**
     * 只允许为中文字符、数字、字母
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterTWO(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String    regEx   =   "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile("^[\u4e00-\u9fa5a\\w]+$");

        Matcher m = p.matcher(str == null ? "" : str);
        return m.find();
    }

    /**
     * 只允许为数字、字母、规定的特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterTHREE(String str) throws PatternSyntaxException {
        // 只允许字母和数字
        // String    regEx   =   "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile("^[a-zA-Z0-9`~!@#$%^&*()+=|{}':;',\\[\\].-<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$");

        Matcher m = p.matcher(str == null ? "" : str);
        return m.find();
    }

    /**
     * 只允许为中文、数字、字母
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterHOUR(String str) throws PatternSyntaxException {
        String regEx = "^[\u4e00-\u9fa5a\\w]+$";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 只允许为中文、数字、字母、规定的特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterFive(String str) throws PatternSyntaxException {
        String regEx = "^[\u4e00-\u9fa5a\\w`~!@#$%^&*()+=|{}':;',-\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$";

        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * 只允许为中文、数字、字母、规定的特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static boolean strFilterSix(String str) throws PatternSyntaxException {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        if (str.indexOf("<") != -1 || str.indexOf(">") != -1 || str.indexOf("\"") != -1) {
            return false;
        }
        return true;
    }

    /**
     * 处理把空字符或者空对象转换成空对象
     *
     * @param obj
     * @return
     */
    public static Object getDataToObjectByNULL(Object obj) {
        if (null == obj || "".equals(obj.toString())) {
            return null;
        } else {
            return obj;
        }
    }

    /**
     * 处理把空字符转换成空对象
     *
     * @param str
     * @return
     */
    public static String formatNullStringByUOM(String str) {
        if ("null".equals(str)) {
            return null;
        } else {
            //return str.replace(".0", "");
            return str;
        }
    }

    /**
     * 处理把空字符或者空对象转换成空对象
     *
     * @param obj
     * @return
     */
    public static String objectToString(Object obj) {
        return null == obj ? "" : obj.toString();
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean checkNULLByString(String str) {
        if (null == str || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 产生随机数
     */
    public static int getRandomRangeNum(int start, int end) {
        Random random = new Random();
        return random.nextInt(end - start + 1) + start;
    }

    /**
     * 获取序列号yyyyMMssHHmmss+6位随机数
     */
    public static String getDateSequence(int start, int end) {
        String nowDate = CommonUtils.formatDateToString(new Date(), "yyyyMMssHHmmss");
        String msgId = nowDate + CommonUtils.getRandomRangeNum(start, end);
        return msgId;
    }

    /**
     * 处理URL缓存问题
     */
    public static String processURLCache(String url) {
        String nowDate = CommonUtils.formatDateToString(new Date(), "yyyyMMssHHmmss");
        if (url.indexOf("?") >= 0) {
            return url + "&t=" + nowDate;
        } else {
            return url + "?t=" + nowDate;
        }
    }

    /**
     * 将HttpServletRequest中的参数反射至实体类
     *
     * @param <T>
     * @param obj
     * @param request
     * @return
     */
    public static <T> void reflectParameter(T obj, HttpServletRequest request) {
        try {
            //获取对象的Class
            Class<?> clazz = obj.getClass();
            //获取Class中所有已声明的属性集合
            Field[] fileds = clazz.getDeclaredFields();
            //遍历属性结合
            for (Field f : fileds) {
                //过滤被final、static修饰的成员变量
                if ((f.getModifiers() & Modifier.FINAL) > 0
                        || (f.getModifiers() & Modifier.STATIC) > 0) {
                    continue;
                }
                //取消所有私有变量的限制
                f.setAccessible(true);//取消Field的访问检查
                //获取属性的类型,long/int/string....
                Class<?> fieldType = f.getType();
                //获取属性的名字
                String fieldName = f.getName();
                //根据属性的名字从request中获取value
                String paramVal = request.getParameter(fieldName);
                //判断类型,如果是String
                if (String.class == fieldType) {
                    f.set(obj, paramVal);
                    //判断类型,如果是long,则使用NumberUtils.toLong,即使为空,也赋默认值0L
                } else if (long.class == fieldType || Long.class == fieldType) {
                    f.set(obj, NumberUtils.toLong(paramVal));
                    //判断类型,如果是int,则使用NumberUtils.toInt,即使为空,也赋默认值0
                } else if (int.class == fieldType || Integer.class == fieldType) {
                    f.set(obj, NumberUtils.toInt(paramVal));
                    //判断类型,如果是date,则使用DateUtil.parseDateNewFormat格式化日期格式
                } else if (Date.class == fieldType) {
                    f.set(obj, DateUtils.parseDate(paramVal, "yyyy-MM-dd HH:mm:ss"));
                }
            }
        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        } catch (ParseException e) {

        }
    }

    /**
     * 判断是否是邮箱
     *
     * @param email 邮箱
     * @return boolean
     */
    public static boolean isEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else {
            String reg = "^([-\\.\\w]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
            return email.matches(reg);
        }
    }

    /**
     * 判断是否是手机号
     *
     * @param mobile 手机号
     * @return boolean
     */
    public static boolean isMobile(String mobile) {
        if (mobile.isEmpty()) {
            return false;
        } else {
            String reg = "^1[3|4|5|7|8][0-9]\\d{8}$";
            return mobile.matches(reg);
        }
    }

    /**
     * 判断key是否是纯英文字母
     *
     * @param key 关键字
     * @return boolean
     */
    public static boolean isEnlish(String key) {
        if (key.isEmpty()) {
            return false;
        } else {
            String reg = "^[a-z\\sA-Z,]+$";
            return key.matches(reg);
        }
    }

    /**
     * 随机生成4位数
     * @param length
     * @return
     */
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


    /**
     * 替换html编码的转义字符
     * 解决带圆点的商品名搜索不到的问题
     * @param str
     * @return
     */
    public  static  String  replaceHtmlCode(String str){
       if(isNotEmpty(str)){
           return  str.replace("&middot;","·");
       }
       return  str;
    }

    /**
     *  双精度的数字保留x位小数
     */
    public static Double replaceDoubleToBigDecimal(Object str,int length){
        if(isNotEmpty(str))
            return new BigDecimal(str+"").setScale(length,BigDecimal.ROUND_HALF_UP).doubleValue();
        else
            return new Double(0);
    }

    public static void print(Object var1){
        System.out.print(var1);
    }

    public static void println(){
        System.out.println();
    }

    public static void println(Object var1){
        System.out.println(var1);
    }


    public static Double doubleMutiply(Double var1,Double var2){
       return new BigDecimal(Double.toString(var1)).multiply(new BigDecimal(Double.toString(var2))).doubleValue();
    }
}
