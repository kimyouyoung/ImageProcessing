package project.imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class Edge {
	
	public double[][] im2ar(BufferedImage bi, double e_n){
		
		double[][] output = new double[bi.getHeight()][bi.getWidth()];
		for (int y = 0; y < bi.getHeight(); y++) {
			for (int x = 0; x < bi.getWidth(); x++) {
				Color c = new Color(bi.getRGB(x, y));
				output[y][x] += c.getRed();
				output[y][x] += c.getGreen();
				output[y][x] += c.getBlue();
				output[y][x] /= e_n;
			}
		}
		return output;
	}
	
	public double[][] getFilter(int size) {
		int x = size / 2;
		double[][] output = new double[size][size];
		double sum = 0;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
			output[i][j] = 1.0 / (1 + Math.pow(Math.pow(i - x, 2) + Math.pow(j - x, 2), 0.5));
			sum += output[i][j];
			}
		}
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				output[i][j] /= sum;
			}
		}
		return output;
	}
	
	public double[][] convolution(double[][] map, double[][] filter) {
		
		if (filter.length % 2 == 1) {
			int w = filter.length / 2;
			double[][] output = new double[map.length][map[0].length];
			for (int y = 0; y < map.length; y++) {
				for (int x = 0; x < map[y].length; x++) {
					for (int i = 0; i < filter.length; i++) {
						for (int j = 0; j < filter[i].length; j++) {
							try {
								output[y][x] += map[y - i + w][x - j + w] * filter[i][j];
							} catch (ArrayIndexOutOfBoundsException e) {

							}
						}
					}
				}
			}
		
			return output;
		} else {
			return null;
		}
	}
	
	public double[][] arrayInColorBound(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[i].length; j++) {
				ar[i][j] = Math.max(0, ar[i][j]);
				ar[i][j] = Math.min(225, ar[i][j]);
			}
		}
		return ar;
	}
	
	public double[][] arrayColorInverse(double[][] ar) {
		for (int i = 0; i < ar.length; i++) {
			for (int j = 0; j < ar[i].length; j++) {
				ar[i][j] = 255 - ar[i][j];
			}
		}
		return ar;
	}
	
	public BufferedImage ar2im(double[][] ar) {
		BufferedImage output = new BufferedImage(ar[0].length, ar.length, BufferedImage.TYPE_INT_BGR);
		for (int y = 0; y < ar.length; y++) {
			for (int x = 0; x < ar[y].length; x++) {
			output.setRGB(x, y, new Color((int) ar[y][x], (int) ar[y][x], (int) ar[y][x]).getRGB());
			}
		}
		return output;
	}

}
