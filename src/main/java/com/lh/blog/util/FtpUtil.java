package com.lh.blog.util;

import org.apache.commons.net.ftp.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Calendar;
import java.util.Date;

import java.net.SocketException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.io.CopyStreamListener;

public class FtpUtil {

    private final static Logger logger = Logger.getLogger(FtpUtil.class);

    private final FTPClient ftpClient;

    private final int PORT = 21;
    private final String IP = "149.28.238.229";
    private final String NAME = "ftpuser";
    private final String PASS = "lh971121";

    /**
     * 获取FTPClient对象
     *
     * @param ftpHost     FTP主机服务器
     * @param ftpPassword Ftp 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort     FTP端口 默认为21
     *                    //     * @param ftpServerDic FTP服务器保存目录/
     *                    //     * @param listener
     * @return
     */
    private FTPClient getFTPConnection(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword) throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect(ftpHost, ftpPort);// 连接FTP服务器
        ftp.login(ftpUserName, ftpPassword);// 登陆FTP服务器
        ftp.setControlEncoding("UTF-8");
        ftp.setCharset(Charset.forName("UTF-8"));
        ftp.setKeepAlive(true);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        //设置被动模式传输
        ftp.enterLocalPassiveMode();
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            logger.info("未连接到FTP，用户名或密码错误。");
            ftp.disconnect();
        } else {
            logger.info("FTP连接成功。");
        }
