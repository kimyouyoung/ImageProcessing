package project.imageProcessing;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileSystemView;

public class Image extends JFrame{
	
	JFileChooser filechose = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	String address = "";
	String save_add = "";
	
	JPanel panel;
	JPanel panel_1;
	JPanel panel_2;
	JPanel panel_3;
	
	int mx;
	int my;
	
	JSlider e_silder;
	JSlider b_silder;
	
	
	BufferedImage buf = null;
	ImageIcon m_image;
	Shape s;
	
	int function = 0;
	int mode = 0;
	int black = 0;
	double gamma = 1.0;
	
	int color = 0;
	
	double e_n = 0.5;
	float bright = 0.5f;
	
	String s_add = "";
	
	int count = 0;
	
	public Image() {
		
		this.setSize(1400, 800);
		this.getContentPane().setBackground(Color.DARK_GRAY);
		
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		JMenuItem[] item = new JMenuItem[3];
		String[] name_item = {"Open", "Save", "Exit"};
		
		
		for(int i = 0; i < name_item.length; i++) {
			item[i] = new JMenuItem(name_item[i]);
			menu.add(item[i]);
			item[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String str = e.getActionCommand();
					if(str.equals("Exit")) {
						System.exit(0);
					}else if(str.equals("Open")) {
						int returnvalue = filechose.showOpenDialog(null);
						if(returnvalue == JFileChooser.APPROVE_OPTION) {
							File select = filechose.getSelectedFile();
							if(function == 6)
								s_add = select.getAbsolutePath();
							else
								address = select.getAbsolutePath();
							mode = 1;
							repaint();
						}
					}else if(str.equals("Save")) {
						int returnvalue = filechose.showSaveDialog(null);
						if(returnvalue == JFileChooser.APPROVE_OPTION) {
							File select = filechose.getSelectedFile();
							save_add = select.getAbsolutePath();
						}
						try {
							String string = save_add + ".jpg";
							ImageIO.write(buf, "jpg", new File(string));
						}catch(IOException t) {
							t.printStackTrace();
						}
					}
					
				}
			});
		}
		
		
		menuBar.add(menu);
		setJMenuBar(menuBar);
		
		this.setTitle("PHOTO");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setLayout(null);
		
		
		panel = new JPanel();
		panel.setBounds(50, 20, 1300, 50);
		panel.setBackground(Color.DARK_GRAY);
		
		Checkbox cb = new Checkbox("Black");
		cb.setForeground(Color.WHITE);
		cb.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					black = 1;
					repaint();
				}else { 
					black = 0;
					repaint();
				}
			}
			
		});
		panel.add(cb);
		
		JButton[] btn = new JButton[9];
		String[] fun = {"BLACK & WHITE", "EDGE", "ORIGINAL", "CONTRAST", "MAGNIFIER", "SYNTHETIC", "SHARPNESS", "ROTATE", "COLOR"};
		
		for(int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(fun[i]);
			panel.add(btn[i]);
			btn[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					String str = e.getActionCommand();
					
					if(str.equals("BLACK & WHITE")) {
						function = 1;
						repaint();
					}else if(str.equals("EDGE")) {
						function = 2;
						repaint();
					}else if(str.equals("ORIGINAL")) {
						function = 3;
						repaint();
					}else if(str.equals("CONTRAST")) {
						function = 4;
						repaint();
					}else if(str.equals("MAGNIFIER")) {
						function = 5;
						repaint();
					}else if(str.equals("SYNTHETIC")) {
						function = 6;
						repaint();
					}else if(str.equals("SHARPNESS")) {
						function = 7;
						repaint();
					}else if(str.equals("ROTATE")) {
						function = 8;
						count++;
						repaint();
					}else if(str.equals("COLOR")) {
						function = 9;
						repaint();
					}
					
				}
				
			});
		}
		
		String[] list = {"------", "RED", "GREEN", "BLUE"};
		JComboBox<String> col = new JComboBox<String>(list);
		col.setSelectedIndex(0);
		col.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String str = (String)col.getSelectedItem();
				if(str.equals("RED")) {
					color = 1;
					repaint();
				}else if(str.equals("GREEN")) {
					color = 2;
					repaint();
				}else if(str.equals("BLUE")) {
					color = 3;
					repaint();
				}else {
					color = 4;
					repaint();
				}
			}
			
		});
		
		panel.add(col);
		this.add(panel);
		
		panel_1 = new MyPanel();
		panel_1.setBounds(100, 100, 550, 600);
		panel_1.setBackground(Color.white);
		this.add(panel_1);
		
		panel_2 = new ChangePanel();
		panel_2.setBounds(760, 100, 550, 600);
		panel_2.setBackground(Color.white);
		this.add(panel_2);
		
		
		e_silder = new JSlider(0, 30, 10);
		e_silder.setBounds(900, 700, 300, 50);
		e_silder.setMajorTickSpacing(10);
		e_silder.setMinorTickSpacing(5);
		e_silder.setForeground(Color.WHITE);
		e_silder.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider select = (JSlider)e.getSource();
				e_n = select.getValue() * 0.1;
				repaint();
			}
			
		});
		this.add(e_silder);
		
		JSlider s_silder = new JSlider(0, 100, 100);
		s_silder.setBounds(200, 50, 300, 50);
		s_silder.setForeground(Color.WHITE);
		s_silder.setMajorTickSpacing(50);
		s_silder.setMinorTickSpacing(10);
		s_silder.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider select = (JSlider)e.getSource();
				gamma = select.getValue() * 0.01;
				repaint();
			}
			
		});
		this.add(s_silder);
		
		JSlider bi_silder = new JSlider(100, 250, 100);
		bi_silder.setBounds(500, 50, 300, 50);
		bi_silder.setForeground(Color.WHITE);
		bi_silder.setMajorTickSpacing(50);
		bi_silder.setMinorTickSpacing(10);
		bi_silder.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider select = (JSlider)e.getSource();
				gamma = select.getValue() * 0.01;
				repaint();
			}
			
		});
		this.add(bi_silder);
		
		JSlider bb_silder = new JSlider(0, 250, 50);
		bb_silder.setBounds(900, 50, 300, 50);
		bb_silder.setForeground(Color.white);
		bb_silder.setMajorTickSpacing(20);
		bb_silder.setMinorTickSpacing(10);
		bb_silder.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider select = (JSlider)e.getSource();
				bright = Float.parseFloat(String.valueOf(select.getValue() * 0.01));
				repaint();
			}
			
		});
		this.add(bb_silder);
		this.setVisible(true);

	}
	
	class MyPanel extends JPanel{

		public MyPanel() {
			
		}
		
		public void paintComponent(Graphics g) {
			if(mode == 1) {
				Dimension d = panel_1.getSize();
				ImageIcon image = new ImageIcon(address);
				g.drawImage(image.getImage(), 0, 0, d.width, d.height, null);
			}
			if(function == 5) {
				Dimension d = panel_1.getSize();
				MyMouseListener ml = new MyMouseListener();
				this.addMouseListener(ml);
				this.addMouseMotionListener(ml);
			}
		}
		
	}
	
	class ChangePanel extends JPanel{
		
		
		public ChangePanel() {
			
		}
		
		public void paintComponent(Graphics g1) {
			Graphics2D g = (Graphics2D)g1;
			if(function == 1) {
				Dimension d = panel_2.getSize();
				try {
					BufferedImage image = ImageIO.read(new File(address));
					for(int y = 0; y < image.getHeight(); y++) {
	                    for(int x = 0; x < image.getWidth(); x++) {
	                        Color color = new Color(image.getRGB(x, y));
	                        int Y = (int) (0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue());
	                        image.setRGB(x, y, new Color(Y, Y, Y).getRGB());
	                    }
	                 }
					BufferedImage result_bi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
					RescaleOp rescaleOp = new RescaleOp(bright, 0, null);
					rescaleOp.filter(image, result_bi);
					BufferedImage gam = gammaCorrection(result_bi, gamma);
					float[] sharpen =new float[] {
							0.0f, -1.0f, 0.0f,
							-1.0f, 5.0f, -1.0f,
							0.0f, -1.0f, 0.0f,
					};
					Kernel kernel = new Kernel(3, 3, sharpen);
					ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
					gam = op.filter(gam, null);
					ImageIcon img = new ImageIcon(gam);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
					buf = gam;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(function == 2) {
				Dimension d = panel_2.getSize();
				
				try {
					BufferedImage image = ImageIO.read(new File(address));
					Edge edge = new Edge();
					double[][]ar = edge.im2ar(image, e_n);
					double[][] filterBlur = edge.getFilter(3);
					ar = edge.convolution(ar, filterBlur);
					double[][] filterEdge = { { 1, 1, 1 }, { 1, -8, 1 }, { 1, 1, 1 } };
					ar = edge.convolution(ar, filterEdge);
					ar = edge.arrayInColorBound(ar);
					if(black == 0)
						ar = edge.arrayColorInverse(ar);
					BufferedImage output = edge.ar2im(ar);
 					ImageIcon img = new ImageIcon(output);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
					buf = output;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(function == 3) {
				Dimension d = panel_2.getSize();
				
				try {
					BufferedImage image = ImageIO.read(new File(address));
					BufferedImage result_bi = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
					RescaleOp rescaleOp = new RescaleOp(bright, 0, null);
					rescaleOp.filter(image, result_bi);
					BufferedImage gam = gammaCorrection(result_bi, gamma);
					ImageIcon img = new ImageIcon(gam);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
					buf = gam;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(function == 4) {
				Dimension d = panel_2.getSize();
				
				try {
					BufferedImage original = ImageIO.read(new File(address));
					BufferedImage gam = gammaCorrection(original, gamma);
					ImageIcon img = new ImageIcon(gam);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
					buf = gam;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			
				g.drawImage(buf, 0, 0, d.width, d.height, null);
					
			}else if(function == 5) {
					BufferedImage image;
					try {
						image = ImageIO.read(new File(address));
						buf = image;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				MyMouseListener ml = new MyMouseListener();
				this.addMouseListener(ml);
				this.addMouseMotionListener(ml);
				
				
				Dimension d = panel_2.getSize();
				if(m_image != null) {
					g.drawImage(m_image.getImage(), 0, 0, d.width, d.height, null);
				}
			}else if(function == 6) {
				Dimension d = panel_2.getSize();
				try {
					BufferedImage image = ImageIO.read(new File(address));
					if(!s_add.equals("")) {
						BufferedImage merge = ImageIO.read(new File(s_add));
						ImageIcon img = new ImageIcon(image);
						g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
						ImageIcon mer = new ImageIcon(merge);
						g.drawImage(mer.getImage(), 100, 0, mer.getIconWidth(), mer.getIconHeight(), null);
						buf = new BufferedImage(800, 810, BufferedImage.TYPE_INT_RGB);
						buf.getGraphics();
					}else {
						ImageIcon img = new ImageIcon(image);
						g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
						buf = image;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(function == 7) {
				Dimension d = panel_2.getSize();
				
				try {
					BufferedImage image = ImageIO.read(new File(address));
					float[] sharpen =new float[] {
							0.0f, -1.0f, 0.0f,
							-1.0f, 5.0f, -1.0f,
							0.0f, -1.0f, 0.0f,
					};
					Kernel kernel = new Kernel(3, 3, sharpen);
					ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
					image = op.filter(image, null);
					ImageIcon img = new ImageIcon(image);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
					buf = image;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else if(function == 8) {
				Dimension d = panel_2.getSize();
				double angle = 0;
				int x = 0;
				int y = 0;
				if(count%4 == 1) {
					angle = 90;
				}else if(count%4 == 2) {
					angle = 180;
				}else if(count%4 == 3) {
					angle = 270;
				}else if(count%4 == 0) {
					angle = 0;
				}
				BufferedImage image;
				try {
					image = ImageIO.read(new File(address));
					BufferedImage new_image = rotate(image, angle);

					ImageIcon img = new ImageIcon(new_image);
					g.drawImage(img.getImage(), x, y, d.width+x, d.height+y, null);
					buf = new_image;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(function == 9) {
				Dimension d = panel_2.getSize();
				BufferedImage image = null;
				
				try {
					BufferedImage new_image = null;
					image = ImageIO.read(new File(address));
					if(color == 1)
						new_image = getRed(image);
					else if(color == 2)
						new_image = getGreen(image);
					else if(color == 3)
						new_image = getBlue(image);
					else
						new_image = image;
					ImageIcon img = new ImageIcon(new_image);
					g.drawImage(img.getImage(), 0, 0, d.width, d.height, null);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public BufferedImage getRed(BufferedImage image) {
		BufferedImage new_image;
		int width = image.getWidth();
		int height = image.getHeight();
		
		int red, green, blue;
		
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				red = new Color(image.getRGB(w, h)).getRed();
				green = new Color(image.getRGB(w, h)).getGreen();
				blue = new Color(image.getRGB(w, h)).getBlue();
				if(new Color(image.getRGB(w, h)).getRed() > 180) {
					red = 255;
					
				}
				image.setRGB(w, h, new Color(red, green, blue).getRGB());
			}
		}
		new_image = image;
		return new_image;
	}
	
	public BufferedImage getGreen(BufferedImage image) {
		BufferedImage new_image;
		int width = image.getWidth();
		int height = image.getHeight();
		
		int red, green, blue;
		
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				red = new Color(image.getRGB(w, h)).getRed();
				green = new Color(image.getRGB(w, h)).getGreen();
				blue = new Color(image.getRGB(w, h)).getBlue();
				if(new Color(image.getRGB(w, h)).getGreen() > 180) {
					green = 255;
					
				}
				image.setRGB(w, h, new Color(red, green, blue).getRGB());
			}
		}
		new_image = image;
		return new_image;
	}
	
	public BufferedImage getBlue(BufferedImage image) {
		BufferedImage new_image;
		int width = image.getWidth();
		int height = image.getHeight();
		
		int red, green, blue;
		
		for(int w = 0; w < width; w++) {
			for(int h = 0; h < height; h++) {
				red = new Color(image.getRGB(w, h)).getRed();
				green = new Color(image.getRGB(w, h)).getGreen();
				blue = new Color(image.getRGB(w, h)).getBlue();
				if(new Color(image.getRGB(w, h)).getBlue() > 180) {
					blue = 255;
					
				}
				image.setRGB(w, h, new Color(red, green, blue).getRGB());
			}
		}
		new_image = image;
		return new_image;
	}
	
	public BufferedImage rotate(BufferedImage bi, double angle) {
		int width = bi.getWidth();
	    int height = bi.getHeight();

	    BufferedImage biFlip;
	    if (angle == 90 || angle == 270)
	        biFlip = new BufferedImage(height, width, bi.getType());
	    else if (angle == 180)
	        biFlip = new BufferedImage(width, height, bi.getType());
	    else 
	        return bi;

	    if (angle == 90) {
	        for (int i = 0; i < width; i++)
	            for (int j = 0; j < height; j++)
	                biFlip.setRGB(height- j - 1, i, bi.getRGB(i, j));
	   }

		if (angle == 180) {
		    for (int i = 0; i < width; i++)
		        for (int j = 0; j < height; j++)
		            biFlip.setRGB(width - i - 1, height - j - 1, bi.getRGB(i, j));
		}
	
		if (angle == 270) {
		    for (int i = 0; i < width; i++)
		        for (int j = 0; j < height; j++)
		            biFlip.setRGB(j, width - i - 1, bi.getRGB(i, j));
		}

		bi.flush();
		bi = null;

		return biFlip;
	}
	
	public BufferedImage gammaCorrection(BufferedImage original, double gamma) {

        int alpha, red, green, blue;
        int newPixel;

        double gamma_new = 1 / gamma;
        int[] gamma_LUT = gamma_LUT(gamma_new);

        BufferedImage gamma_cor = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

        for (int i = 0; i < original.getWidth(); i++) {
            for (int j = 0; j < original.getHeight(); j++) {

                alpha = new Color(original.getRGB(i, j)).getAlpha();
                red = new Color(original.getRGB(i, j)).getRed();
                green = new Color(original.getRGB(i, j)).getGreen();
                blue = new Color(original.getRGB(i, j)).getBlue();

                red = gamma_LUT[red];
                green = gamma_LUT[green];
                blue = gamma_LUT[blue];

                newPixel = colorToRGB(alpha, red, green, blue);

                gamma_cor.setRGB(i, j, newPixel);

            }

        }

        return gamma_cor;

    }

    public int[] gamma_LUT(double gamma_new) {
        int[] gamma_LUT = new int[256];

        for (int i = 0; i < gamma_LUT.length; i++) {
            gamma_LUT[i] = (int) (255 * (Math.pow((double) i / (double) 255, gamma_new)));
        }

        return gamma_LUT;
    }

    public int colorToRGB(int alpha, int red, int green, int blue) {

        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;

        return newPixel;

    }

	
	class MyMouseListener extends MouseAdapter implements MouseListener{
		
		
		public void mousePressed(MouseEvent e) {
			
		}
		
		public void mouseReleased(MouseEvent e) {
			
		}
		
		public void mouseDragged(MouseEvent e) {
			
		}
		
		public void mouseMoved(MouseEvent e) {
			
			if(function == 5) {
				Point start_p = e.getPoint();
				mx = start_p.x;
				my = start_p.y;
				BufferedImage sub_img = null;
				if(start_p.x >0 && start_p.y>100 && start_p.x+80<550 && start_p.y+100<600) {
					sub_img = buf.getSubimage(start_p.x+60, start_p.y-200, 150, 150);
					java.awt.Image im = sub_img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
					m_image = new ImageIcon(im);
				}
				repaint();
				
				
			}
			
		}
		
	}
	
}
