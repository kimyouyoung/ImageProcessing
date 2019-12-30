package project.imageProcessing;


import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class ImageLabel {
    
    private static final int TYPE_BYTE_GRAY_SCALE = 1;
    
    private static final int TYPE_COLOR_SPACE_CS = 11;
    
    private static final int TYPE_GRAY_FILTER = 21;
    
    private static final int TYPE_RGB_AVR = 31;
    private static final int TYPE_LUMINOUS = 32;
    
    
    private static JFrame jf = new JFrame("GrayScaleTest");
    
    
    public static void main(String[] args) throws IOException {
        String filePath = "/Users/youyoungkim/Documents/jamjari.jpg";
        boolean isXaxis = true;
        doExecute(filePath, isXaxis);
    }


    private static void doExecute(String filePath, boolean isXaxis) throws IOException {
        
        int showHType = SwingConstants.RIGHT;
        int showVType = SwingConstants.CENTER;
        int axis = BoxLayout.Y_AXIS;
        if ( isXaxis ) {
            showHType = SwingConstants.CENTER;
            showVType = SwingConstants.BOTTOM;
            axis = BoxLayout.X_AXIS;
        }
        
        Container contentPane = jf.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, axis));
        
        
        BufferedImage ori = ImageIO.read(new File(filePath));
        BufferedImage byteGray = toGraySacle(ori, TYPE_BYTE_GRAY_SCALE);
        BufferedImage rgbAvgGray = toGraySacle(ori, TYPE_RGB_AVR);
        BufferedImage luminousGray = toGraySacle(ori, TYPE_LUMINOUS);
        BufferedImage colorSpaceCSGray = toGraySacle(ori, TYPE_COLOR_SPACE_CS);
        BufferedImage grayFilter = toGraySacle(ori, TYPE_GRAY_FILTER);
        
        
        ImageIcon oriIcon = new ImageIcon(ori);
        ImageIcon byteGrayIcon = new ImageIcon(byteGray);
        ImageIcon rgbAvgGrayIcon = new ImageIcon(rgbAvgGray);
        ImageIcon luminousGrayIcon = new ImageIcon(luminousGray);
        ImageIcon colorSpaceCSGrayIcon = new ImageIcon(colorSpaceCSGray);
        ImageIcon grayFilterIcon = new ImageIcon(grayFilter);
        
        
        JLabel oriLabel = new JLabel("oriImage");
        oriLabel.setHorizontalTextPosition(showHType);
        oriLabel.setVerticalTextPosition(showVType);
        oriLabel.setIcon(oriIcon);
        
        JLabel byteGrayLabel = new JLabel("byteGray");
        byteGrayLabel.setHorizontalTextPosition(showHType);
        byteGrayLabel.setVerticalTextPosition(showVType);
        byteGrayLabel.setIcon(byteGrayIcon);
                
        JLabel rgbAvgGrayLabel = new JLabel("rgbAvgGray");
        rgbAvgGrayLabel.setHorizontalTextPosition(showHType);
        rgbAvgGrayLabel.setVerticalTextPosition(showVType);
        rgbAvgGrayLabel.setIcon(rgbAvgGrayIcon);
        
        JLabel luminousGrayLabel = new JLabel("luminousGray");
        luminousGrayLabel.setHorizontalTextPosition(showHType);
        luminousGrayLabel.setVerticalTextPosition(showVType);
        luminousGrayLabel.setIcon(luminousGrayIcon);
        
        JLabel colorSpaceCSGrayLabel = new JLabel("colorSpaceCSGray");
        colorSpaceCSGrayLabel.setHorizontalTextPosition(showHType);
        colorSpaceCSGrayLabel.setVerticalTextPosition(showVType);
        colorSpaceCSGrayLabel.setIcon(colorSpaceCSGrayIcon);
        
        JLabel grayFilterLabel = new JLabel("grayFilter");
        grayFilterLabel.setHorizontalTextPosition(showHType);
        grayFilterLabel.setVerticalTextPosition(showVType);
        grayFilterLabel.setIcon(grayFilterIcon);
        
        
        
        contentPane.add(oriLabel);
        contentPane.add(byteGrayLabel);
        contentPane.add(rgbAvgGrayLabel);
        contentPane.add(luminousGrayLabel);
        contentPane.add(colorSpaceCSGrayLabel);
        contentPane.add(grayFilterLabel);
        
        
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }
    
    
    private static BufferedImage toGraySacle(BufferedImage oriImage, int type) {
        
        int width = oriImage.getWidth();
        int height = oriImage.getHeight();
        
        BufferedImage toImage = null;
        
        if ( type == TYPE_BYTE_GRAY_SCALE ) {
            toImage = new  BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
            Graphics2D toGraphics2D = (Graphics2D)toImage.getGraphics();
            toGraphics2D.drawImage(oriImage, 0, 0, null);
        } else if ( type == TYPE_RGB_AVR ) {
            toImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            for ( int i = 0 ; i < width; i++ ) {
                for ( int j = 0 ; j < height ; j++ ) {
                    int eachRGB = oriImage.getRGB(i, j);
                    int alpha = ( eachRGB & 0xFF000000 ); 
                    int red = (eachRGB & 0x00FF0000) >> 16;
                    int green = (eachRGB & 0x0000FF00) >>  8;
                    int blue = (eachRGB & 0x000000FF);
                    int newRGB = (int)(( red + green + blue ) / 3);
                    int eachNewRGB = alpha | (newRGB << 16) | (newRGB << 8 ) | newRGB;
                    toImage.setRGB(i, j, eachNewRGB);
                }
            }
        } else if ( type == TYPE_LUMINOUS ) {
            toImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            
            for ( int i = 0 ; i < width; i++ ) {
                for ( int j = 0 ; j < height ; j++ ) {
                    int eachRGB = oriImage.getRGB(i, j);
                    int alpha = ( eachRGB & 0xFF000000 ); 
                    int red = (eachRGB & 0x00FF0000) >> 16;
                int green = (eachRGB & 0x0000FF00) >>  8;
                    int blue = (eachRGB & 0x000000FF);
                    double red2 = red * 0.2125;
                    double green2 = green * 0.7154;
                    double blue2 = blue * 0.0721;
                    int newRGB = (int)( red2 + green2 + blue2 );
                    int eachNewRGB = alpha | (newRGB << 16) | (newRGB << 8 ) | newRGB;
                    toImage.setRGB(i, j, eachNewRGB);
                }
            }
        } else if ( type == TYPE_COLOR_SPACE_CS ) {
            ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
            ColorConvertOp op = new ColorConvertOp(cs, null);
            toImage = op.filter(oriImage, null);
        } else if ( type == TYPE_GRAY_FILTER ) {
            ImageFilter filter = new GrayFilter(true, 50);
            ImageProducer producer = new FilteredImageSource(oriImage.getSource(), filter);
            Image image = jf.createImage(producer);
            toImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D gr2D = (Graphics2D)toImage.getGraphics();
            gr2D.drawImage(image, 0, 0, null);
            
            
        }
        
        return toImage;
        
    }

}
