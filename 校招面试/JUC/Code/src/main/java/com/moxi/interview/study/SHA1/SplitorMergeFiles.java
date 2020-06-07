package com.moxi.interview.study.SHA1;

import java.io.*;
import java.security.MessageDigest;

public class SplitorMergeFiles {

    public static void main(String[] args) throws IOException {

        String file = "D:\\file\\split\\test.txt";

        // 创建大文件
        createBigFile(file);
        System.out.println("创建大文件成功");

        // 切割文件
        int count = 10;
        getSplitFile(file, count);
        System.out.println("切割文件成功");

        // 合并文件
        // 合并后的文件
        String resultFile = "D:\\file\\split\\result.txt";
        // 合并前的文件
        String tempFile = "D:\\file\\split\\test.txt";
        merge(resultFile, tempFile, 10);
        System.out.println("合并文件成功");

        // 判断两个文件中的内容
        String oldText = getFileContent("D:\\file\\split\\result.txt");
        String newText = getFileContent("D:\\file\\split\\test.txt");
        System.out.println("获取文件内容");

        // 文件进行sha1加密
        String shaOldText = getSha1(oldText);
        String shaNewText = getSha1(newText);
        System.out.println("对原始文件以及合并的文件进行sha1加密");

        System.out.println("两个文件是否相同：" + shaOldText.equals(shaNewText));
    }

    /**
     * 创建大文件
     * @throws IOException
     */
    public static void createBigFile(String path) throws IOException {
        File file = new File(path);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        String str = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa1";
        for (int i = 0; i < 100000; i++) {
            bufferedWriter.write(str);
            bufferedWriter.newLine();
        }
        bufferedWriter.flush();
        bufferedWriter.close();
    }

    /**
     * 切割文件
     * @param file
     * @param count
     */
    public static void getSplitFile(String file,int count){

        //预分配文件所占用的磁盘空间，在磁盘创建一个指定大小的文件，“r”表示只读，“rw”支持随机读写
        try {
            RandomAccessFile raf = new RandomAccessFile(new File(file), "r");
            //计算文件大小
            long length = raf.length();
            System.out.println(length);
            //计算文件切片后每一份文件的大小
            long maxSize = length / count;

            System.out.println(maxSize);

            long offset = 0L;//定义初始文件的偏移量(读取进度)
            //开始切割文件
            for(int i = 0; i < count - 1; i++){ //count-1最后一份文件不处理
                //标记初始化
                long fbegin = offset;
                //分割第几份文件
                long fend = (i+1) * maxSize;
                //写入文件
                offset = getWrite(file, i, fbegin, fend);

            }

            //剩余部分文件写入到最后一份(如果不能平平均分配的时候)
            if (length - offset > 0) {
                //写入文件
                getWrite(file, count-1, offset, length);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 指定文件每一份的边界，写入不同文件中
     * @param index 源文件的顺序标识
     * @param end 结束指针的位置
     * @return long
     */
    public static long getWrite(String filePath,int index,long begin,long end){

        long endPointer = 0L;
        try {
            //申明文件切割后的文件磁盘
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            } else {
                file.createNewFile();
            }

            RandomAccessFile in = new RandomAccessFile(new File(filePath), "r");
            //定义一个可读，可写的文件并且后缀名为.tmp的二进制文件
            RandomAccessFile out = new RandomAccessFile(new File(filePath + "_" + index + ".tmp"), "rw");

            //申明具体每一文件的字节数组
            byte[] b = new byte[1024];
            int n = 0;
            //从指定位置读取文件字节流
            in.seek(begin);
            //判断文件流读取的边界
            while(in.getFilePointer() <= end && (n = in.read(b)) != -1){
                //从指定每一份文件的范围，写入不同的文件
                out.write(b, 0, n);
            }

            //定义当前读取文件的指针
            endPointer = in.getFilePointer();

            //关闭输入流
            in.close();
            //关闭输出流
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return endPointer;
    }

    /**
     * 文件合并
     * @param file 指定合并文件
     * @param tempFile 分割前的文件名
     * @param tempCount 文件个数
     */
    public static void merge(String file,String tempFile,int tempCount) {
        RandomAccessFile raf = null;
        try {
            //申明随机读取文件RandomAccessFile
            raf = new RandomAccessFile(new File(file), "rw");
            //开始合并文件，对应切片的二进制文件
            for (int i = 0; i < tempCount; i++) {
                //读取切片文件
                RandomAccessFile reader = new RandomAccessFile(new File(tempFile + "_" + i + ".tmp"), "r");
                byte[] b = new byte[1024];
                int n = 0;
                while ((n = reader.read(b)) != -1) {
                    raf.write(b, 0, n);//一边读，一边写
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取文件中的全部内容
     * @return
     */
    public static String getFileContent(String path) {
        File file = new File(path);
        Long fileLengthLong = file.length();
        byte[] fileContent = new byte[fileLengthLong.intValue()];
        try {
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(fileContent);
            inputStream.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        String string = new String(fileContent);
        return string;
    }

    public static String getSha1(String str) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}