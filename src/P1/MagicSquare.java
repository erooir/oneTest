package P1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class MagicSquare {
    public static void main(String[] args) throws IOException {
        boolean flag = false;
        for (int i = 0; i < 5; i++) {
            System.out.println(i + 1 + ".txt用isLegalMagicSquare方法的判断结果如下：");
            flag = isLegalMagicSquare(i + 1 + ".txt");
            System.out.println(flag);
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("生成n*n幻方矩阵，请输入你想要的n（仅支持奇数）：");
        int n = sc.nextInt();
        generateMagicSquare(n);
        System.out.println("利用isLegalMagicSquare检测写入6.txt中的矩阵结果如下：");
        flag = isLegalMagicSquare("6.txt");
        System.out.println(flag);
    }




    public static boolean isLegalMagicSquare(String fileName) throws IOException {
        //String[] line = null;//临时存储每行字符串分割后的字符串数组
        String[][] str_matrix = new String[1000][];//以字符串形式存储矩阵
        int[] num = new int[1000];//记录每行长度
        int[][] num_matrix = new int[1000][1000];//存储矩阵整数
        int count = 0;//行数记录器
        File fl = null;
        FileReader fr = null;
        BufferedReader bfr = null;
        try {
            fl = new File("src\\P1\\txt\\"+fileName);
            fr = new FileReader(fl);
            bfr = new BufferedReader(fr);
            String s = null;
            while((s = bfr.readLine()) != null){
                str_matrix[count] = s.split("\t");
                num[count] = str_matrix[count].length;
                count++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("文件读取失败");
        }finally {
            bfr.close();//关闭字符缓冲输入流
        }
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < num[i]; j++) {
                //用正则表达式直接区分字符串是否合法
                if(!(str_matrix[i][j].matches("\\d+"))){
                    System.out.println("矩阵输入不合法,存在非正整数或未用\t分隔");
                    return false;
                }
                num_matrix[i][j] = Integer.valueOf(str_matrix[i][j]);
            }
        }
        for (int i = 0; i < count; i++) {
            if(count != num[i]){
                System.out.println("矩阵输入不合法，行列数不相等或每行数字个数不等");
                return false;
            }
        }
        int sum = 0,add= 0;
        //取任意行或任意列的数字和为基准
        for (int i = 0; i < count; i++) {
            sum += num_matrix[i][0];
        }
        //判断每行数字和是否相等
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < num[i]; j++) {
                add+= num_matrix[i][j];
            }
            if(sum != add){
                return false;
            }
            add = 0;
        }
        //判断每列数字和是否相等
        for (int i = 1; i < num[0]; i++) {
            add = 0;
            for (int j = 0; j < count; j++) {
                add += num_matrix[i][j];
            }
            if(sum != add){
                return false;
            }
        }
        add = 0;
        //判断两对角线数字和是否相等
        for (int i = 0; i <count ; i++) {
            add += num_matrix[i][i];
        }
        if(sum != add){
            return false;
        }
        add = 0;
        for (int i = 0; i < count; i++) {
            add += num_matrix[i][count-1-i];
        }
        if(sum != add){
            return false;
        }
        return true;
    }




    public static boolean generateMagicSquare(int n)  {
        File f ;
        FileOutputStream fos = null;
        OutputStreamWriter writer = null;
        try {
            int[][] magic = new int[n][n];
            int row = 0, col = n / 2, i, j, square = n * n;
            //循环赋值n*n次后停止
            for (i = 1; i <= square; i++) {
                magic[row][col] = i;//赋值
                if (i % n == 0)//判断当前数字是否为n的倍数
                    row++;//如果是，为正下方一位的数字赋值
                else {
                    if (row == 0)//如果当前赋值在上边界
                        row = n - 1;//跳跃到下边界
                    else
                        row--;//否则行数减一
                    if (col == (n - 1))//如果当前赋值在右边界
                        col = 0;//跳跃到左边界
                    else
                        col++;//否则列数加一
                }
            }
            //打印矩阵
            for (i = 0; i < n; i++) {
                for (j = 0; j < n; j++)
                    System.out.print(magic[i][j] + "\t");
                System.out.println();
            }
            //将生成的矩阵写入6.txt
            f = new File("src\\P1\\txt\\6.txt");
            fos = new FileOutputStream(f);
            writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            for (int k = 0; k < n; k++) {
                for (int l = 0; l < n-1; l++) {
                    writer.append(magic[k][l] + "\t");
                }
                writer.append(magic[k][n-1] + "\n");
            }
            //优雅的退出
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("输入数据为偶数，数组越界访问");
            return false;
        } catch (NegativeArraySizeException e){
            System.out.println("输入数据为负数，不存在负长度的数组");
            return false;
        } catch (FileNotFoundException e){
            System.out.println("文件打开失败");
            return false;
        } catch (IOException e) {
            System.out.println("写文件异常");
            return false;
        } finally {
            try {
                writer.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("流关闭失败");
                return false;
            }
        }
        return true;
    }
}
