/**
 * 文件操作类
 */
package com.ai.gis.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FileUtil {
    public static final Map<String, String> TYPES = new HashMap<>();

    static {
        TYPES.put("FFD8FFE0", "jpg");
        TYPES.put("89504E47", "png");
        TYPES.put("47494638", "gif");
        TYPES.put("49492A00", "tif");
        TYPES.put("424D", "bmp");
        TYPES.put("41433130", "dwg"); // CAD
        TYPES.put("38425053", "psd");
        TYPES.put("7B5C727466", "rtf"); // 日记本
        TYPES.put("3C3F786D6C", "xml");
        TYPES.put("68746D6C3E", "html");
        TYPES.put("44656C69766572792D646174653A", "eml"); // 邮件
        TYPES.put("D0CF11E0", "doc");
        TYPES.put("D0CF11E0", "xls");//excel2003版本文件
        TYPES.put("5374616E64617264204A", "mdb");
        TYPES.put("252150532D41646F6265", "ps");
        TYPES.put("255044462D312E", "pdf");
        TYPES.put("504B0304", "docx");
        TYPES.put("504B0304", "xlsx");//excel2007以上版本文件
        TYPES.put("52617221", "rar");
        TYPES.put("57415645", "wav");
        TYPES.put("41564920", "avi");
        TYPES.put("2E524D46", "rm");
        TYPES.put("000001BA", "mpg");
        TYPES.put("000001B3", "mpg");
        TYPES.put("6D6F6F76", "mov");
        TYPES.put("3026B2758E66CF11", "asf");
        TYPES.put("4D546864", "mid");
        TYPES.put("1F8B08", "gz");
    }

    /**
     * 根据文件的字节数据获取文件类型
     *
     * @param filePath 文件路径
     * @return
     */
    public static String getFileType(String filePath) throws IOException {
        //提取前六位作为魔数
        String magicNumberHex = getHex(getFileBytes(filePath), 8);
        return TYPES.get(magicNumberHex);
    }

    /**
     * 根据文件的字节数据获取文件类型
     *
     * @param data 字节数组形式的文件数据
     * @return
     */
    public static String getFileType(byte[] data) throws IOException {
        //提取前六位作为魔数
        String magicNumberHex = getHex(data, 8);
        return TYPES.get(magicNumberHex);
    }

    /**
     * 上传文件(单)
     *
     * @param file 文件对象
     * @param path 保存路径
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile file, String path) throws IOException {
        String originalFilename = file.getOriginalFilename();// 原文件名称
        String date = new SimpleDateFormat("yyyy/MM").format(new Date()).toString();
        String fileName = date + "/" + UUID.randomUUID().toString().replace("-", "") + originalFilename.substring(originalFilename.lastIndexOf("."));
        File dest = new File(path + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.setWritable(true);
            dest.getParentFile().mkdirs();
        }
        file.transferTo(dest);
        return fileName;
    }

    /**
     * 上传文件(多)
     *
     * @param files file数组
     * @param path  保存路径
     * @return
     */
    public static List<String> upload(MultipartFile[] files, String path) throws IOException {
        List<String> imagePathList = new ArrayList<>();
        //判断file数组不能为空并且长度大于0
        if (files != null && files.length > 0) {
            String date = new SimpleDateFormat("yyyy/MM").format(new Date()).toString();
            // 处理路径并判断目录是否存在
            File dir = new File(path + "/" + date);
            if (!dir.exists()) {
                // 设置写权限
                dir.setWritable(true);
                dir.mkdirs();
            }
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // 原文件名称
                    String fileName = file.getOriginalFilename();
                    String newFileName = date + "/" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
                    file.transferTo(new File(path + "/" + newFileName));
                    imagePathList.add(newFileName);
                }
            }
        }
        return imagePathList;
    }

    /**
     * 将字符串写入文件
     *
     * @param content
     * @param filePath
     */
    public static void writeFile(String content, String filePath) throws IOException {
        FileOutputStream fs = new FileOutputStream("C:\\Users\\GISUNI\\Downloads\\HeatLayer.js");
        System.out.println("fs" + fs);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath), "utf-8"));
        bw.write(content);
        bw.close();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return
     */
    public static boolean delete(String filePath) {
        if (filePath == null) {
            return false;
        }
        File file = new File(filePath);
        if (file.isFile()) {
            return file.delete();
        }
        return false;
    }

    /**
     * 验证文件大小
     *
     * @param fileLen  文件大小
     * @param fileSize 规定文件大小
     * @param fileUnit 单位
     * @return
     */
    public static boolean checkFileSize(Long fileLen, int fileSize, String fileUnit) {
        double fileSizeCom = 0;
        if ("B".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if ("K".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if ("M".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024);
        } else if ("G".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024 * 1024 * 1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;
    }

    /**
     * 读取文件字节数据
     *
     * @param filePath
     * @return
     * @throws IOException
     */
    public static byte[] getFileBytes(String filePath) throws IOException {
        InputStream fs = new FileInputStream(filePath);
        byte[] b = new byte[fs.available()];
        fs.read(b);
        return b;
    }

    /**
     * 获取16进制表示的魔数
     *
     * @param data              字节数组形式的文件数据
     * @param magicNumberLength 魔数长度
     * @return
     */
    public static String getHex(byte[] data, int magicNumberLength) {
        //提取文件的魔数
        StringBuilder magicNumber = new StringBuilder();
        //一个字节对应魔数的两位
        int magicNumberByteLength = magicNumberLength / 2;
        for (int i = 0; i < magicNumberByteLength; i++) {
            magicNumber.append(Integer.toHexString(data[i] >> 4 & 0xF));
            magicNumber.append(Integer.toHexString(data[i] & 0xF));
        }
        return magicNumber.toString().toUpperCase();
    }


    public static String FileUploader() {
        return "33";
    }

    /**
     * Spring框架的multipartFile接口转File
     *
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public static File multipartFileToFile(MultipartFile multipartFile, String tempPath) throws IOException {
        File destDir = new File(tempPath);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        File file = new File(tempPath + File.separator + multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fis = new FileOutputStream(file);
        fis.write(multipartFile.getBytes());
        fis.close();
        return file;
    }

    /**
     * 解压读取文件
     *
     * @param zipFile
     * @param destDirPath
     * @throws IOException
     */
    public static void unZipFile(FileInputStream zipFile, String destDirPath) throws IOException {
        try {
            // 创建目标文件夹
            File destDir = new File(destDirPath);
            if (!destDir.exists()) {
                destDir.mkdirs();
            }
            // 创建 ZipInputStream 对象
            try (ZipInputStream zipInputStream = new ZipInputStream(zipFile)) {
                // 读取 ZipEntry
                ZipEntry entry;
                while ((entry = zipInputStream.getNextEntry()) != null) {
                    String entryName = entry.getName();
                    // 构建解压后的文件路径
                    String entryPath = destDirPath + File.separator + entryName;
                    // 如果是文件夹，创建文件夹
                    if (entry.isDirectory()) {
                        File dir = new File(entryPath);
                        dir.mkdirs();
                    } else {
                        // 如果是文件，写入文件内容
                        try (OutputStream outputStream = new FileOutputStream(entryPath)) {
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = zipInputStream.read(buffer)) > 0) {
                                outputStream.write(buffer, 0, length);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件
     *
     * @param path
     * @return
     */
    public static File getFile(String path) {
        File file = new File(path);
        if (file == null) {
            System.out.println("找不到路径：" + path);
        }
        return file;
    }

    /**
     * 获取文件名（没有文件后缀）
     *
     * @param fileFullName
     * @return
     */
    public static String getFileName(String fileFullName) {
        if (fileFullName != null) {
            int lastIndex = fileFullName.lastIndexOf('.');
            if (lastIndex != -1) {
                return fileFullName.substring(0, lastIndex);
            } else {
                // 文件名没有后缀
                return fileFullName;
            }
        }
        return null;
    }

    /**
     * 删除文件夹下文件
     *
     * @param path
     */
    public static void deleteFilesInFolder(String path) {
        File folder = new File(path);
        // 检查文件夹是否存在
        if (!folder.exists()) {
            System.out.println("文件夹不存在");
            return;
        }

        // 检查文件夹是否是目录
        if (!folder.isDirectory()) {
            System.out.println("指定的路径不是一个文件夹");
            return;
        }

        // 获取文件夹中的所有文件和子文件夹
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                // 如果是文件，则删除
                if (file.isDirectory()) {
                    deleteFilesInFolder(file.getAbsolutePath());
                }
                // 如果是文件夹，则递归删除
                else if (file.isDirectory()) {
                    file.delete();
                }
            }
        }
    }

    /**
     * 文件夹寻找匹配后缀的文件
     *
     * @param folder
     * @param matching
     * @return
     */
    public static String findMatchFile(File folder, String matching) {
        for (File file : folder.listFiles()) {
            if (file.isFile() && file.getName().toLowerCase().endsWith(matching)) {
                return file.getName();
            }
        }
        return null;
    }

    public static byte[] getByteInputStream(InputStream ins) {
        // 使用一个循环来读取和打印每个字节
        byte[] bytes = new byte[0];
        try {
            // 使用 ByteArrayOutputStream 缓冲读取的字节
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int bytesRead;
            byte[] data = new byte[1024]; // 缓冲区大小可以根据需要调整

            // 从输入流中读取数据到缓冲区
            while ((bytesRead = ins.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, bytesRead);
            }

            buffer.flush(); // 刷新缓冲区（可选）

            // 获取从输入流读取的所有字节
            bytes = buffer.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ins.close(); // 关闭输入流
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public static String imgToBase64(MultipartFile file) throws IOException {
        try {
            byte[] imgBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imgBytes);
            return base64Image;
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
    }
}