//        try {
//
//        } catch (SocketException e) {
//            logger.error("FTP的IP地址可能错误，请正确配置。\n", e);
//        } catch (IOException e) {
//            logger.error("FTP的端口错误,请正确配置。\n", e);
//        }
        return ftp;
    }

    /**
     * 构造方法
     *
     * @param ftpHost     ftp地址
     * @param ftpPort     ftp开放的端口
     * @param ftpUserName ftp登录用户名
     * @param ftpPassword ftp登录密码
     * @param listener
     * @throws java.io.IOException
     */
    public FtpUtil(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, CopyStreamListener listener) throws IOException {
        ftpClient = this.getFTPConnection(ftpHost, ftpPort, ftpUserName, ftpPassword);
        ftpClient.setCopyStreamListener(listener);
    }

    public FtpUtil() throws IOException {
        ftpClient = this.getFTPConnection(IP, PORT, NAME, PASS);
//        ftpClient.setCopyStreamListener(listener);
    }

    private String changeFileNameCharset(String fileName) {
        return new String(fileName.getBytes(), Charset.forName("UTF-8"));
    }

    /**
     * 改变FTP工作目录
     *
     * @param toRemotePath FTP当前的工作目录
     * @return true成功选择工作目录
     * @throws IOException
     */
    public boolean changeWorkingDirectory(String toRemotePath) throws IOException {
        if (ftpClient == null) {
            return false;
        }
        if (!ftpClient.isConnected()) {
            return false;
        }
        return ftpClient.changeWorkingDirectory(toRemotePath);
    }

    /**
     * 上传文件，需要制定上传的目录，调用changeWorkingDirectory方法
     *
     * @param fileName 文件名
     * @param is       文件数据流
     * @return true上传成功
     * @throws IOException IO错误
     */
    public boolean uploadFile(String fileName, InputStream is) throws IOException {
        if (ftpClient == null) {
            return false;
        }
        if (!ftpClient.isConnected()) {
            return false;
        }
        boolean bool = ftpClient.storeFile(changeFileNameCharset(fileName), is);
        is.close();
        return bool;
    }

    /**
     * 上传单个文件
     *
     * @param fileName     上传的文件名
     * @param is           上传的文件数据流
     * @param toRemotePath ftp远程目录
     * @return true正确的上传完成
     * @throws IOException IO流错误
     */
    public boolean uploadFile(String fileName, InputStream is, String toRemotePath) throws IOException {
        if (ftpClient == null) {
            logger.info("还没有连接");
            return false;
        }
        if (!ftpClient.isConnected()) {
            logger.info("FTP连接失败");
            return false;
        }
        logger.info("FTP设置路径:" + toRemotePath);
        logger.info("FTP文件名:" + changeFileNameCharset(fileName));
        logger.info("FTP设置流:" + is.available());
        if (!ftpClient.changeWorkingDirectory(toRemotePath)) {
            logger.info("创建目录");

            createDirectorys(toRemotePath);
        }
        logger.info("FTP改变目录："+ftpClient.changeWorkingDirectory(toRemotePath));

        boolean bool = ftpClient.storeFile(changeFileNameCharset(fileName), is);
        logger.info("FTP上传：" + bool);
        is.close();
        return bool;
    }

    /**
     * 创建文件或目录
     *
     * @param dirPath 上传的文件或目录
     * @return
     * @throws IOException IO流错误
     */
    public void createDirectorys(String dirPath) throws IOException {
        if (!dirPath.endsWith("/")) {
            dirPath += "/";
        }
        String directory = dirPath.substring(0, dirPath.lastIndexOf("/") + 1);
        ftpClient.makeDirectory("/");
        int start = 0;
        int end = 0;
        if (directory.startsWith("/")) {
            start = 1;
        } else {
            start = 0;
        }
        end = directory.indexOf("/", start);
        String subDirectory = "";
        while (true) {
            subDirectory += "/" + new String(dirPath.substring(start, end));
//            logger.info(subDirectory);
            if (!ftpClient.changeWorkingDirectory(subDirectory)) {
                if (ftpClient.makeDirectory(subDirectory)) {
                    ftpClient.changeWorkingDirectory(subDirectory);
                } else {
                    logger.info("创建目录失败");
                    return;
                }
            }
            start = end + 1;
            end = directory.indexOf("/", start);
            //检查所有目录是否创建完毕  
            if (end <= start) {
                break;
            }
        }

    }

    /**
     * 上传文件或目录
     *
     * @param file       上传的文件或目录
     * @param remotePath ftp远程目录
     * @throws IOException IO流错误
     */
    private void uploadFile(File file, String remotePath) throws IOException {
        if (file == null) {
            return;
        }
        if (ftpClient == null) {
            return;
        }
        if (!ftpClient.isConnected()) {
            return;
        }
        if (file.isDirectory()) {
            File[] subFiles = file.listFiles();
            if (subFiles == null || subFiles.length == 0) {
                return;
            }
            String path = remotePath + "//" + file.getName();
            //在FTP服务器上创建同名文件夹
            ftpClient.makeDirectory(path);
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    //ftpClient.changeWorkingDirectory("//");
                    //ftpClient.makeDirectory(path + "//" + subFile.getName());
                    ftpClient.changeWorkingDirectory(path);
                    String subFileName = changeFileNameCharset(subFile.getName());
                    if (!ftpClient.changeWorkingDirectory(path + "/" + subFileName)) {
                        ftpClient.makeDirectory(subFileName);
                    }
                    uploadFile(subFile, path);
                } else if (subFile.isFile()) {
                    uploadFile(subFile.getName(), new FileInputStream(subFile), path + "//");
                }
            }
        } else if (file.isFile()) {
            uploadFile(file.getName(), new FileInputStream(file), remotePath);
        }
    }

    /**
     * 上传文件或文件目录
     *
     * @param file       上传的文件或目录
     * @param remotePath ftp远程目录
     * @throws IOException IO流错误
     */
    public void uploadDirectory(File file, String remotePath) throws IOException {
        uploadFile(file, remotePath);
    }

    /**
     * 下载单个文件
     *
     * @param remotePath ftp远程目录
     * @param fileName   下载的文件名
     * @param os         文件输出流
     * @return true正确下载完成
     */
    public boolean downloadFile(String remotePath, String fileName, OutputStream os) {
        boolean bool = false;
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (fileName.equals(ftpFile.getName())) {
                    ftpClient.retrieveFile(ftpFile.getName(), os);
                    os.flush();
                    os.close();
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
            bool = false;
        }
        return bool;
    }

    /**
     * 下载整个目录，包括文件和子目录
     *
     * @param remotePath ftp远程目录
     * @param localPath  本地目录
     * @throws IOException IO流错误
     */
    public void downloadDirectory(String remotePath, String localPath) throws IOException {
        downloadDirectory(remotePath, new File(localPath + "//"));
    }

    /**
     * 下载整个目录，包括文件和子目录
     *
     * @param remotePath ftp远程目录
     * @param localDic   本地目录
     * @throws IOException IO流错误
     */
    public void downloadDirectory(String remotePath, File localDic) throws IOException {
        if (remotePath == null) {
            return;
        }
        if ("".equals(remotePath)) {
            return;
        }
        if (localDic == null) {
            return;
        }
        if (!localDic.exists()) {
            localDic.mkdirs();
        }
        FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
        if (ftpFiles == null) {
            return;
        }
        if (ftpFiles.length == 0) {
            return;
        }
        for (FTPFile ftpFile : ftpFiles) {
            String filePath = localDic.getAbsolutePath() + "//" + ftpFile.getName();
            if (ftpFile.isDirectory()) {
                downloadDirectory(remotePath + "//" + ftpFile.getName(), new File(filePath));
            } else {
                //downloadFile(remotePath, ftpFile.getName(), os);
                try (FileOutputStream os = new FileOutputStream(new File(filePath))) {
                    ftpClient.changeWorkingDirectory(remotePath);
                    ftpClient.retrieveFile(ftpFile.getName(), os);
                    os.flush();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param remotePath ftp远程目录
     * @param fileName   删除的文件名
     * @return true成功删除文件
     */
    public boolean deleteFile(String remotePath, String fileName) {
        boolean bool = false;
        try {
            ftpClient.changeWorkingDirectory(remotePath);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile ftpFile : ftpFiles) {
                if (fileName.equals(ftpFile.getName())) {
                    bool = ftpClient.deleteFile(fileName);
                    break;
                }
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }
        return bool;
    }

    /**
     * 关闭FTP连接
     *
     * @return true关闭成功
     * @throws IOException IO流错误
     */
    public boolean close() throws IOException {
        if (ftpClient == null) {
            return true;
        }
        boolean bool = ftpClient.logout();
        if (ftpClient.isConnected()) {
            ftpClient.disconnect();
        }
        return bool;
    }


}
