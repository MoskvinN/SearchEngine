import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    private static int newWidth = 300;
    public static void main(String[] args) {
        String srcFolder = "data\\src";
        String dstFolder = "data\\dst";

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        int newFiles = files.length / 12;

        File[] files1 = new File[newFiles];
        System.arraycopy(files, 0, files1, 0, files1.length);
        ImageResizer resizer1 = new ImageResizer(files1, newWidth, dstFolder, start);
        new Thread(resizer1).start();

        File[] files2 = new File[newFiles];
        System.arraycopy(files, newFiles, files2, 0, files2.length);
        ImageResizer resizer2 = new ImageResizer(files2, newWidth, dstFolder, start);
        new Thread(resizer2).start();

        File[] files3 = new File[newFiles];
        System.arraycopy(files, newFiles * 2, files3, 0, files3.length);
        ImageResizer resizer3 = new ImageResizer(files3, newWidth, dstFolder, start);
        new Thread(resizer3).start();

        File[] files4 = new File[newFiles];
        System.arraycopy(files, newFiles * 3, files4, 0, files4.length);
        ImageResizer resizer4 = new ImageResizer(files4, newWidth, dstFolder, start);
        new Thread(resizer4).start();

        File[] files5 = new File[newFiles];
        System.arraycopy(files, newFiles * 4, files5, 0, files5.length);
        ImageResizer resizer5 = new ImageResizer(files5, newWidth, dstFolder, start);
        new Thread(resizer5).start();

        File[] files6 = new File[newFiles];
        System.arraycopy(files, newFiles * 5, files6, 0, files6.length);
        ImageResizer resizer6 = new ImageResizer(files6, newWidth, dstFolder, start);
        new Thread(resizer6).start();

        File[] files7 = new File[newFiles];
        System.arraycopy(files, newFiles * 6, files7, 0, files7.length);
        ImageResizer resizer7 = new ImageResizer(files7, newWidth, dstFolder, start);
        new Thread(resizer7).start();

        File[] files8 = new File[newFiles];
        System.arraycopy(files, newFiles * 7, files8, 0, files8.length);
        ImageResizer resizer8 = new ImageResizer(files8, newWidth, dstFolder, start);
        new Thread(resizer8).start();

        File[] files9 = new File[newFiles];
        System.arraycopy(files, newFiles * 8, files9, 0, files9.length);
        ImageResizer resizer9 = new ImageResizer(files9, newWidth, dstFolder, start);
        new Thread(resizer9).start();

        File[] files10 = new File[newFiles];
        System.arraycopy(files, newFiles * 9, files10, 0, files10.length);
        ImageResizer resizer10 = new ImageResizer(files10, newWidth, dstFolder, start);
        new Thread(resizer10).start();

        File[] files11 = new File[newFiles];
        System.arraycopy(files, newFiles * 10, files11, 0, files11.length);
        ImageResizer resizer11 = new ImageResizer(files11, newWidth, dstFolder, start);
        new Thread(resizer11).start();

        File[] files12 = new File[files.length - 11 * newFiles];
        System.arraycopy(files, newFiles * 11, files12, 0, files12.length);
        ImageResizer resizer12 = new ImageResizer(files12, newWidth, dstFolder, start);
        new Thread(resizer12).start();
    }
}
