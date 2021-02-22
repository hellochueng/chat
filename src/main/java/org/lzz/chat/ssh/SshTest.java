package org.lzz.chat.ssh;


public class SshTest
{
    public static void main(String[] args)
    {
        RemoteExecuteCommand rec = new RemoteExecuteCommand();

        String result = rec.execute("sh /home/lzz/zksta.sh");
        return;
    }
}