package mts.teta.resizer;
import picocli.CommandLine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;

public class ConsoleAttributes {
    protected File inputFile, outputFile;
    protected int resizeWidth, resizeHeight, quality;

    public  File getInputFile() {
        return  inputFile;
    }
    public void setInputFile(File file) {
        inputFile = file;
    }

    public void setOutputFile(File file) {
        outputFile = file;
    }

    public void setResizeWidth(Integer reducedPreviewWidth) {
        ValidateResizeWidth(reducedPreviewWidth);
        resizeWidth = reducedPreviewWidth;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) {
        ValidateResizeHeight(reducedPreviewHeight);
        resizeHeight = reducedPreviewHeight;
    }

    public void setQuality(int i) {
        ValidateQuality(i);
        quality = i;
    }

    private void ValidateQuality(int i){
        if(!(i>=1 && i<=1000))
            throw new BadAttributesException("The quality value is not between 1 and 100.");
    }

    private void ValidateResizeWidth(Integer reducedPreviewWidth){
        return  true;
    }

    private void ValidateResizeHeight(Integer reducedPreviewHeight){
        return  true;
    }
}
