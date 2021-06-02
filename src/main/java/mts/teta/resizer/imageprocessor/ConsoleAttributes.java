package mts.teta.resizer.imageprocessor;

import picocli.CommandLine.*;

import java.io.File;

enum ImageFormat{ JPEG,PNG}

public class ConsoleAttributes {

    protected File inputFile, outputFile;
    protected int quality = -1;
    public int blurRadius =-1;
    public boolean resizeFlag, qualityFlag, cropFlag, blurFlag;

    @Parameters(index = "0", paramLabel = "input-file")
    public void setInputFile(File file) throws BadAttributesException {
        //TODO validation
        validateFile(file);
        inputFile = file;
    }

    @Parameters(paramLabel = "output-file")
    public void setOutputFile(File file) throws BadAttributesException {
        //TODO validation
        validateFile(file);
        outputFile = file;
    }

    @ArgGroup(exclusive = true, multiplicity = "1")
    public Exclusive exclusive;

    static class Exclusive {
        @Option(names = {"--resize"}, required = true, description = "resize the image", paramLabel = "<width height>")
        public ResizeArgs resizeArgs;

        @Option(names = {"--crop"}, required = true,  description = "cut out one rectangular area of the image", paramLabel = "<width height x y>")
        public CropArgs cropArgs;
    }

    static class ResizeArgs {
        @Parameters(index = "0") int width;
        @Parameters(index = "1") int height;
    }

    static class CropArgs extends ResizeArgs {
        @Parameters(index = "2") int x;
        @Parameters(index = "3") int y;
    }

    @Option(names = {"--quality"}, description = "JPEG/PNG compression level", paramLabel = "<value>")
    public void setQuality(int i) throws BadAttributesException {
        if(!(i>=1 && i<=100))
            throw new BadAttributesException("The quality value is not between 1 and 100.");
        quality = i;
        qualityFlag = true;
    }

    @Option(names = {"--blur"}, description = "reduce image noise detail levels ")
    private void setBlurRadius(int radius) throws BadAttributesException {
        if(radius < 1)
            throw new BadAttributesException("The blur radius value is less than 1");
        blurRadius = radius;
        blurFlag = true;
    }

    @Option(names = {"--format"}, description = "the image format type: ${COMPLETION-CANDIDATES}")
    public ImageFormat outputFormat;

    public void setResizeWidth(Integer reducedPreviewWidth) throws BadAttributesException {
        //TODO validation
        exclusive.resizeArgs.width = reducedPreviewWidth;
        resizeFlag = true;
    }

    public void setResizeHeight(Integer reducedPreviewHeight) throws BadAttributesException {
        //TODO validation
        exclusive.resizeArgs.height = reducedPreviewHeight;
        resizeFlag = true;
    }

    private void validateFile(File file) throws BadAttributesException {
        //TODO validation
    }

    public  File getOutputFile(){
        return  outputFile;
    }


}
