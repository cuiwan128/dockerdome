package com.demo.jsch;

 import java.io.BufferedReader;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.nio.charset.Charset;

 import com.jcraft.jsch.Channel;
 import com.jcraft.jsch.ChannelExec;
 import com.jcraft.jsch.ChannelSftp;
 import com.jcraft.jsch.JSch;
 import com.jcraft.jsch.JSchException;
 import com.jcraft.jsch.Session;
 import com.jcraft.jsch.SftpException;

 import lombok.extern.slf4j.Slf4j;

/**
 * @author drz
 */
@Slf4j
public class JSchUtil {

    private static final String CHARSET = "UTF-8";

    private static final Integer SESSION_TIMEOUT = 30000;

    /**
     * 获取指定linux服务器连接
     */
    public static Session getSession(String userName, String password, String host, int port)
            throws JSchException {
        JSch js = new JSch();
        Session session = js.getSession(userName, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");
        session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");//可以跳过kerberos认证
        session.setTimeout(SESSION_TIMEOUT);
        session.connect();
        return session;
    }

    /**
     * 关闭所有连接
     */
    public static void close(Channel channel, Session session) {
        closeChannel(channel);
        closeSession(session);
    }

    /**
     *  关闭session实例连接接
     */
    public static void closeSession(Session session) {
        if (session != null && session.isConnected()) {
            session.disconnect();
            session = null;
        }
    }

    /**
     *  关闭channel连接
     */
    public static void closeChannel(Channel channel) {
        if (channel != null) {
            channel.disconnect();
            channel = null;
        }
    }

    /**
     * 执行ssh命令接口
     * @param userName 服务器登录账号
     * @param password 服务器登录密码
     * @param host 服务器ip
     * @param port 服务器端口
     * @param command 需要执行的命令，多条命令使用分号";"分隔，eg: cd /tmp; ls -l
     */
    public static void executeCmd(String userName, String password, String host, int port,
                                  String command) {
        log.info("ssh连接请求报文:userName={},host={},port={},command={} ", userName, host, port, command);

        Session session = null;
        ChannelExec channel = null;
        InputStream in = null;
        InputStream errorIn = null;
        BufferedReader reader = null;
        BufferedReader errorReader = null;
        try {
            session = getSession(userName, password, host, port);
            channel = (ChannelExec) session.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            channel.setErrStream(System.err);
            channel.connect();
            log.info("成功连接到服务器" + host + ":" + port);
            in = channel.getInputStream();
            errorIn = channel.getErrStream();

            reader = new BufferedReader(new InputStreamReader(in, Charset.forName(CHARSET)));
            errorReader = new BufferedReader(
                    new InputStreamReader(errorIn, Charset.forName(CHARSET)));

            String buf = null;
            StringBuffer buffer = new StringBuffer();
            while ((buf = reader.readLine()) != null) {
                buffer.append(buf);
                buffer.append("\n");
            }
            while ((buf = errorReader.readLine()) != null) {
                buffer.append(buf);
                buffer.append("\n");
            }

            log.info("执行结果：ReturnResult:" + "\n" + buffer.toString());
        } catch (JSchException e) {
            log.error("获取连接失败：", e);
        } catch (IOException e) {
            log.error("io异常：", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    in = null;
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    reader = null;
                }
            }
            close(channel, session);
        }
    }

    /**
     * 下载文件
     * @param userName 服务器登录账号
     * @param password 服务器登录密码
     * @param host 服务器ip
     * @param port 服务器端口
     * @param src linux服务器文件地址 eg: /home/tmp/a.txt
     * @param dst 下载到本地的文件路径（目录或文件地址），重名会覆盖  eg: D:/test/a1.txt
     */
    public static void download(String userName, String password, String host, int port, String src,
                                String dst) {
        log.info("sftp连接信息:userName={},host={},port={},src={},dst={}", userName, host, port, src,
                dst);
        ChannelSftp channelSftp = null;
        Session session = null;
        try {
            session = getSession(userName, password, host, port);
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.info("成功链接到SFTP服务器 " + host + ":" + port);
            channelSftp.get(src, dst);
            log.info("下载结束");
        } catch (JSchException e) {
            log.error("获取连接失败：", e);
        } catch (SftpException e) {
            log.error("Sftp通道下载文件失败：", e);
        } catch (Exception e) {
            log.error("未知异常：", e);
        } finally {
            close(channelSftp, session);
        }
    }

    /**
     * 上传文件
     * @param userName 服务器登录账号
     * @param password 服务器登录密码
     * @param host 服务器ip
     * @param port 服务器端口
     * @param src 本地文件地址 eg: D:/test/a1.txt
     * @param dst 目标路径(目录或者新文件地址)，文件名可修改，重名会覆盖  eg: /home/tmp/c1.html
     */
    public static void upLoad(String userName, String password, String host, int port, String src,
                              String dst) {
        log.info("sftp连接信息:userName={},host={},port={},src={},dst={}", userName, host, port, src,
                dst);
        ChannelSftp channelSftp = null;
        Session session = null;
        try {
            session = getSession(userName, password, host, port);
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            log.info("成功链接到SFTP服务器 " + host + ":" + port);
            channelSftp.put(src, dst);
            log.info("上传结束");
        } catch (JSchException e) {
            log.error("获取连接失败：", e);
        } catch (SftpException e) {
            log.error("Sftp通道上传文件失败：", e);
        } catch (Exception e) {
            log.error("未知异常：", e);
        } finally {
            close(channelSftp, session);
        }
    }

    public static void main(String[] args) throws JSchException, IOException {
        //执行输入信息
        JSchUtil.executeCmd("root", "", "39.103.159.181", 22, "cd /tmp;ls -l");
    }
}
