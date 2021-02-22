package org.lzz.chat.ssh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class RemoteExecuteCommand
{
    // 字符编码默认是utf-8
    private static String    DEFAULTCHART    = "UTF-8";
    private Connection        conn;
    private String            ip;
    private String            userName;
    private String            userPwd;

    public RemoteExecuteCommand()
    {
        this.ip = "localhost";
        this.userName = "lzz";
        this.userPwd = "123";
    }

    public Boolean login()
    {
        boolean flg = false;
        try
        {
            conn = new Connection(ip);
            conn.connect();
            flg = conn.authenticateWithPassword(userName, userPwd);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return flg;
    }

    public String execute(String cmd)
    {
        String result = "";
        try
        {
            if (login())
            {
                System.out.println("start :");
                Session session = conn.openSession();

                session.requestPTY("bash");
                session.startShell();
                PrintWriter out = new PrintWriter(session.getStdin());
                out.println(cmd);
                out.flush();
                out.println("exit");
                out.close();
                session.waitForCondition(ChannelCondition.CLOSED | ChannelCondition.EOF | ChannelCondition.EXIT_STATUS,
                        60000);
                System.out.println("exec has finished!");
                session.close();
                conn.close();
            }
        } catch (

        IOException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
