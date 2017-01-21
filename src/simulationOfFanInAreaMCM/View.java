/** 
 * @author finalObject
 *         http://www.finalobject.cn
 *         i@finalobject.cn
 *         https://github.com/finalObject
 * @date 2017年1月21日 下午12:22:17
 * @version 1.0
 */
package simulationOfFanInAreaMCM;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class View extends JPanel {
	int DEFAULT_X=1000;
	int DEFAULT_Y=400;
	double scale=8;
	int shiftX=50,shiftY=50;
	Area area;
	ArrayList<Car> cars;
	Scene scene;
	public View(Area area,ArrayList<Car> cars,Scene scene){
		super();
		this.area=area;
		this.cars=cars;
		this.scene=scene;

	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawRoad(g2);
		drawCars(g2);
		drawCarsTar(g2);
	}
	public void drawCarsTar(Graphics2D g2){
		int size=cars.size();
		Car car=null;
		for (int i=0;i<size;i++){
			car=cars.get(i);
			int x1=(int)((car.x)*scale)+shiftX;
			int y1=(int)((car.y)*scale)+shiftY;
			int x2=(int)((car.tarX)*scale)+shiftX;
			int y2=(int)((car.tarY)*scale)+shiftY;
			g2.setColor(Color.GRAY);
			g2.drawLine(x1, y1,x2,y2);
			g2.setColor(Color.BLACK);
		}
	}
	public void drawCars(Graphics2D g2){
		int size=cars.size();
		Car car=null;
		for (int i=0;i<size;i++){
			car=cars.get(i);
			int x=(int)((car.x-car.length/2)*scale)+shiftX;
			int y=(int)((car.y-car.width/2)*scale)+shiftY;
			g2.setColor(Color.GREEN);
			g2.fillRect(x, y, (int)(car.length*scale), (int)(car.width*scale));
			g2.setColor(Color.BLACK);
		}
	}
	public void drawRoad(Graphics2D g2){
		g2.setStroke(new BasicStroke(2.0f));
		int line11X=0+shiftX;
		int line11Y=0+shiftY;
		int line12X=(int)((area.lengthSpeed+area.lengthT)*scale)+line11X;
		int line12Y=line11Y;
		g2.drawLine(line11X, line11Y, line12X, line12Y);

		int line21X=line11X;
		int line21Y=line11Y+(int)(area.widthT*scale);
		int line22X=(int)((area.lengthT)*scale)+line21X;
		int line22Y=line21Y;
		g2.drawLine(line21X, line21Y, line22X, line22Y);

		int line31X=line22X;
		int line31Y=line22Y;
		int line32X=line12X;
		int line32Y=line12Y+(int)(area.widthL*scale);
		g2.drawLine(line31X, line31Y, line32X, line32Y);
		g2.setStroke(new BasicStroke(1.0f));
		for(int i=0;i<scene.numOfToll-1;i++){
			int line1X=0+shiftX;
			int line1Y=(int)(scene.widthT*(i+1)*scale)+shiftY;
			int line2X=50+shiftX;;
			int line2Y=(int)(scene.widthT*(i+1)*scale)+shiftY;
			g2.drawLine(line1X, line1Y, line2X, line2Y);
		}
		
	}
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_X , DEFAULT_Y);
	}
}
