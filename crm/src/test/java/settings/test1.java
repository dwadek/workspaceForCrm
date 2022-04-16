package settings;

import com.dwadek.crm.utils.DateTimeUtil;
import com.dwadek.crm.utils.MD5Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class test1 {

    public static void main(String[] args) {
        /*String expireTime = "2019-10-10 10:10:10";

        String currentTime = DateTimeUtil.getSysTime();
        int count = expireTime.compareTo(currentTime);
        System.out.println(count);*/

        /*String lockState = "0";
        if("0".equals(lockState)){
            System.out.println("账号已锁定");
        }*/

        //浏览器端的ip地址
        /*String ip = "192.168.1.1";
        //允许访问的ip地址群
        String allowIps = "192.168.1.1,192.168.1.2";
        if(allowIps.contains(ip)){
            System.out.println("有效的ip地址，允许访问系统");
        }else {
            System.out.println("ip地址受限，请联系管理员");
        }*/

        String pwd = "123";
        pwd = MD5Util.getMD5(pwd);
        System.out.println(pwd);
    }
}
